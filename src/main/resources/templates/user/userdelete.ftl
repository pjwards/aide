<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="deleteError" type="java.util.Optional<String>" -->
<#import "/spring.ftl" as spring/>

<@layout.extends name="user/userbase.ftl">
    <@layout.put block="head" type="prepend">
    <title>Aide :: <@spring.message "header.user.delete"/></title>
    </@layout.put>

    <@layout.put block="header" type="replace">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><@spring.message "header.user.delete"/></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    </@layout.put>

    <@layout.put block="contents" type="replace">
    <section>
        <#if deleteError??>
            <div class="alert alert-dismissable alert-danger text-center">
                <button type="button" class="close" data-dismiss="alert">×</button>
                <p>${deleteError}</p>
            </div>
        </#if>
    </section>
    <div class="row">
        <div class="col-lg-12">
            <form role="form" name="form" id="user_delete" action="" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-group <#if deleteError??>has-error</#if>">
                    <label><@spring.message "header.password"/></label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="Password" required>
                </div>
                <button type="submit" class="btn btn-danger"><@spring.message "header.user.delete"/></button>
            </form>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row (nested) -->
    </@layout.put>

    <@layout.put block="footer" type="replace">
    </@layout.put>

    <@layout.put block="script" type="replace">
    </@layout.put>
</@layout.extends>