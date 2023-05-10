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
         <link rel="stylesheet" href="../css/applyPosition.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>
        .apply-header{
            color: var(--geek-purple);
            text-align: center;
        }
        </style>
    </head>
    <body>
    <div class="wrapper">
        <h1 class="apply-header">Application Page</h1>
        <div class="form-wrapper">
            <form:form modelAttribute="positionCandidate" method="POST" action="applyposition" >
                <div class="generic_section" id="position-list">
                    <label for="position-id" class="input_label">Position Applying for:</label>
                    <form:select path="id.positionId" id="position-id">
                        <c:forEach var="position" items="${positions}">
                            <c:if test="${position.getPosition_id() == selectedPositionID}">
                                <form:option selected="true" value="${position.getPosition_id()}" label="${position.getName()}"/>
                            </c:if>
                            <c:if test="${position.getPosition_id() != selectedPositionID}">
                                <form:option value="${position.getPosition_id()}" label="${position.getName()}"/>
                            </c:if>
                        </c:forEach>
                    </form:select>
                </div>
            </form:form>
            <form:form modelAttribute="newCandidate" method="POST" action="applyposition" >
                <div class="generic_section" id="candidate-form">
                    <label for="candidate-first-name" class="input_label">First Name:</label>
                    <input path="first_name" type="text" name="name" id="candidate-first-name">
                    <label for="candidate-last-name" class="input_label">Last Name:</label>
                    <input path="last_name" type="text" name="name" id="candidate-last-name">
                    <!-- NEED TO CHANGE THIS TO UPLOAD RATHER THAN A TEXT ENTRY -->
                    <label for="candidate-resume" class="input_label">Resume:</label>
                    <input path="resume_path" type="text" name="name" id="candidate-resume" placeholder="Upload Document ->">
                    <!-- Need to Add a Cover Letter Path to the candidate table -->
                    <label for="candidate-cover-letter" class="input_label">Cover Letter:</label>
                    <input type="text" name="name" id="candidate-cover-letter" placeholder="Upload Document ->">
                    <label for="candidate-notes" class="input_label">Comments:</label>
                    <textarea path="notes" type="text" name="name" id="candidate-notes" style="height: 200px; width: 70%;"></textarea>
                    <p></p>
                    <button style="margin: 50px; width: 40%" type="submit" id="button_apply">Submit</button>
                </div>
            </form:form>
        </div>
    </div>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>
    <script>
        submitForms = function(){
            document.getElementById(""
        }
    </script>
</html>