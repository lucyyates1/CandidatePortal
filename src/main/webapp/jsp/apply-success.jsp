<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title>Applied</title>
        <jsp:include page="header.jsp"/>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <link rel="stylesheet" href="../css/index.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="../js/index.js"></script>
    </head>
    <body>
        <div class="wrapper">
            <div id="content-wrapper" style="display: grid;">
                <h1 style="text-align: center;">Thank You For Applying!</h1>
                <button style="width: 15%; margin: auto;" id="button_return">Return Home</button>
                <br>
                <button style="width: 20%; margin: auto;" id="button-positions">View Other Positions</button>
            </div>
        </div>
    </body>
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
            /*
             * Return to the Home Page
             * -sends you back to the home page
             */
            $('#button_return').on('click', function(){
                positionID = urlParams.get('id')   // retrieves the url param id of the position
                $.ajax({
                    type: "GET",
                    url: '/',
                    success: function (position) {
                        window.location = "/"
                    },

                    failure: function (errMsg) {
                        console.log(errMsg.toString())
                    }
                });
            })
            /*
             * Return to the Positions Page
             * -sends you back to the positions page
             */
            $('#button-positions').on('click', function(){
                positionID = urlParams.get('id')   // retrieves the url param id of the position
                $.ajax({
                    type: "GET",
                    url: '/positions',
                    success: function (position) {
                        window.location = "/positions"
                    },

                    failure: function (errMsg) {
                        console.log(errMsg.toString())
                    }
                });
            })
         });
    </script>
</html>