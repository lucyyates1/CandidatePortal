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
        <jsp:include page="needl-header.jsp"/>
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

            <button id="button_close_position" style="margin-bottom: 1rem;">Close Position</button>
            <c:if test="${listCandidates.size() > 0}">
                <h3>Candidates for ${position.name}</h3>

                <table id="active_candidate_positions" class="contentTable">
            	    <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Score</th>
                            <th>Score Run Date</th>
                            <th>Initial Contact Date</th>
                            <th>Meet and Greet Date</th>
                            <th>Technical Interview Date</th>
                            <th>Offer Date</th>
                            <th>Notes</th>
                            <!-- <th>Hyperlink to Resume (WIP)</th> -->
                        </tr>
                    </thead>
                    <tbody>
                    <!-- Iterate through list and populate table -->
                        <c:forEach var="candidate" begin="0" end="${listCandidates.size() - 1}">
                                <tr id="${listCandidates.get(candidate).application_id}">
                                    <td>${listCandidates.get(candidate).first_name}</td>
                                    <td>${listCandidates.get(candidate).last_name}</td>
                                    <td id="score">${listCandidates.get(candidate).score}</td>
                                    <c:if test="${listCandidates.get(candidate).score_run_date != null}">
                                        <td id="score_run_date">${listCandidates.get(candidate).score_run_date.format(formatter)}</td>
                                    </c:if>
                                    <c:if test="${listCandidates.get(candidate).score_run_date == null}">
                                        <td id="score_run_date"></td>
                                    </c:if>
                                    <c:if test="${listCandidates.get(candidate).initial_contact_date != null}">
                                        <td>${listCandidates.get(candidate).initial_contact_date.format(formatter)}</td>
                                    </c:if>
                                    <c:if test="${listCandidates.get(candidate).initial_contact_date == null}">
                                        <td></td>
                                    </c:if>
                                    <c:if test="${listCandidates.get(candidate).meet_and_greet_date != null}">
                                        <td>${listCandidates.get(candidate).meet_and_greet_date.format(formatter)}</td>
                                    </c:if>
                                    <c:if test="${listCandidates.get(candidate).meet_and_greet_date == null}">
                                        <td></td>
                                    </c:if>
                                    <c:if test="${listCandidates.get(candidate).technical_interview_date != null}">
                                        <td>${listCandidates.get(candidate).technical_interview_date.format(formatter)}</td>
                                    </c:if>
                                    <c:if test="${listCandidates.get(candidate).technical_interview_date == null}">
                                        <td></td>
                                    </c:if>
                                    <c:if test="${listCandidates.get(candidate).offer_date != null}">
                                        <td>${listCandidates.get(candidate).offer_date.format(formatter)}</td>
                                    </c:if>
                                    <c:if test="${listCandidates.get(candidate).offer_date == null}">
                                        <td></td>
                                    </c:if>
                                    <td>${listCandidates.get(candidate).notes}</td>
                                </tr>
             		    </c:forEach>
             	    </tbody>
          	    </table>
                <button id="button_add_candidate" style="margin-right:20px">Add Candidate</button>
                <button id="button_update_candidate" style="margin-right:20px">Update Candidate Info</button>
                <button id="button_score_candidate" style="margin-right:20px">Score Candidate</button>
          	</c:if>
          	<c:if test="${listCandidates.size() < 1}">
          	    <h3>There are currently no candidates for ${position.name}</h3>
          	    <button id="button_add_candidate" style="margin-right:20px">Add Candidate</button>
          	</c:if>
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

            // Returns you to the recruiting page
            $('#back_arrow').on('click', function(){
                window.location.href = "/recruiting"
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
             * Closing out a position
             * - archives the position and sends you to the recruiting page
             */
            $('#button_close_position').on('click', function(e){
                positionID = ${position.position_id}
                e.preventDefault()
                if (window.confirm("Are you sure you want to archive this position?")) {
                    $.ajax({
                    	type: "PUT",
                        url: '/recruiting',
                        data: { "selectedID": positionID },
                        success: function (candidate) {
                        	console.log("Successfully removed the position!")
                        	window.location.href = "/recruiting"
                        },

                        failure: function (errMsg) {
                            console.log(errMsg.toString())
                        }
                    });
                }
            })

            /*
             * Adding a Candidate
             * -sends you to the addCandidate page
             */
            $('#button_add_candidate').on('click', function(){
                positionID = urlParams.get('id')   // retrieves the url param id of the position
                $.ajax({
                    type: "GET",
                    url: '/addCandidate?id=' + positionID,
                    success: function (position) {
                        window.location = "/addCandidate?id=" + positionID
                    },

                    failure: function (errMsg) {
                        console.log(errMsg.toString())
                    }
                });
            })

            /*
             * Scoring a Candidate
             * - sends a call to the scoring API
             */
            $('#button_score_candidate').on('click', function(){
                if (selectedID != null) {
                    candidateID = selectedID
                    positionID = urlParams.get('id')
                    $.ajax({
                        type: "PUT",
                        url: '/getScore',
                        data: { "candidateID": candidateID,
                                "positionID": positionID },
                        success: function (response) {
                            $("#" + selectedID + " #score").html(response.score)
                            $("#" + selectedID + " #score_run_date").html(response.date)
                            console.log(response.score)
                            console.log(response.date)
                            selectedID = null
                            $("tbody tr").removeClass("tr-sel")
                            console.log("Candidate was scored!")
                        },

                        failure: function (errMsg) {
                            console.log(errMsg.toString())
                        }
                    });
                }
            })
        });
    </script>
</html>
