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
        .innerWrapper {
            display: flex;
            margin-bottom: 2rem;
        }
        .left {
            flex-grow: .3;
            flex-basis: 0;
        }
        .right {
            display: flex;
            flex-direction: column;
            flex-grow: 1;
        }
        .info{
            background-color: lightgrey;
            position: fixed;
            width: 30%;
            top: 18%;
            height: 500px;
            border-radius: 10px;
        }
        .description{
            background-color: lightgrey;
            overflow-y: scroll;
            height: 580px;
            color: black;
            padding: 4px;
            border-radius: 10px;
           }
        </style>
    </head>
    <body>
    <div class="wrapper">
        <h2 class="apply-header">Application Page</h2>
        <div class="innerWrapper">
            <div class="left">
                <div class="info">
                    <h2 style="color: black; padding: 4px;" >${selectedPosition.name}</h2>
                    <hr>
                    <p class="description">
                        ${selectedPosition.description}
                    </p>
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
                        <input name="first-name" type="text" id="candidate-first-name" required="required" />
                        <label for="candidate-last-name" class="input_label">Last Name (*):</label>
                        <input name="last-name" type="text" id="candidate-last-name" required="required" />
                        <!-- FILE UPLOADS FOR RESUME -->
                        <label for="resume-upload" class="input_label">Resume (*):</label>
                        <input id="resume-upload" type="file" name="resume" required="required"/>
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