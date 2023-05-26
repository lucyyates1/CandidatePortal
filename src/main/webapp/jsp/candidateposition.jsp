<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Candidates for Position</title>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <style>
            .backArrow {
                margin-bottom: 2rem;
                font-size: 3rem;
                color: var(--navy);
                cursor: pointer;
            }
            .displayTable td {
                padding-left: 0;
            }
        </style>
        <jsp:include page="header.jsp"/>
    </head>
    <body>
        <div class="loadingContainer">
            <div class="loadingAnimation"></div>
        </div>
        <div class="wrapper">

            <h1 style="padding-bottom: 0">Position Details for ${position.name}</h1>
            <span id="back_arrow" class="backArrow">&#x21A9</span>

            <button type="button" class="collapsible">Position Information</button>
            <div class="collapsibleContent">
                <table id="position_information_description" class="displayTable" style="width: 100%; background-color: var(--grey-light); border-radius: 0;">
                    <tr>
                        <td style="width: 14%">Description: </td>
                        <td>${position.description}</td>
                    </tr>
                </table>
            </div>
            <div style="padding: 0 18px; background-color: var(--grey-light);">
                <table id="position_information_other" class="displayTable" style="width: 100%; background-color: var(--grey-light); border-radius: 0;">
                    <tr>
                        <td style="width: 14%">Education: </td>
                        <c:if test="${position.isEducation_required()}" >
                            <td>${position.getEducation()} (Required)</td>
                        </c:if>
                        <c:if test="${!(position.isEducation_required())}" >
                            <td>${position.getEducation()} (Preferred)</td>
                        </c:if>
                    </tr>
                    <tr>
                        <td style="width: 14%">Qualifications: </td>
                        <td>
                            <c:forEach var="skill" items="${position.getPosition_skills()}">
                                <p>${skill.getSkill().getName()}</p>
                            </c:forEach>
                            <c:forEach var="certification" items="${position.getPosition_certification()}">
                                <p>Certification in ${certification.getPosition().getName()}</p>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>Date:</td>
                        <c:if test="${position.date != null}">
                            <td>${position.date.format(formatter)}</td>
                        </c:if>
                        <c:if test="${position.date == null}">
                            <td></td>
                        </c:if>
                    </tr>
                    <tr>
                        <td style="width: 14%">Place of Performance: </td>
                        <td>${position.place_of_performance}</td>
                    </tr>
                </table>
            </div>

            <button id="button_apply" style="margin-bottom: 1rem;">Apply</button>
        </div>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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

            // Sorting function ***********************************************************
        	var lastTH = "";
        	$('th').on('click', function(){
	            var table = $(this).parents('table').eq(0)
	            var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()))
	            if (lastTH == this || lastTH == "") {
	                this.asc = !this.asc
	            }
	            else {
	                this.asc = true
	            }
	            if (!this.asc){rows = rows.reverse()}
	            for (var i = 0; i < rows.length; i++){
	                table.append(rows[i])
	            }

                // Remember how the column is currently sorted
                $('th').removeClass(["th-sort-asc","th-sort-desc"])
                $(this).toggleClass("th-sort-asc", this.asc)
                $(this).toggleClass("th-sort-desc", !this.asc)
                lastTH = this
            })
            // Sorting helper functions
            function comparer(index) {
                return function(a, b) {
                    var valA = getCellValue(a, index), valB = getCellValue(b, index)
                    return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB)
                }
      	    }
     		function getCellValue(row, index){ return $(row).children('td').eq(index).text() }

            // This on click function makes it possible to select rows in the table
     		var selectedID = null;
            $('.contentTable tbody tr').on('click', function(){
                $('tbody tr').removeClass("tr-sel")
                $(this).toggleClass("tr-sel")
                selectedID = $(this).attr('id')
            })

            // Returns you to the positions page
            $('#back_arrow').on('click', function(){
                window.location.href = "/positions"
            })

            // Handles Collapsible Div
            $('.collapsible').on('click', function(){
                this.classList.toggle('active');
                var content = this.nextElementSibling;
                if (content.style.maxHeight){
                    content.style.maxHeight = null;
                } else {
                    content.style.maxHeight = content.scrollHeight + "px";
                }
            })

            /*
             * Applying to the Position
             * -sends you to the addCandidate page
             */
            $('#button_apply').on('click', function(){
                positionID = urlParams.get('id')   // retrieves the url param id of the position
                $.ajax({
                    type: "GET",
                    url: '/applyposition?id=' + positionID,
                    success: function (position) {
                        window.location = "/applyposition?id=" + positionID
                    },

                    failure: function (errMsg) {
                        console.log(errMsg.toString())
                    }
                });
            })
        });
    </script>
</html>
