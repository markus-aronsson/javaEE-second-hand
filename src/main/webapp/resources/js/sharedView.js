/***********************************
 * 
 * Javascript for the gradings
 * and sliders
 * 
 */

$(document).ready(function () {
    
    // Decorate ratings
    $('.star').raty({
        score: function() {
            return $(this).attr('data-score');
        },
        readOnly: true,
        space: false
    });
    
    $('.write-star').raty({
        score: function() {
            return $(this).attr('data-score');
        },
        cancel: true,
        width: "100%",
        target: "[name*='ratingInputField']",
        targetType: 'number',
        targetKeep: true,
        mouseout: function(score, evt) {
            if ($("[name*='ratingInputField']").val() === "") {
                $("[name*='ratingInputField']").val(0);
            }
        }
    });
    
    $( "#distanceSlider" ).slider({
        range: "min",
        min: 0,
        max: 4,
        value: $("[name*='distanceInputField']").val(),
        slide: function( event, ui ) {
            $( "[name*='distanceInputField']" ).val(ui.value);
        }
    });
    
    // Decorate filter button
    $("#toggleFilterButton").button({
        icons: {
            primary: "ui-icon-circle-plus"
        },
        text: false
    });
});