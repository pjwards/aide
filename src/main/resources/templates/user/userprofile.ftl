<#-- @ftlvariable name="user" type="com.pjwards.aide.domain.User" -->

<@layout.extends name="user/userbase.ftl">
    <@layout.put block="head" type="prepend">
    <title>Aide :: Profile</title>
    </@layout.put>

    <@layout.put block="header" type="replace">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Profile</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </@layout.put>

    <@layout.put block="contents" type="replace">
        <div class="row">
            <div class="col-lg-12">
                <form role="form">
                    <div class="form-group">
                        <img class="circle" src="<#if user.assets??>${user.assets.realPath}<#else>/basic/img/user.png</#if>" alt="icon">
                    </div>
                    <div class="form-group">
                        <label>Name</label>
                        <p class="form-control-static">${user.name}</p>
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <p class="form-control-static">${user.email}</p>
                    </div>
                    <div class="form-group">
                        <label>Company</label>
                        <p class="form-control-static">${user.company}</p>
                    </div>
                    <div class="form-group">
                        <label>Sign up Date</label>
                        <p class="form-control-static">${user.createdDate?string("yyyy-MM-dd HH:mm")}</p>
                    </div>
                    <div class="form-group">
                        <label>Description</label>
                        <p class="form-control-static">${user.description}</p>
                    </div>
                </form>
                <div class="edit-info">
                    <a class="btn btn-info" href="/settings/update" role="button">Update Profile</a>
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