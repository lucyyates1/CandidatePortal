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
        <style>

        </style>
    </head>
    <body>
    <div class="wrapper">
        <h2 class="apply-header">Application Page</h2>
        <div class="innerWrapper">
            <div class="left">
                <div class="info">
                    <h2 style="color: black; padding: 20px;" >${selectedPosition.name}</h2>
                    <hr>
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
            </div>
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
                            <input name="first-name" type="text" id="candidate-first-name" />
                            <span class="error">${firstnameError}</span>
                        </div>
                        <label for="candidate-last-name" class="input_label">Last Name (*):</label>
                        <div>
                            <input name="last-name" type="text" id="candidate-last-name" />
                            <span class="error">${lastnameError}</span>
                        </div>
                        <!-- FILE UPLOADS FOR RESUME -->
                        <label for="resume-upload" class="input_label">Resume (*):</label>
                        <div>
                            <input id="resume-upload" type="file" name="resume"/>
                            <span class="error">${resumeError}</span>
                        </div>
                        <!-- FILE UPLOADS FOR COVER LETTER -->
                        <!-- Need to Add a Cover Letter Path to the candidate table -->
                        <label for="cover-letter-upload" class="input_label">Cover Letter:</label>
                        <input id="cover-letter-upload" type="file" name="cover-letter" />
                        <label for="candidate-notes" class="input_label">Comments:</label>
                        <textarea name="text" type="text" id="candidate-notes" style="height: 200px; width: 70%;"></textarea>
                        <p></p>
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
        $(document).ready(function(){
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            const queryString = window.location.search
            const urlParams = new URLSearchParams(queryString)

            /*
            * Adding CSRF token to AJAX header
            */
            $(function () {
                var token = $("input[name='_csrf']").val();
                var header = "X-CSRF-TOKEN";
                $(document).ajaxSend(function(e, xhr, options) {
                    xhr.setRequestHeader(header, token);
                });
            });

            // Handles Collapsible Div
            $('.collapsible').on('click', function(){
                this.classList.toggle('active');
                var content = this.nextElementSibling;
                var other = document.getElementById("other_info_container")
                if (content.style.maxHeight){
                    content.style.maxHeight = null;
                    other.style.opacity = 1;
                } else {
                    content.style.maxHeight = 500 + "px";
                    other.style.opacity = 0;
                }
            })

            $(".loadingContainer").hide();
            // Disabling the page while an AJAX request is made
            $(document).ajaxStart(function () {
                $("*").css('pointer-events','none');
                $("*").css('overflow','hidden');
                $("body").css('background-color','var(--grey-light)');
                $("body").css('opacity','0.5');
                $(".loadingContainer").show();
            });
            // Enabling the page once the AJAX request returns
            $(document).ajaxStop(function () {
                $("*").css('pointer-events','');
                $("*").css('overflow','');
                $("body").css('background-color','');
                $("body").css('opacity','');
                $(".loadingContainer").hide();
            });
         });
    </script>
</html>