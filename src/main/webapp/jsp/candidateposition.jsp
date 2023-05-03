<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title>Needl</title>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <jsp:include page="header.jsp"/>
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
            #position-info{
                padding: 0 18px;
                background-color: var(--grey-light);
            }
        </style>
    </head>
    <body>
    <div class="wrapper">
        <h1 style="padding-bottom: 0">Position Details for ${position.name}</h1>
        <span id="back_arrow" class="backArrow">&#x21A9</span>
        <div id="position-info">
            <table id="position_information_description" class="displayTable" style="width: 100%; background-color: var(--grey-light); border-radius: 0;">
                <tr>
                    <td style="width: 14%; font-weight: bold;">Description: </td>
                </tr>
                <tr>
                    <td>${position.description}</td>
                </tr>
                <tr>
                    <td style="width: 14%; font-weight: bold;">Education Required: </td>
                </tr>
                <tr>
                    <c:if test="${position.isEducation_required()}">
                        <td>${position.education} (Required)</td>
                    </c:if>
                    <c:if test="${!(position.isEducation_required())}">
                        <td>${position.education}</td>
                    </c:if>
                </tr>
                <tr>
                    <td style="width: 14%; font-weight: bold;">Qualifications: </td>
                </tr>
                <tr>
                    <td>N/A</td>
                </tr>
                <tr>
                    <td style="width: 14%; font-weight: bold;">Place Of Performance:</td>
                </tr>
                <tr>
                    <td>${position.getPlace_of_performance()}</td>
                </tr>
                <tr>
                    <td style="width: 14%; font-weight: bold;">Date:</td>
                </tr>
                <tr>
                    <td>${position.date.format(formatter)}</td>
                </tr>
            </table>
        </div>
        <div>
            <button style="margin: 2rem 0;" id="button_apply">Apply Here</button>
        </div>
    </div>
    </body>
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

         		function getCellValue(row, index){ return $(row).children('td').eq(index).text() }


                // Returns you to the home page
                $('#back_arrow').on('click', function(){
                    window.location.href = "/"
                })

                /*
                 * Applying for a position
                 * -sends you to the application page
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