<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->

<from action="/api/conferences" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <input type="text" name="name" value="name"/>
    <input type="text" name="slogan" value="slogan"/>
    <input type="text" name="description" value="description"/>

    <input type="submit" value="submit"/>
</from>