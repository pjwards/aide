<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->

<div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="/">Aide</a>
</div>
<!-- /.navbar-header -->

<ul class="nav navbar-top-links navbar-right">
    <li>
        <a href="/"><i class="fa fa-home fa-fw"></i>Home</a>
    </li>
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
            <img src="<#if currentUser.assets??>${currentUser.assets.realPath}</#if>"
                 style="width:20px; height:20px;" alt="icon" onerror="if (this.src != '/img/user.png') this.src = '/img/user.png'">  <i class="fa fa-caret-down"></i>
        </a>
        <ul class="dropdown-menu dropdown-user">
            <li><p class="text-muted text-center">Signed in as ${currentUser.name}</p></li>
        <#if currentUser.role == "ADMIN">
            <li class="divider"></li>
            <li class="dropdown-header">Administrator</li>
            <li><a href="/settings/users">User List</a></li>
        </#if>
            <li class="divider"></li>
            <li class="dropdown-header">Account</li>
            <li><a href="/user/${currentUser.id?c}">Profile</a></li>
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
</ul>
<!-- /.navbar-top-links -->