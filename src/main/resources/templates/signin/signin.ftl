<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="error" type="java.util.Optional<String>" -->

<@layout.extends name="signin/signinbase.ftl">
    <@layout.put block="head" type="prepend">
        <title>AIDE :: Sign In</title>
    </@layout.put>

    <@layout.put block="header" type="replace">
        <h1><strong><a href="/">Aide</a></strong></h1>

        <div class="description">
            <p>
                <strong>What is hot issue this week?</strong> Hot issues, hot people and analysis for a
                facebook group.
                <br/><a href="/user/sign_up/"><strong>Sign up</strong></a> now, you can use funny functions.
            </p>
        </div>
    </@layout.put>

    <@layout.put block="content-head-left" type="replace">
        <h3>Sign in our site</h3>

        <p>Enter your username and password to sign in:</p>
        <#if error.isPresent()>
            <p style="color: #de615e;">Your username and password didn't match. Please try again.</p>
        </#if>
    </@layout.put>

    <@layout.put block="content-head-right" type="replace">
        <i class="fa fa-sign-in"></i>
    </@layout.put>

    <@layout.put block="contents" type="replace">
        <form role="form" method="post" action="/sign_in" class="login-form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group <#if error.isPresent()>has-error</#if>">
                <label class="sr-only" for="username">Email</label>
                <input type="email" placeholder="Email Address"
                       class="form-email form-control"
                       id="email" name="email" required autofocus>
            </div>
            <div class="form-group <#if error.isPresent()>has-error</#if>">
                <label class="sr-only" for="password">Password</label>
                <input type="password" placeholder="Password"
                       class="form-password form-control"
                       id="password" name="password" required>
            </div>
            <button type="submit" class="btn">Sign in!</button>

        </form>
    </@layout.put>

    <@layout.put block="content-related" type="replace">
        <div style="text-align: center;">
            <a href="/forgot_password/new">Lost password?</a>

            <p>If you don't have an account, you can <a href="/user/sign_up/">sign up</a> for one.
        </div>
    </@layout.put>

    <@layout.put block="footer" type="prepend">
    </@layout.put>

    <@layout.put block="script" type="prepend">
    </@layout.put>
</@layout.extends>
