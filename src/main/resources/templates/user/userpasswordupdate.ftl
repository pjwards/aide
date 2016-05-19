<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="form" type="com.pjwards.aide.domain.forms.UserUpdatePasswordForm" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "/spring.ftl" as spring>

<@layout.extends name="user/userbase.ftl">
    <@layout.put block="head" type="prepend">
    <title>Aide :: Change Password</title>
    </@layout.put>

    <@layout.put block="header" type="replace">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Change Password</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    </@layout.put>

    <@layout.put block="contents" type="replace">
        <@spring.bind "form" />
        <#if spring.status.error>
        <div class="row">
            <div class="col-lg-12">
                <div class="alert alert-dismissable alert-danger text-center">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <#list spring.status.errorMessages as error>
                        <p>${error}</p>

                        <#if error?contains("Old")><#global errorOldPassword=true></#if>
                        <#if error?contains("Passwords") || error?contains("Password")><#global errorPassword=true></#if>
                        <#if error?contains("Passwords") || error?contains("PasswordRepeated")><#global errorPasswordRepeated=true></#if>
                    </#list>
                </div>
            </div>
        </div>
        </#if>

    <div class="row">
        <div class="col-lg-12">
            <form role="form" action="" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="id" id="id" value="${currentUser.getId()?c}"/>

                <div class="form-group <#if errorOldPassword??>has-error</#if>">
                    <label>Old Password</label>
                    <input type="password" class="form-control" name="oldPassword" id="oldPassword" placeholder="Password" required>
                </div>
                <div class="form-group" <#if errorPassword??>has-error</#if>">
                    <label>New Password</label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="New Password" required>
                </div>
                <div class="form-group" <#if errorPasswordRepeated??>has-error</#if>>
                    <label>Static Control</label>
                    <input type="password" class="form-control" name="passwordRepeated" id="passwordRepeated" placeholder="Repeat Password" required>
                </div>
                <button type="submit" class="btn btn-info">Update Password</button>
                <a href="/forgot_password/new" class="help-block">Forgot password?</a>
            </form>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row (nested) -->
    </@layout.put>

    <@layout.put block="footer" type="replace">
    </@layout.put>

    <@layout.put block="script" type="replace">
    </@layout.put>

</@layout.extends>