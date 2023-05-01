<%-- Header --%>

<style>
    div.header {
        display: flex;
        justify-content: space-between;     /* allows image to be left justified and nav bar to be right justified */
        padding: 5%;
        width: 90%;
        padding-top: 1%;
        padding-bottom: 1%;
        align-items: center;
    }
    .buttonsWrapper {
        display: flex;
        align-items: center;
    }
    img.header {
        max-width: 18vw;
        min-width: 15rem;
    }
    a.header {
        color: var(--navy);
        padding: 1.5rem;
        font-size: 1.2em;
        text-decoration: none;      /* removes underline from links */
        border-color: var(--geek-purple);
    }
    a.header:hover {
        box-shadow: inset 0px -3px 0px 0px var(--geek-purple);
    }
</style>

<div class="header">
    <div>   <!-- necessary to wrap in a div to ensure proper formatting -->
        <a href="/">
            <img class="header" src="../assets/Needl Logo.png"/>
        </a>
    </div>
    <div class="buttonsWrapper">
        <a class="header" href="/">Positions</a>
        <a class="header" href="recruiting">Applied</a>
        <a class="header" href="admin">Profile</a>
        <form name="logoutForm" action="logout" method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="submit" style="padding: 1rem 0rem; margin: 1.5rem; background: indianred; width: 100%" value="Logout"/>
        </form>
    </div>
</div>