$(document).ready(function () {
    $("#nav-bar").load("../html/navigation.html");

    var searchParam = window.location.search;

    if (searchParam === '') {
        $(".edit-wrapper").hide();
    } else {
        $(".edit-wrapper").show();
    }

    var messageParam = searchParam.match(/[^?]+$/gm);
    if (messageParam !== null) {
        message = messageParam[0];
        if (message === "success") {
            displayMessage("green", "Book information changed.");
        }
    }

    displaySearchMessage("red", "Book with that ISBN does not exist.");

    function displaySearchMessage(color, text) {
        if ($(".edit-wrapper").is(":visible") && !$("#title").val()) {
            $(".edit-message-section").toggleClass("message-toggle");
            $(".edit-message-section").css("background-color", color);
            $(".edit-message-section").first().children().text(text);
            setTimeout(function () {
                $(".edit-message-section").toggleClass("message-toggle");
            }, 10000);
            $(".edit-wrapper").hide();
        }
    }

    function displayMessage(color, text) {
        $(".message-section").toggleClass("message-toggle");
        $(".message-section").css("background-color", color);
        $(".message-section").first().children().text(text);
        setTimeout(function () {
            $(".message-section").toggleClass("message-toggle");
        }, 10000);
    }
});