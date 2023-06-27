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