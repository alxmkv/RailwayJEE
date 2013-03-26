$(document).ready(function() {
	$("#register-button").click(function() {
		$("#login-form").hide();
		$("#register-form").show();
	});

	$("#register-form:home-button").click(function() {
		$("#register-form").hide();
		$("#login-form").show();
	});

	$("#begindatepicker").datepicker({
		appendText : '(yyyy-mm-dd)',
		autoSize : true,
		changeMonth : true,
		changeYear : true,
		constrainInput : true,
		dateFormat : 'yy-mm-dd',
		duration : 'fast',
		firstDay : 1,
		minDate : 0,
		numberOfMonths : [ 1, 1 ],
		selectOtherMonths : true,
		showButtonPanel : true,
		showOn : 'both',
		showOtherMonths : true,
		showWeek : true
	});
	$("#enddatepicker").datepicker({
		appendText : '(yyyy-mm-dd)',
		autoSize : true,
		changeMonth : true,
		changeYear : true,
		constrainInput : true,
		dateFormat : 'yy-mm-dd',
		duration : 'fast',
		firstDay : 1,
		minDate : 0,
		numberOfMonths : [ 1, 1 ],
		selectOtherMonths : true,
		showButtonPanel : true,
		showOn : 'both',
		showOtherMonths : true,
		showWeek : true
	});
})(jQuery);