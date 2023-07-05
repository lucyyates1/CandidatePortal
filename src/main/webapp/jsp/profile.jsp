<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title>User Profile</title>
        <jsp:include page="header.jsp"/>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <link rel="stylesheet" href="../css/profile.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="../js/profile.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>

        </style>
    </head>
    <body>
        <div class="wrapper">
            <h2 class="apply-header">Profile Info</h2>
            <div class="info-container">
                <span class="label">Username:</span>
                <span class="info">${user.username}</span>
                <button onclick="openUser()">Change Username</button>
                <span class="error">${usernameError}</span>
                <div id="username-form">
                    <form method="POST" action="/changeusername" id="change-username">
                        <input class="change-info" name="username" type="text" placeholder="Enter New Username"/>
                        <button type="submit">Confirm</button>
                        <button type="button" onclick="closeUser()">Cancel</button>
                    </form>
                </div>
            </div>
            <div class="info-container">
                <span class="label">Password:</span>
                <button onclick="openPassword()">Change Password</button>
                <span class="error">${passwordError}</span>
                <div id="password-form">
                    <form method="POST" action="/changepassword" id="change-password">
                        <input class="change-info" name="originalPassword" type="password" placeholder="Enter Current Password"/>
                        <div class="password">
                            <input class="formInput" type="password" name="newPassword" placeholder="Enter New Password" spellcheck="false"/>
                            <div class="tooltip" style="right: 37%;">
                                <span class="tooltipText">Password must be at least seven (7) characters long, and contain one (1) number, one (1) uppercase letter, one (1) lowercase letter, and one (1) special character.</span>
                                <div class="infolink" style="font-size: 20px"></div>
                            </div>
                        </div>
                        <input class="change-info" name="confirmPassword" type="password" placeholder="Confirm New Password"/>
                        <button type="submit">Confirm</button>
                        <button type="button" onclick="closePassword()">Cancel</button>
                    </form>
                </div>
            </div>
            <div class="info-container">
                <span class="label">First Name:</span>
                <span class="info">${user.first_name}</span>
                <button onclick="openFirstName()">Change First Name</button>
                <div id="first-name-form">
                    <form method="POST" action="/changefirstname" id="change-first-name">
                        <input class="change-info" name="firstname" type="text" placeholder="Enter First Name"/>
                        <button type="submit">Confirm</button>
                        <button type="button" onclick="closeFirstName()">Cancel</button>
                    </form>
                </div>

            </div>
            <div class="info-container">
                <span class="label">Last Name:</span>
                <span class="info">${user.last_name}</span>
                <button onclick="openLastName()">Change Last Name</button>
                <div id="last-name-form">
                    <form method="POST" action="/changelastname" id="change-last-name">
                        <input class="change-info" name="lastname" type="text" placeholder="Enter Last Name"/>
                        <button type="submit">Confirm</button>
                        <button type="button" onclick="closeLastName()">Cancel</button>
                    </form>
                </div>
            </div>
            <div class="info-container">
                <span class="label">Email:</span>
                <span class="info">${user.email}</span>
                <button onclick="openEmail()">Change Email</button>
                <span class="error">${emailError}</span>
                <div id="email-form">
                    <form method="POST" action="/changeEmail" id="change-email">
                        <input class="change-info" name="newEmail" type="text" placeholder="Enter New Email"/>
                        <input class="change-info" name="confirmEmail" type="text" placeholder="Confirm New Email"/>
                        <button type="submit" id="button_apply">Confirm</button>
                        <button type="button" onclick="closeEmail()">Cancel</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>
</html>