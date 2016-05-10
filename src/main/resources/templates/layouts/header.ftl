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
                    <li><a href="/sign_in"><i class="fa fa-sign-in fa-fw"></i>Sign in</a></li>
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
                            <li><p class="text-muted text-center">Signed in as ${currentUser.name}</p></li>
                            <li class="divider"></li>
                            <li><a href="/user/${currentUser.id?c}">Profile</a></li>
                            <#if currentUser.role == "ADMIN">
                                <li class="divider"></li>
                                <li class="dropdown-header">Administrator</li>
                                <li><a href="/users">User List</a></li>
                            </#if>

                            <li class="divider"></li>
                            <li class="dropdown-header">Account</li>
                            <li><a href="/settings/admin">Settings</a></li>
                            <li>
                                <a href="javascript:void(0)" onclick="document.getElementById('logout').submit();">Sign Out</a>

                                <form action="/sign_out" id="logout" method="post">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                            </li>
                        </ul>
                        <!-- /.dropdown-user -->
                    </li>
                    <!-- /.dropdown -->
                </#if>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>