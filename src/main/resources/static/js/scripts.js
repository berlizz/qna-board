
String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};


$(".answer-write input[type=submit]").on("click", addAnswer);

function addAnswer(e) {
	e.preventDefault();

	var queryString = $(".answer-write").serialize();
	var url = $(".answer-write").attr("action");

	$.ajax({
		type : "post",
		url : url,
		data : queryString,
		dataType : "json",
		success : onSuccess
	});
}

function onSuccess(data, status) {
	var answerTemplate = $("#answerTemplate").html();
	template = answerTemplate.format(data.writer.userId, data.formattedCreateTime, data.contents, data.question.id, data.id);
	$(".qna-comment-slipp-articles").append(template);
	
	$(".answer-write textarea").val("");
	
	$(".qna-comment-count strong").html(Number($(".qna-comment-count strong").html()) + 1);
}

$(document).on("click", ".link-delete-answer", function(e) {
	e.preventDefault();
	
	var that = $(this);
	var url = that.attr("href");
	
	$.ajax({
		type : "delete",
		url : url,
		dataType : "json",
		success : function(data) {
			if(data.valid) {
				that.closest("article").remove();
				$(".qna-comment-count strong").html(Number($(".qna-comment-count strong").html()) - 1);
			} else {
				alert(data.errorMessage);
			}
		}
	});
});




