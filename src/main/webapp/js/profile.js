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
    $("#change-password").submit(function (event) {
        event.preventDefault();
        post_password($(this));
    });
    $('#edit-user').on('click', function(){
        $.ajax({
            type: "GET",
            url: '/profile/edit',
            success: function (position) {
                window.location = "profile/edit";
            },

            failure: function (errMsg) {
                console.log(errMsg.toString())
            }
        });
    });
    $('#upload-resume').submit(function (event) {
        event.preventDefault();
        post_resume($(this));
    });
});

function post_password(form){
    var actionUrl = form.attr('action');
    var formdata = new FormData(form[0]);
    document.getElementById("password-form").style.maxHeight = "0px";
    document.getElementById("password-info").style.display = "none";
    $.ajax({
        type: "POST",
        url: '/changepassword',
        processData: false,
        contentType: false,
        data: formdata ? formdata : form.serialize(),

        success: function (response, textStatus) {
            console.log(response);
            window.location.replace("/profile?code=" + response);
        },

        error: function (errMsg) {
            console.log(errMsg.toString())
        }
    });
}

function post_resume(form){
    var actionUrl = form.attr('action');
    var formdata = new FormData(form[0]);

    $.ajax({
        type: "POST",
        url: '/addResume',
        processData: false,
        contentType: false,
        data: formdata,

        success: function (response, textStatus) {
            console.log(response);
            window.location.replace("/profile?code=7");
        },

        error: function (errMsg) {
            console.log(errMsg.toString())
        }
    });
}

function openPassword(){
    document.getElementById("password-form").style.maxHeight = "280px";
    document.getElementById("password-info").style.display = "block";
}

function closePassword(){
    document.getElementById("password-form").style.maxHeight = "0px";
    document.getElementById("password-info").style.display = "none";
}

function openResume(){
    document.getElementById("grey-out").style.display = "block";
    document.getElementById("pop-up-form").style.display = "block";
}

function closeResume(){
    document.getElementById("grey-out").style.display = "none";
    document.getElementById("pop-up-form").style.display = "none";
}
