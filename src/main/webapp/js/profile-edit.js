$(document).ready(function(){
    // Adding CSRF token to AJAX header
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(function () {
        var token = $("input[name='_csrf']").val();
        var header = "X-CSRF-TOKEN";
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });
    $("#edit-user").submit(function (event) {
        event.preventDefault();
        post_user($(this));
    });
    $('#return-click').on('click', function(){
        $.ajax({
            type: "GET",
            url: '/profile',
            success: function (position) {
                window.location = "/profile";
            },

            failure: function (errMsg) {
                console.log(errMsg.toString())
            }
        });
    });
});

function post_user(form){
    var actionUrl = form.attr('action');
    var formdata = new FormData(form[0]);
    $.ajax({
        type: "POST",
        url: '/editUser',
        processData: false,
        contentType: false,
        data: formdata ? formdata : form.serialize(),

        success: function (response, textStatus) {
            console.log(response);
            window.location.replace("/profile/edit?code=" + response);
        },

        error: function (errMsg) {
            console.log(errMsg.toString())
        }
    });
}