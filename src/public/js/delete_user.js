$(document).ready(function () {
    $("#nav-bar").load("../html/navigation.html");

    var messageParam = window.location.search.match(/[^?]+$/gm);
    if (messageParam !== null) {
        message = messageParam[0];
        switch (message) {
            case 'success':
                displayMessage("green", "User deleted from database.");
                break;
            case 'error':
                displayMessage("red", "User with that username does not exist.");
                break;
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