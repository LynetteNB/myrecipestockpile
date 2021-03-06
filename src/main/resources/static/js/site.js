$(document).ready(function () {

    // create stockpile button on profile page

    // - Hearting A Recipe -

    $('#heart').on('click', function () {
        $(this).toggleClass('liked');
        var recipeId = $('#recipeIdInfo').attr('data');
        var userId = $('#userIdInfo').attr('data');
        $.ajax("/heart-update", {
            type: "POST",
            data: {
                userId: userId,
                recipeId: recipeId,
                _csrf: $('input[name=_csrf]').val()
            }
        }).done(function (results) {
            // Update the visual heart number for better user experience
            // Requires a unique Id on a heart span. Refactor to use on multi-recipe listing
            var heartCount = $('#heartCount');
            var currentCount = heartCount.text();
            var newCount;
            var increaseCount = $('#heart').hasClass('liked');
            if (increaseCount) {
                newCount = parseInt(currentCount) + 1;
                heartCount.text(newCount);
            } else {
                newCount = parseInt(currentCount) - 1;
                heartCount.text(newCount);
            }

        });

    });

    // hearting recipes on multi-recipe page

    var cardHearts = $('.card-hearts');
    cardHearts.each(function (index) {
        $(this).attr('id', 'heart' + index);
        $(this).click(function () {
            $(this).toggleClass('liked');
            var recipeId = $(this).attr('data');
            var userId = $('#userIdInfo').attr('data');
            $.ajax("/heart-update", {
                type: "POST",
                data: {
                    userId: userId,
                    recipeId: recipeId,
                    _csrf: $('input[name=_csrf]').val()
                }
            }).done(function (results) {
                console.log('sent')
            });
            var heartCount = $(this).find('.heartCount').first();
            var currentCount = heartCount.text();
            var newCount;
            var increaseCount = $(this).hasClass('liked');
            if (increaseCount) {
                newCount = parseInt(currentCount) + 1;
                heartCount.text(newCount);
            } else {
                newCount = parseInt(currentCount) - 1;
                heartCount.text(newCount);
            }
        });
    });


    // - Recipe Form Input HTML Injections -
    // These 2 functions place id's and listeners on respective sections.
    // Without running these, some inputs might not have ids.
    remakeIngredientIdsAndListeners();
    remakeInstructionIdsAndListeners();

    // --- To and and remove items in the ingredients section ---
    $('#addIngredient').click(function () {
        addIngredient();
        remakeIngredientIdsAndListeners();
    });

    // Injects new Ingredient html for inputs at bottom of the list with a fade in animation
    function addIngredient() {
        var html = '<div class="col-sm-12 ingredientItem" style="display: none">' +
            '<div class="row">' +
            '<div class="col-sm-2"><button type="button" class="btn btn-outline-danger btn-block rmvIngButton">&#8998;</button></div>' +
            '<div class="col-sm-2">' +
            '<input class="form-control" type="text" name="quantity"/>' +
            '</div>' +
            '<div class="col-sm-8">' +
            '<input class="form-control" type="text" name="ingredients"/>' +
            '</div>' +
            '</div>' +
            '</div>';
        $('#ingredientList').append(html);
        $('.ingredientItem').filter(':last').show('slow');
    }

    // Recreates the the ids on ingredientItems and each of their delete buttons
    // Refreshed the click listeners on the delete buttons
    function remakeIngredientIdsAndListeners() {
        $('.ingredientItem').each(function (index) {
            var idx = 'ingId' + index;
            $(this).attr('id', idx);
        });
        $('.rmvIngButton').each(function (index) {
            var idx2 = 'removeIng' + index;
            $(this).attr('id', idx2);
            $(this).off('click');
            $(this).click(function () {
                var ingredientItem = $(this).closest('.ingredientItem');
                ingredientItem.hide('slow');
                setTimeout(function () {
                    ingredientItem.remove();
                }, 600);

            })
        });
    }

    // --- To add and remove items in the instructions section ---

    $('#addInstruction').click(function () {
        addInstruction();
        remakeInstructionIdsAndListeners();
    });

    // Injects new Instruction html for inputs at bottom of the list with a fade in animation
    function addInstruction() {
        var html = '<div class="col-sm-12 instructionItem" id="instructId0" style="display: none">' +
            '<div class="row">' +
            '<div class="col-sm-2">' +
            '<button type="button" class="btn btn-outline-danger btn-block rmvInstructButton" id="removeInstruct0">&#8998;' +
            '</button>' +
            '</div>' +
            '<div class="col-sm-10">' +
            '<input class="form-control" type="text" name="instructions_text"/>' +
            '</div>' +
            '</div>' +
            '</div>';
        $('#instructionList').append(html);
        $('.instructionItem').filter(':last').show('slow');
    }

    // Recreates the the ids on instructionItems and each of their delete buttons
    // Refreshed the click listeners on the delete buttons
    function remakeInstructionIdsAndListeners() {
        $('.instructionItem').each(function (index) {
            var idx = 'instructId' + index;
            $(this).attr('id', idx);
        });
        $('.rmvInstructButton').each(function (index) {
            var idx2 = 'removeInstruct' + index;
            $(this).attr('id', idx2);
            $(this).off('click');
            $(this).click(function () {
                var instructionItem = $(this).closest('.instructionItem');
                instructionItem.hide('slow');
                setTimeout(function () {
                    instructionItem.remove();
                }, 600);
            })
        });
    }

    // Delete check. If checkbox isn't checked, it's submit button is disabled
    $('#deleteBox').click(function () {
        if ($('#deleteBox').is(':checked')) {
            $('#deleteButton').removeAttr('disabled');
        } else {
            $('#deleteButton').attr('disabled', 'disabled');
        }
    });

    $('#save-to-stockpile').click(function () {
        $('#multi_select').slideToggle();
    });

    $('.carousel').carousel({
        interval: 5000
    });

});
