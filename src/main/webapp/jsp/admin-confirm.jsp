<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Admin</title>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <link rel="stylesheet" href="../css/admin.css">
        <jsp:include page="needl-header.jsp"/>
    </head>
    <body>
        <div class="wrapper">
            <h1>Invitation success</h1>
            <p>I can&#39;t wait :)</p>
            <%-- Back to invite user --%>
            <form method="GET" id="adminInviteForm" action="${pageContext.request.contextPath}/admin_inviteUser">
                <button type="submit">Invite User</button>
            </form>
            <%-- Back to admin --%>
            <form method="GET" id="backButton" action="${pageContext.request.contextPath}/admin">
                <button type="submit">Admin</button>
            </form>
        </div>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</html>