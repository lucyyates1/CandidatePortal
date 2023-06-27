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
                        <!-- Iterate through list and populate table -->
                        <c:if test="${listCandidates.size() == 0}">
                        </c:if>
                        <c:if test="${listCandidates.size() != 0}">
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
                                        <td>Under Consideration</td>
                                        <td>N/A</td>
                                    </c:if>
                                    <c:if test="${listCandidates.get(candidate).getMeet_and_greet_date() != null}">
                                        <td> Scheduled Meet And Greet</td>
                                        <td>${listCandidates.get(candidate).getMeet_and_greet_date().format(formatter)}</td>
                                    </c:if>
                                </c:if>
                                <c:if test="${listCandidates.get(candidate).archived}">
                                    <td>Archived</td>
                                    <td>N/A</td>
                                </c:if>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </tbody>
                </table>
            </div>
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

            /*
             * Sorting activeRecruitingPositions table once the document is loaded
             * - attempted to do so using some sort of event listener (after the table is loaded),
             *   but couldn't get that to work
            */
            var table = $('#active_recruiting_positions')
            var rows = table.find('tr:gt(0)').toArray().sort(comparer(0))
            for (var i = 0; i < rows.length; i++){table.append(rows[i])}

            function comparer(index) {
                return function(a, b) {
                var valA = getCellValue(a, index), valB = getCellValue(b, index)
                return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB)
                }
            }
            function getCellValue(row, index){ return $(row).children('td').eq(index).text() }

            /*
             * Selecting a table header
             * -sorts table based on selected header
             * -sets asc/desc triangle on selected header
            */
            var lastTH = "";
            $('th').on('click', function(){
                var table = $(this).parents('table').eq(0)
                var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()))
                if (lastTH == this || lastTH == ""){this.asc = !this.asc} else {this.asc = true}
                if (!this.asc){rows = rows.reverse()}
                for (var i = 0; i < rows.length; i++){table.append(rows[i])}

                // Remember how the column is currently sorted
                $('th').removeClass(["th-sort-asc","th-sort-desc"])
                $(this).toggleClass("th-sort-asc", this.asc)
                $(this).toggleClass("th-sort-desc", !this.asc)
                lastTH = this
            })

            /*
             * Selecting a table row
             * -highlights row
            */
            var selectedID = null;
            $('.contentTable tbody tr').on('click', function(){
                $('tbody tr').removeClass("tr-sel")
                $(this).toggleClass("tr-sel")
                selectedID = $(this).attr('id')
            })

            /*
             * Selecting Close button
             * -archives highlighted position
             * -removes highlighted position from the table
            */
            $('#button_close_position').on('click', function(){
                if (selectedID != null) {
                    if (window.confirm("Are you sure you want to archive this position?")) {
                        $.ajax({
                            type: "PUT",
                            url: '/recruiting',
                            data: { "selectedID": selectedID },
                            success: function (position) {
                                selectedID = null
                                $(".tr-sel").remove()
                                console.log("Position successfully closed.")
                            },

                            failure: function (errMsg) {
                                console.log(errMsg.toString())
                            }
                        });
                    }
                }
            })

            /*
             * Selecting A Position
             * -sends you to the candidatesposition page (Position Details page)
             */
            $('.positionName').on('click', function(){
                positionID = $(this).closest('tr').attr('id')   // retrieves the SQL id of the position
                $.ajax({
                    type: "GET",
                    url: '/candidatesposition?id=' + positionID,
                    success: function (position) {
                        window.location = "/candidatesposition?id=" + positionID
                    },

                    failure: function (errMsg) {
                        console.log(errMsg.toString())
                    }
                });
            })
        });
    </script>
</html>