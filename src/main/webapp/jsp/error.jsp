<html>
    <!--
        This html file is a bare bones error page that is served with the HttpStatus of the
        failed request.
    -->
    <head>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title>Error</title>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <link rel="stylesheet" href="../css/index.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="../js/index.js"></script>
    </head>
    <body>
        <div class="wrapper">
            <h1>Oops! Something Went Wrong!</h1>
            <h3>You got an HttpStatus of ${status}. Our Engineers are on it.</h3>
            <a href="/"> Go Home</a>
        </div>
    </body>
</html>