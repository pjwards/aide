<#import "/spring.ftl" as spring/>
<#-- @ftlvariable name="rc" type="javax.servlet.http.HttpServletRequest" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${myApp.name} :: main</title>
    </@layout.put>

    <@layout.put block="header" type="prepend">
        <@layout.extends name="layouts/index/header.ftl">
        </@layout.extends>
    </@layout.put>

    <@layout.put block="contents">
    Language : <a href="?lang=en_US">English</a>|<a href="?lang=ko_KR">Korea</a>
    <h3><@spring.message "hello.test"/>, world</h3>
    Current Locale: ${rc.locale}

    </@layout.put>

    <@layout.put block="footer" type="replace">
        <@layout.extends name="layouts/footer.ftl">
        </@layout.extends>
    </@layout.put>

    <@layout.put block="script">
    </@layout.put>
</@layout.extends>