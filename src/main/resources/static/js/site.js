$(document).ready(function () {

    $('a.like-button').on('click', function () {
        $(this).toggleClass('liked');
    });


// Recipe Creation JS


    // Function to add on additional Quantity/Ingredient fields
    $('#addIngredient').click(function () {
        addIngredient();
    });

    $('#removeIngredient').click(function () {
        console.log("remove");
       $('.ingredientItem').filter(':last').remove();
    });


    function addIngredient() {
        var html = "<div class=\"col-sm-12 ingredientItem\">\n" +
            "<div class=\"row\">\n" +
            "<div class=\"col-sm-2\">\n" +
            "<input class=\"form-control\" type=\"text\" name=\"quantity\"/>\n" +
            "</div>\n" +
            "<div class=\"col-sm-8\">\n" +
            "<input class=\"form-control\" type=\"text\" name=\"ingredients\"/>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>"
        $('#ingredientList').append(html);
    }


});
