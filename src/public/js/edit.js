$(document).ready(function () {
    $("#nav-bar").load("../html/navigation.html");

    var searchParam = window.location.search;
    if (searchParam === '') {
        $(".edit-wrapper").hide();
    } else {
        $(".edit-wrapper").show();
    }
});