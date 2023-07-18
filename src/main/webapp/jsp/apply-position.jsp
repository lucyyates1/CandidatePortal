<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title>Application</title>
        <jsp:include page="header.jsp"/>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <link rel="stylesheet" href="../css/apply-position.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://malsup.github.io/jquery.form.js"></script>
        <script src="../js/apply-position.js"></script>
        <script type="text/javascript"></script>
    </head>
    <body>
        <div id="grey-out">
        </div>
        <div id="pop-up-form">
            <div>
                <button type="button" style="background: #f97b7b;" onclick="closeResume()">X</button>
                <h3 style="display: inline; padding-left: 35px; text-decoration: underline;">Add Resume</h3>
            </div>
            <form method="POST" action="/addResume" id="upload-resume"  enctype="multipart/form-data">
                <div id="pop-up-div">
                    <input type="file" name="resume" accept=".doc,.docx,.pdf" required/>
                    <button type"submit" style="width: 150px; font-size: 16px;">Save Resume</button>
                </div>
            </form>
        </div>
        <div class="description-box">
            <div class="info">
                <h2 style="color: black; padding: 20px;" >${selectedPosition.name}</h2>
            </div>
            <button type="button" class="collapsible">Position Information</button>
            <div class="collapsibleContent">
                <table id="position_information_description" class="displayTable" style="width: 100%; background-color: var(--grey-light); border-radius: 0;">
                    <tr>
                        <td class="title">Description: </td>
                        <td>${selectedPosition.description}</td>
                    </tr>
                </table>
            </div>
            <div id="other_info_container">
                <table id="position_information_other" class="displayTable" style="width: 100%; background-color: var(--grey-light); border-radius: 0;">
                    <tr>
                        <td class="title">Education:</td>
                        <c:if test="${selectedPosition.isEducation_required()}" >
                            <td>${selectedPosition.getEducation()} (Required)</td>
                        </c:if>
                        <c:if test="${!(selectedPosition.isEducation_required())}" >
                            <td>${selectedPosition.getEducation()} (Preferred)</td>
                        </c:if>
                    </tr>
                    <tr>
                        <td class="title">Skills:</td>
                        <c:if test="${selectedPosition.getPosition_skills().size() > 0}">
                            <td>
                                <div>
                                    <ul style="display: inline;">
                                        <c:forEach var="skill" items="${skills}">
                                            <li style="display: inline;">
                                                <p class="bubble">${skill.name}</p>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </td>
                        </c:if>
                        <c:if test="${selectedPosition.getPosition_skills().size() == 0}">
                            <td></td>
                        </c:if>
                    </tr>
                    <tr>
                        <td class="title">Certifications:</td>
                        <c:if test="${selectedPosition.getPosition_certification().size() > 0}">
                            <td>
                                <div>
                                    <ul style="display: inline;">
                                        <c:forEach var="certification" items="${certifications}">
                                            <li style="display: inline;">
                                                <p class="bubble">${certification.name}</p>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </td>
                        </c:if>
                        <c:if test="${selectedPosition.getPosition_certification().size() == 0}">
                            <td></td>
                        </c:if>
                    </tr>
                    <tr>
                        <td class="title">Date Posted:</td>
                        <td>${selectedPosition.date.format(formatter)}</td>
                    </tr>
                    <tr>
                        <td class="title">Place of Performance:</td>
                        <td>${selectedPosition.place_of_performance}</td>
                    </tr>
                    <tr>
                        <td class="title">Job Type:</td>
                        <td>${type}</td>
                    </tr>
                    <tr>
                        <td class="title">Virtual:</td>
                        <td>${virtual}</td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="wrapper">
            <h2 class="apply-header">Application Page</h2>
            <div class="innerWrapper">
                <div class="right">
                    <form:form method="POST" action="/applyposition" id="candidate-form"  enctype="multipart/form-data">
                        <div class="generic_section" id="candidate-form">
                            <label for="position" class="input_label">Position Applying for:</label>
                            <select name="position-id" id="position">
                                <c:forEach var="pos" items="${positions}">
                                    <c:if test="${pos == selectedPosition}">
                                        <option selected="true" value="${pos.position_id}" >${pos.name}</option>
                                    </c:if>
                                    <c:if test="${pos != selectedPosition}">
                                        <option value="${pos.position_id}">${pos.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <label for="candidate-first-name" class="input_label">First Name (*):</label>
                            <div>
                                <input name="first-name" type="text" id="candidate-first-name" value=${user.first_name} required/>
                            </div>
                            <label for="candidate-last-name" class="input_label">Last Name (*):</label>
                            <div>
                                <input name="last-name" type="text" id="candidate-last-name" value=${user.last_name} required/>
                            </div>
                            <!-- FILE UPLOADS FOR RESUME -->
                            <label for="resume-upload" class="input_label">Resume (*):</label>
                            <div>
                                <c:if test="${fileNames.size() == 0}">
                                    <input id="resume-upload" type="file" name="resume-upload" accept=".doc,.docx,.pdf" required/>
                                </c:if>
                                <c:if test="${fileNames.size() > 0}">
                                    <select name="resume" id="resume-upload" required>
                                        <c:forEach var="resume" items="${fileNames}">
                                            <option value="${resume}">${resume}</option>
                                        </c:forEach>
                                    </select>
                                    <button type="button" onclick="openResume()">Upload New Resume</button>
                                </c:if>
                            </div>
                            <!-- FILE UPLOADS FOR COVER LETTER -->
                            <!-- Need to Add a Cover Letter Path to the candidate table -->
                            <label for="cover-letter-upload" class="input_label">Cover Letter:</label>
                            <input id="cover-letter-upload" type="file" name="cover-letter" accept=".doc,.docx,.pdf" />
                            <label for="candidate-notes" class="input_label">Comments:</label>
                            <textarea name="notes" type="text" id="candidate-notes" style="height: 200px; width: 70%;"></textarea>
                            <label for="calender" class="input_label">Availability:</label>
                            <div id="calender">
                                <ul class="weekdays">
                                    <li>Su</li>
                                    <li>Mo</li>
                                    <li>Tu</li>
                                    <li>We</li>
                                    <li>Th</li>
                                    <li>Fr</li>
                                    <li>Sa</li>

                                </ul>
                                <ul class="days">
                                <c:forEach var="day" begin="${firstDay}" end="${currentDay - 1}">
                                    <li class="non-select">${day}</li>
                                </c:forEach>
                                <li class="currentDay">${currentDay}</li>
                                <c:forEach var="day" begin="${currentDay + 1}" end="${currentDay + 7}">
                                    <li class="day-option">${day}</li>
                                </c:forEach>
                                <c:forEach var="day" begin="${currentDay + 8}" end="${lastDay}">
                                    <li class="non-select">${day}</li>
                                </c:forEach>
                            </div>
                            <p></p>
                            <button style="margin: 50px; width: 40%" type="submit" id="button_apply">Submit</button>
                            <!--Change to JS-->
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>
</html>