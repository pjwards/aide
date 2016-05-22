<#import "/spring.ftl" as spring/>

<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->

<!-- Navigation -->
<nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse">
                <i class="fa fa-bars"></i>
            </button>
            <a class="navbar-brand page-scroll" href="${conference.id}">
                <i class="fa fa-play-circle"></i> <span class="light">${conference.name}</span>
            </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-right navbar-main-collapse">
            <ul class="nav navbar-nav">
                <!-- Hidden li included to remove active class from about link when scrolled up past about section -->
                <li class="hidden">
                    <a href="#page-top"></a>
                </li>
                <#if currentUser??>
                    <#if conference.isHost(currentUser.user)
                    || conference.isManager(currentUser.user)
                    || conference.isSpeaker(currentUser.user)>
                        <li>
                            <a class="page-scroll" href="${conference.id}/admin"><@spring.message "header.details.admin"/></a>
                        </li>
                    </#if>
                </#if>
                <li>
                    <a class="page-scroll" href="${conference.id}/schedule"><@spring.message "header.details.schedule"/></a>
                </li>
                <li>
                    <a class="page-scroll" href="${conference.id}/register"><@spring.message "header.details.register"/></a>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-globe"></i><i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="?lang=ko_KR">한국어</a></li>
                        <li><a href="?lang=en_US">English</a></li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>