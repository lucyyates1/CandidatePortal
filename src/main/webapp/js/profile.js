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
    $("#change-username").submit(function (event) {
        event.preventDefault();
        post_username($(this));
    });
    $("#change-password").submit(function (event) {
        event.preventDefault();
        post_password($(this));
    });
    $("#change-first-name").submit(function (event) {
        event.preventDefault();
        post_firstname($(this));
    });
    $("#change-last-name").submit(function (event) {
        event.preventDefault();
        post_lastname($(this));
    });
    $("#change-email").submit(function (event) {
        event.preventDefault();
        post_email($(this));
    });
});

function post_username(form){
    var actionUrl = form.attr('action');
    var formdata = new FormData(form[0]);
    $.ajax({
        type: "POST",
        url: '/changeusername',
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

function post_password(form){
    var actionUrl = form.attr('action');
    var formdata = new FormData(form[0]);
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

function post_firstname(form){
    var actionUrl = form.attr('action');
    var formdata = new FormData(form[0]);
    $.ajax({
        type: "POST",
        url: '/changefirstname',
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

function post_lastname(form){
    var actionUrl = form.attr('action');
    var formdata = new FormData(form[0]);
    $.ajax({
        type: "POST",
        url: '/changelastname',
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

function post_email(form){
    var actionUrl = form.attr('action');
    var formdata = new FormData(form[0]);
    $.ajax({
        type: "POST",
        url: '/changeEmail',
        processData: false,
        contentType: false,
        data: formdata ? formdata : form.serialize(),

        success: function (response, textStatus) {
            window.location.replace("/profile?code=" + response);
        },

        error: function (errMsg) {
            console.log(errMsg.toString())
        }
    });
}

function openUser(){
    document.getElementById("username-form").style.display = "block";
    document.getElementById("password-form").style.display = "none";
    document.getElementById("first-name-form").style.display = "none";
    document.getElementById("last-name-form").style.display = "none";
    document.getElementById("email-form").style.display = "none";
}

function closeUser(){
    document.getElementById("username-form").style.display = "none";
}

function openPassword(){
    document.getElementById("password-form").style.display = "block";
    document.getElementById("username-form").style.display = "none";
    document.getElementById("first-name-form").style.display = "none";
    document.getElementById("last-name-form").style.display = "none";
    document.getElementById("email-form").style.display = "none";
}

function closePassword(){
    document.getElementById("password-form").style.display = "none";
}

function openFirstName(){
    document.getElementById("first-name-form").style.display = "block";
    document.getElementById("password-form").style.display = "none";
    document.getElementById("username-form").style.display = "none";
    document.getElementById("last-name-form").style.display = "none";
    document.getElementById("email-form").style.display = "none";
}

function closeFirstName(){
    document.getElementById("first-name-form").style.display = "none";
}

function openLastName(){
    document.getElementById("last-name-form").style.display = "block";
    document.getElementById("password-form").style.display = "none";
    document.getElementById("username-form").style.display = "none";
    document.getElementById("first-name-form").style.display = "none";
    document.getElementById("email-form").style.display = "none";
}

function closeLastName(){
    document.getElementById("last-name-form").style.display = "none";
}

function openEmail(){
    document.getElementById("email-form").style.display = "block";
    document.getElementById("password-form").style.display = "none";
    document.getElementById("username-form").style.display = "none";
    document.getElementById("last-name-form").style.display = "none";
    document.getElementById("first-name-form").style.display = "none";
}

function closeEmail(){
    document.getElementById("email-form").style.display = "none";
}