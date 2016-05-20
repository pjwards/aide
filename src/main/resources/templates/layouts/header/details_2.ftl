<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->

<!-- Navigation -->
<nav id="mainNav" class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand page-scroll" href="/conferences/${conference.id}">${conference.name}</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
            <#if currentUser??>
                <#if conference.isHost(currentUser.user)
                || conference.isManager(currentUser.user)
                || conference.isSpeaker(currentUser.user)>
                    <li>
                        <a class="page-scroll" href="/conferences/${conference.id}/admin">ADMIN</a>
                    </li>
                </#if>
            </#if>
                <li>
                    <a class="page-scroll" href="/conferences/${conference.id}/schedule">SCHEDULE</a>
                </li>
                <li>
                    <a class="page-scroll" href="/conferences/${conference.id}/register">REGISTER</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>