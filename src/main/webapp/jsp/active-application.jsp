<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title>Active Applications</title>
        <jsp:include page="header.jsp"/>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <link rel="stylesheet" href="../css/active-application.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="../js/index.js"></script>
    </head>
    <body>
        <div class="wrapper">
            <h1 class="header" >Currently Active Applications</h1>
            <div class="contentTableWrapper">
                <!-- Iterate through list and populate table -->
                <c:if test="${listCandidates.size() == 0}">
                    <div style="display:flex; flex-direction: column;">
                        <p style="font-weight: bold;font-size: 20px;text-align: center;">This is where you would see active applications and scheduled interview dates</p>
                        <p style="font-weight: bold;font-size: 17px;padding-top: 10px;text-align: center;">Apply to a position to see active updates!</p>
                        <button class="positions" style="width: 25%; align-self: center;">GET STARTED</button>
                    </div>
                </c:if>
                <c:if test="${listCandidates.size() != 0}">
                    <table id="active_recruiting_positions" class="contentTable">
                        <thead>
                            <tr>
                                <th>Position Name</th>
                                <th>Date Applied</th>
                                <th>Current Status</th>
                                <th>Scheduled Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="candidate" begin="0" end="${listCandidates.size() - 1}">
                                <tr id="${listPositions.get(candidate).getPosition_id()}">
                                <td class="positionName">${listPositions.get(candidate).name}</td>
                                <c:if test="${listCandidates.get(candidate).getInitial_contact_date() != null}">
                                    <td>${listCandidates.get(candidate).getInitial_contact_date().format(formatter)}</td>
                                </c:if>
                                <c:if test="${listCandidates.get(candidate).getInitial_contact_date() == null}">
                                    <td></td>
                                </c:if>
                                <c:if test="${!(listCandidates.get(candidate).archived)}" >
                                    <c:if test="${listCandidates.get(candidate).getMeet_and_greet_date() == null}">
                                        <td>Under Review</td>
                                        <td>N/A</td>
                                    </c:if>
                                    <c:if test="${listCandidates.get(candidate).getMeet_and_greet_date() != null}">
                                        <c:if test="${listCandidates.get(candidate).getTechnical_interview_date() == null}">
                                            <td>Meet And Greet</td>
                                            <td>${listCandidates.get(candidate).getMeet_and_greet_date().format(formatter)}</td>
                                        </c:if>
                                        <c:if test="${listCandidates.get(candidate).getTechnical_interview_date() != null}">
                                            <c:if test="${listCandidates.get(candidate).getOffer_date() == null}">
                                                <td>Technical Interview</td>
                                                <td>${listCandidates.get(candidate).getTechnical_interview_date().format(formatter)}</td>
                                            </c:if>
                                            <c:if test="${listCandidates.get(candidate).getOffer_date() != null}">
                                                <td>Offered Position</td>
                                                <td>${listCandidates.get(candidate).getOffer_date().format(formatter)}</td>
                                            </c:if>
                                        </c:if>
                                    </c:if>
                                </c:if>
                                <c:if test="${listCandidates.get(candidate).archived}">
                                    <td>Archived</td>
                                    <td>N/A</td>
                                </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="../js/active-application.js"></script>
</html>