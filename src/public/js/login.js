(function ($) {
	"use strict";

	var options = {
		'btn-loading': '<i class="fa fa-spinner fa-pulse"></i>',
		'btn-success': '<i class="fa fa-check"></i>',
		'btn-error': '<i class="fa fa-remove"></i>',
		'msg-success': 'All Good! Redirecting...',
		'msg-error': 'Wrong login credentials!',
		'useAJAX': true,
	};

	$("#login").validate({
		rules: {
			username: "required",
			password: "required",
		},
		errorClass: "form-invalid"
	});

	$("#login").submit(function () {
		if (options['useAJAX'] == true) {

			dummy_submit_form($(this));

			return false;
		}
	});

	function dummy_submit_form($form) {
		if ($form.valid()) {
			form_loading($form);

			setTimeout(function () {
				form_success($form);
			}, 2000);
		}
	}

})(jQuery);