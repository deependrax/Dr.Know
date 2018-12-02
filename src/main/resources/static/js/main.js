$(document).ready(function() {
	$("#search-form").submit(function(event) {
		// stop submit the form, we will post it manually.
		event.preventDefault();
		ajaxSubmit();
	});
});

function ajaxSubmit() {
	var search = {}
	search["question"] = $("#question").val();
	$('#results').html('');

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/api/ask-dr-know",
		data : JSON.stringify(search),
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {
			console.log(data);
			var json = data.answers.map(ans => {
					var ansStr = "<pre>" + ans.answer.replace(/^"|"$/g, '').replace(/\\n/g, '<br/>'); 
					if(ans.keywords != null && ans.keywords.length > 0) {
							ansStr += "<hr>" + ans.keywords.map(key => {
								return "<span class='keyword " + (key.match ? "match" : "") + "'>" 
									+ key.keyword
									+ '</span>'
						  }).join('\t')
						}
					ansStr += "</pre>";
					return ansStr;
					}).join('')
				
			$('#results').html(json);
		},
		error : function(e) {
			var json = "<pre>"+ JSON.parse(e.responseText).msg + "</pre>";
			$('#results').html(json);
			console.error("ERROR: ", e);
		}
	});
}