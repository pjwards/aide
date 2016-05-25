<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="error" type="java.util.Optional<String>" -->
<#import "/spring.ftl" as spring>

<@layout.extends name="signin/signinbase.ftl">
    <@layout.put block="head" type="prepend">
        <title>AIDE :: <@spring.message "user.login.login"/></title>
    </@layout.put>

    <@layout.put block="header" type="replace">
        <h1><strong><a href="/">Aide</a></strong></h1>

        <div class="description">
            <p>
                <strong><@spring.message "user.login.slug"/></strong> <@spring.message "user.login.description"/>
            </p>
        </div>
    </@layout.put>

    <@layout.put block="content-head-left" type="replace">
        <h3><@spring.message "user.login.info"/></h3>

        <p><@spring.message "user.login.enter"/></p>
        <#if error.isPresent()>
            <p style="color: #de615e;"><@spring.message "user.login.match"/></p>
        </#if>
    </@layout.put>

    <@layout.put block="content-head-right" type="replace">
        <i class="fa fa-sign-in"></i>
    </@layout.put>

    <@layout.put block="contents" type="replace">
        <form role="form" method="post" action="/sign_in" class="login-form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group <#if error.isPresent()>has-error</#if>">
                <label class="sr-only" for="username"><@spring.message "content.list.email"/></label>
                <input type="email" placeholder="Email Address"
                       class="form-email form-control"
                       id="email" name="email" required autofocus>
            </div>
            <div class="form-group <#if error.isPresent()>has-error</#if>">
                <label class="sr-only" for="password"><@spring.message "user.password.password"/></label>
                <input type="password" placeholder="Password"
                       class="form-password form-control"
                       id="password" name="password" required>
            </div>
            <button type="submit" class="btn"><@spring.message "user.login.login"/></button>

        </form>
    </@layout.put>

    <@layout.put block="content-related" type="replace">
        <div style="text-align: center;">
            <a href="/forgot_password/new"><@spring.message "user.login.lost"/></a>

            <p><@spring.message "user.login.join"/> <a href="/user/sign_up/"><@spring.message "user.login.sign"/></a> <@spring.message "user.login.part"/>
        </div>
    </@layout.put>

    <@layout.put block="footer" type="prepend">
    </@layout.put>

    <@layout.put block="script" type="prepend">
    </@layout.put>
</@layout.extends>
