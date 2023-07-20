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
                            <label for="calender" class="input_label">Availability For Interview:</label>
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
                                <c:forEach var="day" items="${daysBefore}">
                                    <li class="non-select">${day.getDayOfMonth()}</li>
                                </c:forEach>
                                <li class="non-select">${currentDay.getDayOfMonth()}</li>
                                <c:forEach var="day" items="${daysAfter}">
                                    <c:if test="${day.getDayOfWeek().getValue() < 6}">
                                        <li id="${day.getDayOfMonth()}" class="day-option">${day.getDayOfMonth()}</li>
                                    </c:if>
                                    <c:if test="${day.getDayOfWeek().getValue() >= 6}">
                                        <li class="non-select">${day.getDayOfMonth()}</li>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                        <div style="margin-left: 30%;">
                            <c:forEach var="day" items="${daysAfter}">
                                <c:if test="${day.getDayOfWeek().getValue() < 6}">
                                    <div id="${day}" class="timeCollapse">
                                        <input type="checkbox" id="${day} 10:00" name="${day}" value="10:00">
                                        <label for="${day} 10:00">10:00 AM</label>
                                        <input type="checkbox" id="${day} 10:30" name="${day}" value="10:30">
                                        <label for="${day} 10:30">10:30 AM</label>
                                        <input type="checkbox" id="${day} 11:00" name="${day}" value="11:00">
                                        <label for="${day} 11:00">11:00 AM</label>
                                        <input type="checkbox" id="${day} 11:30" name="${day}" value="11:30">
                                        <label for="${day} 11:30">11:30 AM</label>
                                        <input type="checkbox" id="${day} 12:00" name="${day}" value="12:00">
                                        <label for="${day} 12:00">12:00 PM</label>
                                        <input type="checkbox" id="${day} 12:30" name="${day}" value="12:30">
                                        <label for="${day} 12:30">12:30 PM</label>
                                        <input type="checkbox" id="${day} 1:00" name="${day}" value="1:00">
                                        <label for="${day} 1:00">1:00 PM</label>
                                        <input type="checkbox" id="${day} 1:30" name="${day}" value="1:30">
                                        <label for="${day} 1:30">1:30 PM</label>
                                        <input type="checkbox" id="${day} 2:00" name="${day}" value="2:00">
                                        <label for="${day} 2:00">2:00 PM</label>
                                        <input type="checkbox" id="${day} 2:30" name="${day}" value="2:30">
                                        <label for="${day} 2:30">2:30 PM</label>
                                        <input type="checkbox" id="${day} 3:00" name="${day}" value="3:00">
                                        <label for="${day} 3:00">3:00 PM</label>
                                        <input type="checkbox" id="${day} 3:30" name="${day}" value="3:30">
                                        <label for="${day} 3:30">3:30 PM</label>
                                        <input type="checkbox" id="${day} 4:00" name="${day}" value="4:00">
                                        <label for="${day} 4:00">4:00 PM</label>
                                        <input type="checkbox" id="${day} 4:30" name="${day}" value="4:30">
                                        <label for="${day} 4:30">4:30 PM</label>
                                    </div>
                                </c:if>
                                <c:if test="${day.getDayOfWeek().getValue() >= 6}">
                                <div style="display: none;"></div>
                                </c:if>
                            </c:forEach>
                            <button style="margin: 50px; width: 40%" type="submit" id="button_apply">Submit</button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>
    <script>
        <c:forEach var="day" items="${daysAfter}">
            $("#${day.getDayOfMonth()}").on('click', function(){
                    var content = document.getElementById("${day}");
                    var otherOnElements = document.getElementsByClassName("time-on");
                    var otherDays = document.getElementsByClassName("active");
                    if (content.style.maxHeight){
                        this.classList.remove('active');
                        content.style.maxHeight = null;
                        content.classList.remove("time-on")
                    } else {
                        Array.from(otherDays).forEach(function (element) {
                            element.classList.remove('active');
                        });
                        Array.from(otherOnElements).forEach(function (element) {
                            element.classList.remove('time-on');
                            element.style.maxHeight = null;
                        });
                        this.classList.toggle('active');
                        content.classList.add("time-on")
                        content.style.maxHeight = 330 + "px";
                    }
            });
        </c:forEach>
    </script>
</html>