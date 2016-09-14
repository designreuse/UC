(function ($) {
	var max = $('.spinner input').attr("maxspin");
	var min = $('.spinner input').attr("minspin");

  $('.spinner input').blur(function() {
	  var value = $.trim($('.spinner input').val());

	  if (value == "" || !isNumber(value)){
		  value = min;
	  }

	  var parseValue = parseInt(value, 10);

	  if (parseValue > max){
		  parseValue = max;
	  }

	  if (parseValue < min){
		  parseValue = min;
	  }

	  $('.spinner input').val(parseInt(parseValue, 10));
  });

  $('.spinner .btn:first-of-type').on('click', function() {
	var value = $.trim($('.spinner input').val());
	if (value == "" || !isNumber(value)){
		value = min;
	}
	var parseValue = parseInt(value, 10) + 1;
	if (parseValue > max){
		parseValue = max;
	}
	if (parseValue < min){
		parseValue = min;
	}
    $('.spinner input').val(parseValue);
  });

  $('.spinner .btn:last-of-type').on('click', function() {
	var value = $.trim($('.spinner input').val());
	if (value == "" || !isNumber(value)){
		value = min;
	}
	var parseValue = parseInt(value, 10) -1;
	if (parseValue < min){
		parseValue = min;
	}
	if (parseValue > max){
		parseValue = max;
	}
    $('.spinner input').val(parseValue);
  });
})(jQuery);