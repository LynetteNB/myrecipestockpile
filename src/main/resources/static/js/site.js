$(document).ready(function () {

    $('a.like-button').on('click', function () {
        $(this).toggleClass('liked');
    });


// Recipe Creation JS

    // Function to add on additional Quantity/Ingredient fields
    $('#addIngredient').click(function () {
        addIngredient();
        addRemoveListeners();
    });

    // $('#removeIngredient').click(function () {
    //     hideIng();
    //     setTimeout(function () {
    //         $('.ingredientItem').filter(':last').remove();
    //     }, 600);
    // });

    function addRemoveListeners() {
        for (var i = 1; i <= $('.ingredientItem').length; i++) {
            // var string = "#removeIng"
            $('#removeIng' + i).off('click');
            $('#removeIng' + i).click(function () {
                var id = $(this).attr('id');
                var index = id.substring(id.length - 1);
                $('#ingId' + index).hide('slow');
                setTimeout(function () {
                    $('#ingId' + index).remove();
                }, 600);
            })
        }
    }


    
    // function removeIng() {
    //     $('.ingredientItem').filter(':last').remove();
    // }
    //
    //
    //
    // function hideIng() {
    //    $('.ingredientItem').filter(':last').hide('slow');
    // }
    //

    function addIngredient() {
        var itemNum = $('.ingredientItem').length + 1;
        var html = "<div class=\"col-sm-12 ingredientItem\" style=\"display: none\" id=\"ingId" + itemNum + "\">" +
            "<div class=\"row\">\n" +
            "<div class=\"col-sm-2\"><button type=\"button\" class=\"btn btn-outline-success btn-block\" id=\"removeIng" + itemNum + "\">Remove</button></div>" +
            "<div class=\"col-sm-2\">\n" +
            "<input class=\"form-control\" type=\"text\" name=\"quantity\"/>\n" +
            "</div>\n" +
            "<div class=\"col-sm-8\">\n" +
            "<input class=\"form-control\" type=\"text\" name=\"ingredients\"/>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>"
        $('#ingredientList').append(html);
        $('.ingredientItem').filter(':last').show('slow');
    }


});
