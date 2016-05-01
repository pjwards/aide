<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->

<header>
    <div class="navbar navbar-default">
        <div class="navbar-header col-lg-offset-1 col-md-offset-1">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">${myApp.name}</a>
        </div>
        <div class="navbar-collapse collapse navbar-responsive-collapse">
            <ul class="nav navbar-nav">
                <li><a href="/article/list">Article</a></li>
                <li><a href="/study/list">Study</a></li>
            </ul>
            <form class="navbar-form navbar-left">
                <input type="text" class="form-control col-lg-8" placeholder="Search">
            </form>

            <!-- For empty space -->
            <div class="nav navbar-nav navbar-right col-lg-1 col-md-1">
            </div>

            <ul class="nav navbar-nav navbar-right">

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
    </div>
</header>
