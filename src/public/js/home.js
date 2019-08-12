$(document).ready(function () {
    $("#nav-bar").load("../html/navigation.html");

    if (!$("#book-table").first().children().is("h1")) {
        var htmlText = $("#book-table").text();
        var htmlObject = $(htmlText);
        $("#book-table").empty();
        $("#book-table").append(htmlObject);
    }
});