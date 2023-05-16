<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Register</title>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <style>
            div.wrapper {
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 0;
                width: 100%;
                height: 100%;
            }
            div.localWrapper {
                display: flex;
                flex-direction: column;
                justify-content: center;
            }
            input[type="submit"] {
                font-size: clamp(1rem, 1vw, 1.5rem);
                width: auto;
                padding: 2%;
                margin-right: 2%;
                display: block;
                cursor: pointer;
                font-weight: bold;
            }
            form {
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            .submitContainer {
                display: flex;
                flex-direction: row;
                justify-content: center;
                align-items: center;
                width: 100%;
            }
            .selectMod {
                color: black;
            }
            .password {
                display: flex;
                flex-direction: row;
                justify-content: center;
                align-items: center;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
        <div class="localWrapper">
            <h2>Register</h2>
            <span class="good">${registrationSuccess}</span>
            <form:form name="RegForm" onsubmit="return validate()" action="${pageContext.request.contextPath}/register" method="POST">
                <span class="error">${firstnameError}</span>
                <input class="formInput" type="text" name="first_name" placeholder="First name"/>
                <span class="error">${lastnameError}</span>
                <input class="formInput" type="text" name="last_name" placeholder="Last name"/>
                <span class="error">${usernameError}</span>
                <input class="formInput" type="text" name="username" placeholder="Username" spellcheck="false"/>
                <span class="error">${emailError}</span>
                <input class="formInput" type="text" name="email" placeholder="Email" spellcheck="false"/>
                <span class="error">${passwordError}</span>
                <div class="password">
                    <input class="formInput" type="password" name="password" placeholder="Password" spellcheck="false"/>
                    <div class="tooltip" style="right: 37%;">
                        <span class="tooltipText">Password must be at least seven (7) characters long, and contain one (1) number, one (1) uppercase letter, one (1) lowercase letter, and one (1) special character.</span>
                        <div class="infolink" style="font-size: 20px"></div>
                    </div>
                </div>
                <span class="error">${passwordConfirmError}</span>
                <input class="formInput" type="password" name="passwordConfirm" placeholder="Confirm Password" spellcheck="false"/>
                <div class="submitContainer">
                    <input type="submit" value="Register"/>
                    <p class="account">Already registered?
                        <a href="/login">Log In</a>
                    </p>
                </div>
            </form:form>
        </div>
        </div>
    </body>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        /*
        // Form submission validation
        function validate() {
            var username = document.forms["RegForm"]["username"];
            var email = document.forms["RegForm"]["email"];
            var password = document.forms["RegForm"]["password"];
            var passwordConfirm = document.forms["RegForm"]["passwordConfirm"];

            if (username.value == "") {
                window.alert("Please enter a username.");
                username.focus();
                return false;
            } else if (email.value == "") {
                window.alert("Please enter your email.");
                username.focus();
                return false;
            } else if (password.value == "") {
                window.alert("Please enter a password.");
                username.focus();
                return false;
            } else if (passwordConfirm.value == "") {
                window.alert("Please confirm your password.");
                username.focus();
                return false;
            }

            /*if () {
                window.alert("");
                return false;
            }*/
            return true;
        }
        */
        // Ensures text is grey if default option in drop down menu is selected
        $(document).ready(function(){
            $('option').on('click', function(){
                if ($(this).attr('id') === "default")
                    $(this).parent().removeClass("selectMod")
                else
                    $(this).parent().addClass("selectMod")
            })
        });
    </script>
</html>