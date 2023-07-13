<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title>Edit User Profile</title>
        <jsp:include page="header.jsp"/>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <link rel="stylesheet" href="../css/profile.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="../js/profile-edit.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>

        </style>
    </head>
    <body>
        <div class="wrapper">
            <div class="inner-wrap">
                <h2 class="apply-header">Profile Info</h2>
                <p class="${style}" id="response" style="text-align: center;">${status}</p>
                <form method="POST" action="/editUser" id="edit-user">
                    <div class="info-container">
                        <span class="label">Username:</span>
                        <input class="change-info" name="username" type="text" value="${user.username}" required/>
                    </div>
                    <div class="info-container">
                        <span class="label">First Name:</span>
                        <input class="change-info" name="firstname" type="text" value="${user.first_name}" required/>
                    </div>
                    <div class="info-container">
                        <span class="label">Last Name:</span>
                        <input class="change-info" name="lastname" type="text" value="${user.last_name}" required/>
                    </div>
                    <div class="info-container">
                        <span class="label">Email:</span>
                        <input class="change-info" name="newEmail" type="text" value="${user.email}" required/>
                    </div>
                    <div class="button-grid">
                        <button type="button" id="return-click">Cancel</button>
                        <button type="submit" id="confirm-form">Confirm</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>
</html>