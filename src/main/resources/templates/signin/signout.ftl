<@layout.extends name="signin/signinbase.ftl">
    <@layout.put block="head" type="prepend">
        <title>AIDE :: Sign Out</title>
    </@layout.put>

    <@layout.put block="content-head-left" type="replace">
        <h3>Sign out our site</h3>

        <p>You've been signed out.</p>
    </@layout.put>

    <@layout.put block="content-head-right" type="replace">
        <i class="fa fa-sign-out"></i>
    </@layout.put>

    <@layout.put block="contents" type="replace">
        <p>Thanks for stopping by; when you come back, don't forget to <a href="/sign_in">sign in</a>
            again.</p>
        <p>If you want to move the main page, use this <a href="/">link</a>.</p>
    </@layout.put>

    <@layout.put block="footer" type="prepend">
    </@layout.put>

    <@layout.put block="script" type="prepend">
    </@layout.put>
</@layout.extends>