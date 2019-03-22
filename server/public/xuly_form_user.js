$(document).ready(function() {
	$("#aboutInput").hide();
	$("#slogan").hide();
	$("#avatar").hide();
	$("#coverAvatar").hide();
	$("#linkInfo").hide();
	$("#completed").hide();

	$("#editProfile").click(function() {
		$("input").prop('disabled', false);
		$("#username").attr('disabled','disabled');
		$("#aboutInput").show();
		$("#aboutOutput").hide();
		$("#slogan").show();
		$("#avatar").show();
		$("#coverAvatar").show();
		$("#linkInfo").show();
		$("#editProfile").hide();
		$("#completed").show();
	});
});