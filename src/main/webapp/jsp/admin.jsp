<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Admin</title>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <link rel="stylesheet" href="../css/admin.css">
        <style>
            .BTN_resetPass,
            .BTN_removeUser {
                font-weight: normal;
                font-size: 1rem;
                margin: 0;
                padding: 0.5rem;
            }
            .labelAndInputWrapper {
                display: flex;
                justify-content: left;
                max-height: 3rem;
            }
            .actionButtonsWrapper {
                display: flex;
                justify-content: space-between;
            }
            .searchButton {
                margin: 0;
                margin-left: 1rem;
                padding: .5rem;
            }
            label {
                padding: 0;
                font-size: 1.5rem;
            }
            input[type="text"] {
                margin: 0;
                margin-left: 1rem;
            }
            tbody tr:hover {
                cursor: auto;
            }
        </style>
        <jsp:include page="needl-header.jsp"/>
    </head>
    <body>
        <div class="wrapper">
            <h1>Administration</h1>
            <form modelAttribute="adminSearchUser" method="POST" id="adminSearchForm"
            action="${pageContext.request.contextPath}/performSearchAdmin">
                <div class="labelAndInputWrapper">
                    <label for="textInputSearch">Search User: </label>
                    <input type="text" path="textInput" id="searchInput" name="textInputSearch"></input>
                    <button type="button" class="searchButton" onClick="">Search</button>
                </div>
            </form>
            <div class="contentTableWrapper">
                <c:if test="${listUsers.size() > 0}">
                    <table id="applicationUsers">
                        <thead>
                            <tr>
                                <th>Username</th>
                                <th>Last Name</th>
                                <th>First Name</th>
                                <th>Role</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Iterate through list and populate table -->
                            <c:forEach var="user" begin="0" end="${listUsers.size() - 1}">
                                <tr id="${listUsers.get(user).user_id}">
                                    <td>${listUsers.get(user).username}</td>
                                    <td>${listUsers.get(user).last_name}</td>
                                    <td>${listUsers.get(user).first_name}</td>
                                    <td>${listUsers.get(user).role.getRole()}</td>
                                    <td>
                                        <div class="actionButtonsWrapper">
                                            <button class="BTN_resetPass" type="button" style="margin:0 20px 0 0">Reset password</button>
                                            <button class="BTN_removeUser" type="button">Remove user</button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${listUsers.size() < 1}">
                    <h1>Sorry, we couldn&#39t find any Users :(</h1>
                </c:if>
            </div>

            <form method="GET" id="adminInviteForm" action="${pageContext.request.contextPath}/admin_inviteUser">
                <button type="submit">Invite User</button>
            </form>
        </div>
    </body>
    <footer>
        <jsp:include page="footer.jsp"/>
    </footer>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function(){

            /*
             * Sorting activeRecruitingPositions table once the document is loaded
             * - attempted to do so using some sort of event listener (after the table is loaded),
             *   but couldn't get that to work
            */
            var table = $('#activeRecruitingPositions')
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
             * Selecting Remove User button
             * -removes respective user from database
             * -removes respective user from the table
            */
            $('.BTN_removeUser').on('click', function(){
                var selectedID = $(this).parent().parent().parent().attr('id')
                if (selectedID != null) {
                    $.ajax({
                        type: "PUT",
                        url: '/admin',
                        data: { "selectedID": selectedID },
                        success: function (removeUser) {
                            $('#' + selectedID).remove()
                            selectedID = null
                            console.log("Position successfully closed.")
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