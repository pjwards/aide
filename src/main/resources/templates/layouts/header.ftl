<#import "/spring.ftl" as spring/>

<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->

<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">${myApp.name}</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-right navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <#if !currentUser??>
                    <li><a href="/sign_in"><i class="fa fa-sign-in fa-fw"></i><@spring.message "login.sign_in"/></a></li>
                <#else>
                    <li>
                        <a href="/conferences/add"><i class="fa fa-plus" aria-hidden="true"></i></a>
                    </li>
                    <!-- /.dropdown -->
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
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>