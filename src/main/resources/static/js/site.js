$(document).ready(function () {
    
$('a.like-button').on('click', function() {
    $(this).toggleClass('liked');
});


// Recipe Creation JS


$('#addIngredient').click(function () {
    $('#ingredientSec').append("<div class=\"col-sm-2\">" +
        "<button type=\"button\" class=\"btn btn-outline-primary\" id=\"removeIng1\">Remove</button>" +
        "<div class=\"col-sm-2\">" +
        "<input class=\"form-control\" type=\"text\" name=\"quantity\"/>" +
        "</div>" +
        "<div class=\"col-sm-8\">" +
        "<input class=\"form-control\" type=\"text\" name=\"ingredients\"/>" +
        "</div>");
});












});






