<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="form" type="com.pjwards.aide.domain.forms.SignUpForm" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "/spring.ftl" as spring>

<@layout.extends name="signin/signinbase.ftl">
    <@layout.put block="head" type="prepend">
    <title>AIDE :: <@spring.message "user.login.sign"/></title>
    </@layout.put>

    <@layout.put block="content-head-left" type="replace">
        <h3><@spring.message "user.login.guide"/></h3>

        <p><@spring.message "user.login.account"/></p>
    </@layout.put>

    <@layout.put block="content-head-right" type="replace">
    <i class="fa fa-sign-in"></i>
    </@layout.put>

    <@layout.put block="contents" type="replace">
        <@spring.bind "form" />
            <#if spring.status.error>
            <section>
                <div class="alert alert-dismissable alert-danger text-center">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <#list spring.status.errorMessages as error>
                        <p>${error}</p>

                        <#if error?contains("email") || error?contains("Email")><#global errorEmail=true></#if>
                        <#if error?contains("Name")><#global errorName=true></#if>
                        <#if error?contains("Passwords") || error?contains("Password")><#global errorPassword=true></#if>
                        <#if error?contains("Passwords") || error?contains("PasswordRepeated")><#global errorPasswordRepeated=true></#if>
                    </#list>
                </div>
            </section>
            </#if>

        <form role="form" method="post" action="" class="login-form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group <#if errorEmail??>has-error</#if>"">
                <label class="sr-only" for="email"><@spring.message "content.list.email"/></label>
                <input type="email" placeholder="Email address"
                       class="form-email form-control"
                       id="email" name="email" required>
            </div>

            <div class="form-group <#if errorName??>has-error</#if>">
                <label class="sr-only" for="name"><@spring.message "content.list.name"/></label>
                <input type="text" placeholder="Name"
                       class="form-username form-control"
                       id="name" name="name" required>
            </div>

            <div class="form-group <#if errorPassword??>has-error</#if>">
                <label class="sr-only" for="password"><@spring.message "user.password.password"/></label>
                <input type="password" placeholder="password"
                       class="form-password form-control"
                       id="password" name="password" required>
            </div>
            <div class="form-group <#if errorPasswordRepeated??>has-error</#if>">
                <label class="sr-only" for="passwordRepeated"><@spring.message "user.password.again"/></label>
                <input type="password" placeholder="password again"
                       class="form-password form-control"
                       id="passwordRepeated" name="passwordRepeated" required>
            </div>

            <button type="submit" class="btn"><@spring.message "user.login.sign"/></button>
        </form>
    </@layout.put>

    <@layout.put block="footer" type="prepend">
    </@layout.put>

    <@layout.put block="script" type="prepend">
    </@layout.put>
</@layout.extends>

