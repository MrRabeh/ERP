/* ====== BOX TOOGLE ===== */$("div.hide").click(function() {
    $(".box .block", this).toggle("slow");
    $(this).toggleClass("show").next().slideToggle("medium");
});
/* ====== ALERTS ====== */$('.alert').click(function() {
    $(this).hide('normal');
});
/* ====== GALLERY ====== */$(function() {
    $('ul.gallery li').hover(function() {
        $('img', this).animate({"opacity": "0.6"}, {queue: true, duration: 150});
    }, function() {
        $('img', this).animate({"opacity": "1"}, {queue: true, duration: 150});
    });
});
/* ======== FORMS ======*/$(function() {
    $("input:checkbox, input:radio, input:file").uniform();
});
$(".chzn-select").chosen();
$(".chzn-select-deselect").chosen({allow_single_deselect: true});
/* ======== Placeholder ======*/$(function() {
    $('input, textarea').placeholder();
});