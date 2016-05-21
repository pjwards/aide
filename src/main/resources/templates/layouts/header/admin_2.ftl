<#import "/spring.ftl" as spring/>

<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->
<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->

<div id="wrapper-header">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/conferences/${conference.id}">${conference.name}</a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <#if !currentUser??>
                <li><a href="/sign_in"><i class="fa fa-sign-in fa-fw"></i><@spring.message "login.sign_in"/></a></li>
            <#else>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <img src="<#if currentUser.assets??>${currentUser.assets.realPath}</#if>"
                             style="width:20px; height:20px;" alt="icon" onerror="if (this.src != '/img/user.png') this.src = '/img/user.png'">  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <#assign arguments = ["${currentUser.name}"]>
                        <li><p class="text-muted text-center"><@spring.messageArgs "header.signed_in_as", arguments/></p></li>
                        <#if currentUser.role == "ADMIN">
                            <li class="divider"></li>
                            <li class="dropdown-header"><@spring.message "header.administrator"/></li>
                            <li><a href="/settings/users"><@spring.message "header.user_list"/></a></li>
                        </#if>

                        <li class="divider"></li>
                        <li class="dropdown-header"><@spring.message "header.account"/></li>
                        <li><a href="/user/${currentUser.id?c}"><@spring.message "header.profile"/></a></li>
                        <li class="divider"></li>
                        <li>
                            <a href="javascript:void(0)" onclick="document.getElementById('logout').submit();"><@spring.message "login.sign_out"/></a>

                            <form action="/sign_out" id="logout" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
            <!-- /.dropdown -->
            </#if>
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
        <!-- /.navbar-top-links -->

        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li>
                        <a href="/conferences/${conference.id}/admin"><i class="fa fa-dashboard fa-fw"></i> <@spring.message "header.admin.dashboard"/></a>
                    </li>
                    <li>
                        <a href="/conferences/${conference.id}/admin/programs"><i class="fa fa-table fa-fw"></i> <@spring.message "header.admin.programs"/></a>
                    </li>
                    <li>
                        <a href="/conferences/${conference.id}/admin/sessions"><i class="fa fa-edit fa-fw"></i> <@spring.message "header.admin.sessions"/></a>
                    </li>
                </ul>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>
</div>