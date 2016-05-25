<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "/spring.ftl" as spring/>

<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <@layout.block name="nav-header">
        <@layout.extends name="user/userheader.ftl">
        </@layout.extends>
    </@layout.block>
    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li class="divider"></li>
                <#--<li class="sidebar-search">-->
                    <#--<div class="input-group custom-search-form">-->
                        <#--<input type="text" class="form-control" placeholder="Search...">-->
                                    <#--<span class="input-group-btn">-->
                                    <#--<button class="btn btn-default" type="button">-->
                                        <#--<i class="fa fa-search"></i>-->
                                    <#--</button>-->
                                <#--</span>-->
                    <#--</div>-->
                    <#--<!-- /input-group &ndash;&gt;-->
                <#--</li>-->
                <li>
                    <a href="/user/${currentUser.id?c}"><i class="fa fa-user fa-fw"></i><@spring.message "header.user.profile"/></a>
                </li>
                <li>
                    <a href="/settings/update"><i class="fa fa-gear fa-fw"></i><@spring.message "header.user.update"/></a>
                </li>
                <li>
                    <a href="/settings/password"><i class="fa fa-check fa-fw"></i><@spring.message "header.user.password"/></a>
                </li>
                <li>
                    <a href="/settings/delete"><i class="fa fa-times fa-fw"></i><@spring.message "header.user.delete"/></a>
                </li>
                <#if currentUser.role == "ADMIN">
                <li>
                    <a href="/settings/users"><i class="fa fa-users fa-fw"></i><@spring.message "header.user.list"/></a>
                </li>
                </#if>
            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
</nav>