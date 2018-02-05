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
        hideIng();
        setTimeout(function () {
            $('.ingredientItem').filter(':last').remove();
        }, 600);
    });
    
    function removeIng() {
        $('.ingredientItem').filter(':last').remove();
    }

    function hideIng() {
       $('.ingredientItem').filter(':last').hide('slow');
    }


    function addIngredient() {
        var item = $('.ingredientItem').length + 1;
        var html = "<div class=\"col-sm-12 ingredientItem\" style=\"display: none\">\n" +
            "<div class=\"row\">\n" +
            "<div class=\"col-sm-2\"><p>" + item + ")</p></div>" +
            "<div class=\"col-sm-2\">\n" +
            "<input class=\"form-control\" type=\"text\" name=\"quantity\"/>\n" +
            "</div>\n" +
            "<div class=\"col-sm-8\">\n" +
            "<input class=\"form-control\" type=\"text\" name=\"ingredients\"/>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>"
        $('#ingredientList').append(html);
        $('.ingredientItem').filter(':last').toggle('slow');
    }


});
