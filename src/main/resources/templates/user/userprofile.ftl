<#-- @ftlvariable name="user" type="com.pjwards.aide.domain.User" -->
<#import "/spring.ftl" as spring/>

<@layout.extends name="user/userbase.ftl">
    <@layout.put block="head" type="prepend">
    <title>Aide :: <@spring.message "header.user.profile"/></title>
    </@layout.put>

    <@layout.put block="header" type="replace">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"><@spring.message "header.user.profile"/></h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </@layout.put>

    <@layout.put block="contents" type="replace">
        <div class="row">
            <div class="col-lg-12">
                <span>
                    <img id="avatar" src="<#if user.assets??>${user.assets.realPath}<#else>/basic/img/user.png</#if>" alt="picture" class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                </span>
                <form role="form">
                    <div class="form-group">
                        <label><@spring.message "content.list.name"/></label>
                        <p class="form-control-static">${user.name}</p>
                    </div>
                    <div class="form-group">
                        <label><@spring.message "content.list.email"/></label>
                        <p class="form-control-static">${user.email}</p>
                    </div>
                    <div class="form-group">
                        <label><@spring.message "content.list.create"/></label>
                        <p class="form-control-static">${user.createdDate?string("yyyy-MM-dd HH:mm")}</p>
                    </div>
                    <div class="form-group">
                        <label><@spring.message "content.list.company"/></label>
                        <p class="form-control-static">${user.company}</p>
                    </div>
                    <div class="form-group">
                        <label><@spring.message "content.list.description"/></label>
                        <p class="form-control-static">${user.description}</p>
                    </div>
                </form>
                <div class="edit-info">
                    <a class="btn btn-info" href="/settings/update" role="button"><@spring.message "user.update.profile"/></a>
                </div>
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