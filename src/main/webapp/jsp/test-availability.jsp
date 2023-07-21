<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title>Test Availability</title>
        <jsp:include page="needl-header.jsp"/>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <link rel="stylesheet" href="../css/index.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="../js/index.js"></script>
    </head>
    <body>
        <div class="wrapper">
            <h1>Availability</h1>
            <div class="innerWrapper">
                <div>
                    <c:forEach var="date" items="${available.keySet()}">
                        <h3>${date}</h3>
                        <ul>
                            <c:forEach var="time" items="${available.get(date)}">
                                <li>${time}</li>
                            </c:forEach>
                        </ul>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>
</html>