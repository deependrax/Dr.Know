$(document).ready(function() {
	$("#search-form").submit(function(event) {
		//stop submit the form, we will post it manually.
		event.preventDefault();
		ajaxSubmit();
	});
});

function ajaxSubmit() {
	var search = {}
	search["question"] = $("#question").val();

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/api/ask-dr-know",
		data : JSON.stringify(search),
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {
			var json = "<h4>This might help</h4>" 
				+ data.answers.map(ans => {
					return "<pre>" + ans.answer + "<hr>" 
						+ ans.keywords.map(key => {
							return "<span class='keyword " + (key.match ? "match" : "") + "'>" 
									+ key.keyword
									+ '</span>'
						  }).join('\t')
						+ "</pre>"			
					}).join('')
				
			$('#results').html(json);
			console.log(data)
		},
		error : function(e) {
			var json = "<h4>Don't under estimate Dr. Know</h4><pre>"
					+ JSON.parse(e.responseText).msg + "</pre>";
			$('#results').html(json);
			console.error("ERROR: ", e);
		}
	});
}