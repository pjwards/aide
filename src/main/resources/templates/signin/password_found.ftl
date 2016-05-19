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
    </@layout.put>

    <@layout.put block="content-head-right" type="replace">
        <i class="fa fa-key"></i>
    </@layout.put>

    <@layout.put block="contents" type="replace">
        <section>
            <#if error??>
                <div class="alert alert-dismissable alert-danger text-center">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <p>${error}</p>
                </div>
            </#if>

            <#if message??>
                <div class="alert alert-dismissable alert-success text-center">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <p>${message}</p>
                </div>
            </#if>
        </section>

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