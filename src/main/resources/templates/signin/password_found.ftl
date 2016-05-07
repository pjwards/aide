<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="message" type="java.lang.String" -->

<@layout.extends name="signin/signinbase.ftl">
    <@layout.put block="head" type="prepend">
        <title>Password reset</title>
    </@layout.put>

    <@layout.put block="content-head-left" type="replace">
        <h3>Password reset</h3>

        <p>Forgotten your password? Enter your e-mail address below, and we'll e-mail instructions for setting a new one.</p>

        <#if error??>
            <p style="color: #de615e;">Your input has some errors. Please try again.</p>
            <p style="color: #de615e;">${error}</p>
        </#if>

        <#if message??>
            <p style="color: #de615e;">Your input has some errors. Please try again.</p>
            <p style="color: #de615e;">${message}</p>
        </#if>
    </@layout.put>

    <@layout.put block="content-head-right" type="replace">
        <i class="fa fa-key"></i>
    </@layout.put>

    <@layout.put block="contents" type="replace">
        <form role="form" method="post" action="" class="login-form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group <#if error??>has-error</#if>">
                <label class="sr-only" for="email">Email</label>
                <input type="email" placeholder="Email Address"
                       class="form-email form-control"
                       id="email" name="email" required autofocus>
            </div>
            <button type="submit" class="btn">Reset my password</button>
        </form>
    </@layout.put>

    <@layout.put block="footer" type="prepend">
    </@layout.put>

    <@layout.put block="script" type="prepend">
    </@layout.put>
</@layout.extends>