<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->
<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->

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
                <li><a href="/sign_in"><i class="fa fa-sign-in fa-fw"></i>Sign in</a></li>
            <#else>
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
            </#if>
        </ul>
        <!-- /.navbar-top-links -->

        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="sidebar-search">
                        <div class="input-group custom-search-form">
                            <input type="text" class="form-control" placeholder="Search...">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                        </div>
                        <!-- /input-group -->
                    </li>
                    <li>
                        <a href="/conferences/${conference.id}/admin"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                    </li>
                    <li>
                        <a href="/conferences/${conference.id}/admin/days"><i class="fa fa-calendar fa-fw"></i> Days</a>
                    </li>
                    <li>
                        <a href="/conferences/${conference.id}/admin/rooms"><i class="fa fa-building fa-fw"></i> Rooms</a>
                    </li>
                    <li>
                        <a href="/conferences/${conference.id}/admin/programs"><i class="fa fa-table fa-fw"></i> Programs</a>
                    </li>
                    <li>
                        <a href="/conferences/${conference.id}/admin/sessions"><i class="fa fa-edit fa-fw"></i> Sessions</a>
                    </li>
                    <li>
                        <a href="/conferences/${conference.id}/admin/update"><i class="fa fa-group fa-fw"></i> Conference</a>
                    </li>
                    <li>
                        <a href="forms.html"><i class="fa fa-user fa-fw"></i> Users</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-wrench fa-fw"></i> Functions<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="panels-wells.html">Panels and Wells</a>
                            </li>
                            <li>
                                <a href="buttons.html">Buttons</a>
                            </li>
                            <li>
                                <a href="notifications.html">Notifications</a>
                            </li>
                            <li>
                                <a href="typography.html">Typography</a>
                            </li>
                            <li>
                                <a href="icons.html"> Icons</a>
                            </li>
                            <li>
                                <a href="grid.html">Grid</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-group fa-fw"></i> Sponsor<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="/conferences/${conference.id}/admin/sponsor/list">Sponsor Manager</a>
                            </li>
                            <li>
                                <a href="/conferences/${conference.id}/admin/sponsor/add">Sponsor Add</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                </ul>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>
</div>