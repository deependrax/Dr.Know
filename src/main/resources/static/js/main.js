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
					var ansStr = "<pre>" + ans.answer.replace(/\\n/g, '<br/>'); 
					if(ans.keywords != null && ans.keywords.length > 0) {
							ansStr += "<hr>" + ans.keywords.map(key => {
								return "<span class='keyword " + (key.match ? "match" : "") + "'>" 
									+ key.keyword
									+ '</span>'
						  }).join(' | ')
						}
					ansStr += "</pre>";
					return ansStr;
					}).join('')
					
				var answersStr = " answers ";
				if(data.answers.length == 1){
					answersStr = " answer "
				}

				if(data.answers[0].answer != "I don't know this."){
					json = "<small><i>" + data.answers.length + answersStr +"found.</i></small><br>" + json;
				}
			$('#results').html(json);
		},
		error : function(e) {
			var json = "<pre>"+ JSON.parse(e.responseText).msg + "</pre>";
			$('#results').html(json);
			console.error("ERROR: ", e);
		}
	});
}