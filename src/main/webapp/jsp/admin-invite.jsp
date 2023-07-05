<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Admin</title>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <link rel="stylesheet" href="../css/admin.css">
        <style>
            .labelAndInputWrapper {
                display: flex;
                justify-content: left;
                max-height: 3rem;
                padding: 1rem;
            }
            label {
                padding: 0;
                font-size: 1.5rem;
            }
            input[type="text"] {
                margin: 0;
                margin-left: 1rem;
            }
        </style>
        <jsp:include page="header.jsp"/>
    </head>
    <body>
        <div class="wrapper">
            <h1>Invite New Users</h1>
            <form modelAttribute="adminSearchUser" method="POST" id="adminSearchForm"
            action="${pageContext.request.contextPath}/admin_inviteUserConfirm">
                <p>
                    Invite a new user to the application by filling out the form below, using
                    the invitee&#39;s information.
                </p>
                <div class="labelAndInputWrapper">
                    <label for="nameInputElement">Full name: </label>
                    <input type="text" path="invitee_name" id="nameInput" name="nameInputElement"></input>
                </div>
                <div class="labelAndInputWrapper">
                    <label for="emailInputElement">Email: </label>
                    <input type="text" path="invitee_email" id="emailInput" name="emailInputElement"></input>
                </div>
                <input type="submit" value="Submit" name="submit_button" id="submit_button">
            </form>
            <form method="GET" id="backButton" action="${pageContext.request.contextPath}/admin">
                <button type="submit">Back</button>
            </form>
        </div>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</html>