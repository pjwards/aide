<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${myApp.name} :: Sign In</title>
    </@layout.put>

    <@layout.put block="header" type="prepend">
    </@layout.put>

    <@layout.put block="contents">
        <section>
            <#if error.isPresent()>
                <div class="alert alert-dismissable alert-danger text-center">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <p>The email or password you have entered is invalid, try again.</p>
                </div>
            </#if>
        </section>
        <section>
            <form role="form" method="post" action="" class="login-form">
                {% csrf_token %}
                <input type="hidden" name="next" value="{{ next }}"/>

                <div class="form-group">
                    <label class="sr-only" for="id_username">Username</label>
                    <input type="text" placeholder="{% if form.username.errors %}{{ form.username.errors.as_text }}
            {% else %}Username...{% endif %}"
                           class="form-username form-control"
                           id="id_username" name="username" required>

                </div>
                <div class="form-group">
                    <label class="sr-only" for="id_password">Password</label>
                    <input type="password" placeholder="{% if form.password.errors %}{{ form.password.errors.as_text }}
            {% else %}Password...{% endif %}"
                           class="form-password form-control"
                           id="id_password" name="password" required>
                </div>
                <button type="submit" class="btn">Sign in!</button>

            </form>
        </section>
        <section>
            <div style="text-align: center;">
                <a href="{% url 'auth_password_reset' %}">Lost password?</a>

                <p>If you don't have an account, you can <a href="/accounts/register/">sign up</a> for one.
            </div>
        </section>
    </@layout.put>
</@layout.extends>
