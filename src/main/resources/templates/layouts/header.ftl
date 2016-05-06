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
            <a class="navbar-brand" href="#">${myApp.name}</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-right navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="conferences/add"><i class="fa fa-plus" aria-hidden="true"></i></a>
                </li>

                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="login.html"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->

            <#if !currentUser??><li><a href="/login">Sign In</a></li>
            <#else>
                <li class="dropdown">
                    <a href="bootstrap-elements.html" data-target="#" class="dropdown-toggle" data-toggle="dropdown">+</a>
                    <ul class="dropdown-menu">
                        <li><a href="/article/write">Article Write</a></li>
                        <li><a href="/study/write">Study Write</a></li>
                    </ul>
                </li>
                <li><a href="/message/list/receive">Message</a></li>
                <li class="dropdown">
                    <a href="bootstrap-elements.html" data-target="#" class="dropdown-toggle" data-toggle="dropdown"><img src="<#if currentUser.assets??>${currentUser.assets.realPath}<#else>/img/user.png</#if>" style="width:20px; height:20px;" alt="icon"><b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><p class="text-muted text-center">Signed in as ${currentUser.name}</p></li>
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

                            <form action="/logout" id="logout" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                        </li>
                    </ul>
                </li>
            </#if>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>