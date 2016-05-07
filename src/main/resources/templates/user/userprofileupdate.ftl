<#-- @ftlvariable name="nameError" type="java.util.Optional<String>" -->
<#-- @ftlvariable name="nameSuccess" type="java.util.Optional<String>" -->
<#-- @ftlvariable name="avatarSuccess" type="java.util.Optional<String>" -->
<#-- @ftlvariable name="companySuccess" type="java.util.Optional<String>" -->
<#-- @ftlvariable name="descriptionSuccess" type="java.util.Optional<String>" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->

<@layout.extends name="user/userbase.ftl">
    <@layout.put block="head" type="prepend">
    <title>Aide :: Profile Edit</title>
    </@layout.put>

    <@layout.put block="header" type="replace">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Profile Edit</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    </@layout.put>

    <@layout.put block="contents" type="replace">
    <section>
        <#if nameError??>
            <div class="alert alert-dismissable alert-danger text-center">
                <button type="button" class="close" data-dismiss="alert">×</button>
                <p>${nameError}</p>
            </div>
        </#if>
        <#if nameSuccess??>
            <div class="alert alert-dismissable alert-success text-center">
                <button type="button" class="close" data-dismiss="alert">×</button>
                <p>${nameSuccess}</p>
            </div>
        </#if>
        <#if avatarSuccess??>
            <div class="alert alert-dismissable alert-success text-center">
                <button type="button" class="close" data-dismiss="alert">×</button>
                <p>${avatarSuccess}</p>
            </div>
        </#if>
        <#if companySuccess??>
            <div class="alert alert-dismissable alert-success text-center">
                <button type="button" class="close" data-dismiss="alert">×</button>
                <p>${companySuccess}</p>
            </div>
        </#if>
        <#if descriptionSuccess??>
            <div class="alert alert-dismissable alert-success text-center">
                <button type="button" class="close" data-dismiss="alert">×</button>
                <p>${descriptionSuccess}</p>
            </div>
        </#if>
    </section>
    <div class="row">
        <div class="col-lg-12">
            <form role="form" action="" method="post" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="form-group <#if nameError??>has-error</#if>"">
                    <label>Name</label>
                    <input class="form-control" type="text" name="name" id="name" placeholder="Name" value="${currentUser.name}" required>
                </div>
                <div class="form-group">
                    <label>Company</label>
                    <input class="form-control" type="text" name="company" id="company" placeholder="Company" value="${currentUser.company}">
                </div>
                <div class="form-group">
                    <label>Description</label>
                    <textarea class="form-control" rows="3" name="description" id="description">${currentUser.description}</textarea>
                </div>
                <div class="form-group">
                    <label>Thumbnail</label>
                    <input type="text" readonly="" class="form-control floating-label" placeholder="Browse...">
                    <input type="file" name="file" id="inputFile" multiple="">
                </div>

                <button type="submit" class="btn btn-info">Update Profile</button>
            </form>
            <img id="avatar" src="<#if currentUser.assets??>/static/${currentUser.assets.realPath}<#else>/img/user.png</#if>" alt="picture" class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
        </div>
        <!-- /.col-lg-12-->
    </div>
    <!-- /.row (nested) -->
    </@layout.put>

    <@layout.put block="footer" type="replace">
    </@layout.put>

    <@layout.put block="script" type="replace">
    <script type="text/javascript">
        $("input[name='file']").on("change", function(){
            // Get a reference to the fileList
            var files = !!this.files ? this.files : [];
            // If no files were selected, or no FileReader support, return
            if ( !files.length || !window.FileReader ) return;
            // Only proceed if the selected file is an image
            if ( /^image/.test( files[0].type ) ) {
                // Create a new instance of the FileReader
                var reader = new FileReader();
                // Read the local file as a DataURL
                reader.readAsDataURL( files[0] );
                // When loaded, set image data as background of page
                reader.onloadend = function(){
                    $("#avatar").attr("src", this.result);
                }
            }
        });
    </script>
    </@layout.put>

</@layout.extends>