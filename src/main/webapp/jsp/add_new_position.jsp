<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <title>Add new position</title>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <link rel="stylesheet" href="../css/addNewPosition.css"> <%--  --%>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <%-- This script stores the serialized list of positions --%>
        <script>
            var templatePositions = ${templatePositionsListJSON};
        </script>
        <script src="../js/add_new_position.js"></script>

        <script type="text/javascript"></script>
        <jsp:include page="header.jsp"/>
    </head>
    <body>
        <h2>Add New Position</h2>

        <div class="eighty_percent">

            <%-- 0th section: template selection --%>
            <div class="generic_section" id="template_section" style="align-items: center;">
                <label for="position_template_menu" class="input_label">Position Template</label>
                <select name="position_template" id="position_template_menu">
                    <option value="-1">- Select Template -</option>
                    <%-- <c:forEach items="${listTemplates}" var="item"> --%>
                    <c:forEach var="i" begin="0" end="${templatePositionsList.size()}">
                        <c:if test = "${templatePositionsList[i].template}">
                            <option value="${templatePositionsList[i].position_id}">
                                ${templatePositionsList[i].name}
                            </option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>

            <form:form modelAttribute="newposition" method="POST" action="positions" id="addPositionForm">

                <%-- 1st section: name, description, date --%>
                <div class="generic_section" id="first_section">
                    <label for="position_name_field" class="input_label">Position Name</label>
                    <input path="name" type="text" name="name" id="position_name_field">
                    <label for="position_date_field" id="position_date_label" class="input_label">Position Date</label>
                    <input path="date" type="date" name="date" id="position_date_field">
                    <label for="position_description_field" class="input_label" id="position_description_label" style="padding-top: 10px;">Description</label>
                    <textArea path="description" rows="15" cols="70" name="description"
                        id="position_description_field"></textarea>
                </div>

                <%-- 2nd section: education, education required, experience required --%>
                <div class="generic_section" id="education_section">
                    <label for="education_field" class="input_label">Education</label>
                    <input path="education" type="text" name="education" id="education_field">
                    <label for="education_required_field" class="input_label" id="education_required_label">Education Required</label>
                    <div class="checkbox">
                        <input path="education_required" type="checkbox" name="education_required" id="education_required_field">
                    </div>
                    <label for="experience_required_field" class="input_label" id="experience_required_label">Experience Required</label>
                    <input path="experience_required" type="number" name="experience_required" id="experience_required_field" min="1" max="255">
                </div>

                <%-- 3rd section: place of performance --%>
                <div class="generic_section" id="performance_section">
                    <label for="place_of_performance" class="input_label">Place of Performance</label>
                    <input path="place_of_performance" type="text" name="place_of_performance" id="place_of_performance">

                    <%-- place_of_performance Priority radio group --%>
                    <label path="place_performance_priority" for="priority" class="input_label">Place of Performance Priority</label>
                    <div class="spanWrapper">
                        <span id="priority" name="priority">
                            <input path="place_performance_priority" type="radio" id="priority_high" name="place_performance_priority" value=3 checked>
                            <label for="priority_high">High</label>
                            <br>
                            <input path="place_performance_priority" type="radio" id="priority_med" name="place_performance_priority" value=2>
                            <label for="priority_med">Medium</label>
                            <br>
                            <input path="place_performance_priority" type="radio" id="priority_low" name="place_performance_priority" value=1>
                            <label for="priority_low">Low</label>
                        </span>
                    </div>
                </div>

                <%-- 4th section: skills --%>
                <div class="generic_section" id="skills_section">

                    <%-- additional keywords --%>
                    <label for="skills_keywords_menu" class="input_label" style="padding-top: 10px;">Skill Keywords</label>
                    <div class="keyword_table" name="position_skills" id="skills_keywords_menu">
                        <div class="keywordRow">
                            <label>Skill Name</label>
                            <label>High Priority</label>
                            <label>Med Priority</label>
                            <label>Low Priority</label>
                            <label>Remove</label>
                        </div>
                    </div>
                </div>
                <div class="addKeywordWrapper">
                    <input type="button" value="Add Skill" name="add_skill" id="add_skill">
                </div>

                <%-- 5th section: certifications --%>
                <div class="generic_section" id="certifications_section">

                    <%-- additional certifications --%>
                    <label for="certifications_keywords_menu" class="input_label" style="padding-top: 10px;">Certification Keywords</label>
                    <div class="keyword_table" name="position_certifications" id="certifications_keywords_menu">
                        <div class="keywordRow">
                            <label>Certification Name</label>
                            <label>High Priority</label>
                            <label>Med Priority</label>
                            <label>Low Priority</label>
                            <label>Remove</label>
                        </div>
                    </div>
                </div>
                <div class="addKeywordWrapper">
                    <input type="button" value="Add Certification" name="add_certification" id="add_certification">
                </div>

                <%-- 6th section: submit_section --%>
                <div class="submit_section">
                    <input path="template" type="checkbox" name="template" id="position_template_field">
                    <label for="position_template_field" style="padding: 0 20px 0 10px; align-self: center;">Template</label>
                    <input type="submit" value="Submit" id="submit_button" style="align-self: center;">
                </div>
            </form:form>
        </div> <%-- eighty_percent --%>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>
</html>