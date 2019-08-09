$(document).ready(function () {
    $("#nav-bar").load("../html/navigation.html");

    var messageParam = window.location.search.match(/[^?]+$/gm);
    if (messageParam !== null) {
        message = messageParam[0];
        switch (message) {
            case 'success':
                displayMessage("green", "User added to the database.");
                break;
            case 'error-user':
                displayMessage("red", "User with that username already exists.");
                break;
            case 'error-password':
                displayMessage("red", "Passwords do not match.");
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