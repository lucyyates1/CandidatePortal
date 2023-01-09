<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Login</title>
        <link rel="stylesheet" href="../css/globalStyleSheet.css">
        <style>
            div.wrapper {
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 0;
                width: 100%;
                height: 100%;
            }
            div.localWrapper {
                display: flex;
                flex-direction: column;
                justify-content: center;
            }
            input[type="submit"] {
                font-size: clamp(1rem, 1vw, 1.5rem);
                width: auto;
                padding: 2%;
                margin-right: 2%;
                display: block;
                cursor: pointer;
                font-weight: bold;
            }
            form {
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            .submitContainer {
                display: flex;
                flex-direction: row;
                justify-content: center;
                align-items: center;
                width: 100%;
            }
            .nestedSubmitContainer {
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
        <div class="localWrapper">
            <h2>Candidate Portal Login</h2>
            <form:form action="${pageContext.request.contextPath}/login" method="POST">
                <c:if test="${param.error != null}">
                    <p class="error">Sorry! You entered invalid username/password.</p>
                </c:if>
                <c:if test="${param.logout != null}">
                    <p class="good">Successfully logged out.</p>
                </c:if>
                <input class="formInput" type="text" name="username" placeholder="Username" spellcheck="false" required/>
                <input class="formInput" type="password" name="password" placeholder="Password" spellcheck="false" required/>
                <div class="submitContainer">
                    <input class="formInput" type="submit" value="Login"/>
                    <div class="nestedSubmitContainer">
                        <p class="account">New user?
                            <a href="/register">
                                <%-- <img src="../image/user_add.png" alt=""/> --%>
                                Register
                            </a>
                        </p>
                        <!-- THIS IS BEING EXCLUDED FOR NOW UNTIL THE FUNCTIONALITY IS ADDED
                        <p class="account">
                            <a href="/recover">
                                <%-- <img src="../image/key.png" alt=""/> --%>
                                I forgot my password
                            </a>
                        </p>
                        -->
                    </div>
                </div>
            </form:form>
        </div>
        </div>
    </body>
</html>