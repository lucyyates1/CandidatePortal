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
        <style>

        .available-bar{
            display: grid;
            grid-template-columns: 100px 100px 100px 100px 100px 100px 100px 100px 100px 100px 100px 100px 100px 100px;
            border-top-style: solid;
            border-bottom-style: solid;
            border-left-style: solid;
            border-color: black;
        }
        .open{
            background-color: var(--geek-purple);
            border-right-style: solid;
        }
        .closed{
            border-right-style: solid;
        }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <h1>Availability</h1>
            <div class="innerWrapper">
                <div>
                    <c:forEach var="date" items="${dates}">
                        <c:if test="${available.get(date).size() != 0}">
                            <div>
                                <h3>${date}</h3>
                                <div class="available-bar">
                                    <c:forEach var="time" items="${times}">
                                        <c:if test="${available.get(date).contains(time)}">
                                            <div class="open">
                                                ${time}
                                            </div>
                                        </c:if>
                                        <c:if test="${!(available.get(date).contains(time))}">
                                            <div class="closed">
                                                ${time}
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>
</html>