<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="form" type="com.pjwards.aide.domain.forms.ForgotPasswordForm" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "/spring.ftl" as spring>

<@layout.extends name="signin/signinbase.ftl">
    <@layout.put block="head" type="prepend">
        <title>Password reset</title>
    </@layout.put>

    <@layout.put block="content-head-left" type="replace">
        <h3>Password reset</h3>
        <p>Enter new password</p>
    </@layout.put>

    <@layout.put block="content-head-right" type="replace">
    <i class="fa fa-key"></i>
    </@layout.put>

    <@layout.put block="contents" type="replace">
        <@spring.bind "form" />
            <#if spring.status.error>
            <section>
                <div class="alert alert-dismissable alert-danger text-center">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <#list spring.status.errorMessages as error>
                        <p>${error}</p>

                        <#if error?contains("Passwords") || error?contains("Password")><#global errorPassword=true></#if>
                        <#if error?contains("Passwords") || error?contains("PasswordRepeated")><#global errorPasswordRepeated=true></#if>
                    </#list>
                </div>
            </section>
            </#if>

        <form role="form" method="post" action="" class="login-form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group <#if errorPassword??>has-error</#if>">
                <label class="sr-only" for="password">New password</label>
                <input type="password"
                       placeholder="Password"
                       class="form-password form-control"
                       id="password" name="password" required autofocus>
            </div>
            <div class="form-group <#if errorPasswordRepeated??>has-error</#if>">
                <label class="sr-only" for="passwordRepeated">New password confirmation</label>
                <input type="password"
                       placeholder="Password again"
                       class="form-password form-control"
                       id="passwordRepeated" name="passwordRepeated" required>
            </div>
            <button type="submit" class="btn">Change my password</button>
        </form>
    </@layout.put>

    <@layout.put block="footer" type="prepend">
    </@layout.put>

    <@layout.put block="script" type="prepend">
    </@layout.put>
</@layout.extends>