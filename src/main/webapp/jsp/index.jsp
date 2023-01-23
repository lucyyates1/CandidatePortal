<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title>Needl</title>
        <jsp:include page="header.jsp"/>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <link rel="stylesheet" href="../css/index.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="../js/index.js"></script>
    </head>
    <body>
        <div class="wrapper">
            <div class="innerWrapper">
                <div class="left">
                    <h1 style="padding-bottom: 1rem;">Welcome Candidate!&trade; ${username}!</h1>

                    <p class="tagLine">Welcome to Needl! This page contains all active positions to apply for.\n Take a look around!</p>


                    <h3 style="margin-top: 2rem;">Current Positions:</h3>
                    <c:if test="${listPositions.size() > 0}">
                        <table id="latest_positions" class="contentTable">
                            <thead>
                                <tr>
                                    <th>Position</th>
                                    <th>Date Posted</th>
                                    <th>Company</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- Iterate through list and populate table -->
                                <c:forEach var="position" begin="0" end="${listPositions.size() - 1}">
                                    <tr id="${listPositions.get(position).position_id}">
                                    <td class="positionName">${listPositions.get(position).name}</td>
                                    <c:if test="${listPositions.get(position).date != null}">
                                        <td>${listPositions.get(position).date.format(formatter)}</td>
                                    </c:if>
                                    <c:if test="${listPositions.get(position).date == null}">
                                        <td></td>
                                    </c:if>
                                    <c:if test="${listFilDates.get(position) != null}">
                                        <td>${listFilDates.get(position).format(formatter)}</td>
                                    </c:if>
                                    <c:if test="${listFilDates.get(position) == null}">
                                        <td></td>
                                    </c:if>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${listPositions.size() < 1}">
                        <h3>There are currently no active positions</h3>
                    </c:if>
                </div>
                <div class="right">
                    <div class="rightTop">
                        <a class="geeksi" href="https://www.geeksi.tech/">
                            <img style="width: 50%;" src="../assets/geeksi-logo-geekpride.png"/>
                        </a>
                    </div>
                    <div class="rightBottom">
                        <h1 style="padding-bottom: 1rem;">About</h1>

                        <h3 class="about">Purpose of Needl.</h3>
                        <br>
                        <p class="aboutContent">Needl is an application that is actively being developed by GeekSI to assist their recruiting team and amplify their recruiting power. GeekSI's recruiting team is second to none, so we're working hard to make sure we can say the same about Needl!</p>
                        <h3 class="about" style="margin-top: 2rem;">Geeks Develop Software.<br>We Do It Brilliantly.</h3>
                        <br>
                        <p class="aboutContent">GeekSI forges trust through integrity and hard work, delivering outstanding technology solutions that bring measurable value to our customers.</p>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>
</html>