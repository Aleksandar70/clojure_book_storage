$('.toggle-register').click(function () {
  $(this).addClass('active');
  $('.toggle-login').removeClass('active');
  $('.login-body').slideUp("fast");
  $('.register-body').delay(275).slideDown("fast");
});

$('.toggle-login').click(function () {
  $(this).addClass('active');
  $('.toggle-register').removeClass('active');
  $('.register-body').slideUp("fast");
  $('.login-body').delay(275).slideDown("fast");
});

$('#registered').click(function () {
  $('.toggle-login').click();
});
