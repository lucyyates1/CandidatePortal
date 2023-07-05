<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <title>Add new candidate</title>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <link rel="stylesheet" href="../css/addNewCandidate.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <script>
            const queryString = window.location.search
            const urlParams = new URLSearchParams(queryString)
        </script>
        <script src="../js/add_new_candidate.js"></script>

        <script type="text/javascript"></script>
        <jsp:include page="header.jsp"/>
    </head>
    <body>
        <div class="loadingContainer">
            <div class="loadingAnimation"></div>
        </div>
        <h2>Add New Candidate</h2>
        <div class="eighty_percent">
            <form:form modelAttribute="newcandidate" method="POST" action="candidates" id="addCandidateForm">
                <div class="generic_section" id="first_section">
                    <label for="first_name_field" class="input_label">First Name</label>
                    <input path="first_name" type="text" name="first_name" id="first_name_field">
                    <label for="last_name_field" class="input_label">Last Name</label>
                    <input path="last_name" type="text" name="last_name" id="last_name_field">
                    <label for="resume_path_field" class="input_label">Candidate Resume Path</label>
                    <input path="resume_path" type="text" name="resume_path" id="resume_path_field">
                    <label for="initial_contact_date_field" id="position_date_label" class="input_label">Initial Contact Date</label>
                    <input path="initial_contact_date" type="date" name="initial_contact_date" id="initial_contact_date_field">
                    <label for="meet_and_greet_date_field" id="position_date_label" class="input_label">Meet and Greet Date</label>
                    <input path="meet_and_greet_date" type="date" name="meet_and_greet_date" id="meet_and_greet_date_field">
                    <label for="notes_field" class="input_label" id="position_description_label" style="padding-top: 10px;">Notes</label>
                    <textArea path="notes" rows="15" cols="70" name="notes" id="notes_field"></textarea>
                </div>
                <div class="submit_section">
                    <input type="submit" value="Submit" id="submit_button" style="align-self: center;">
                </div>
            </form:form>
        </div>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>
</html>