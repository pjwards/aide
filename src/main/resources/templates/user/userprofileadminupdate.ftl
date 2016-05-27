<#-- @ftlvariable name="nameError" type="java.util.Optional<String>" -->
<#-- @ftlvariable name="nameSuccess" type="java.util.Optional<String>" -->
<#-- @ftlvariable name="avatarSuccess" type="java.util.Optional<String>" -->
<#-- @ftlvariable name="companySuccess" type="java.util.Optional<String>" -->
<#-- @ftlvariable name="descriptionSuccess" type="java.util.Optional<String>" -->
<#-- @ftlvariable name="user" type="com.pjwards.aide.domain.User" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "/spring.ftl" as spring/>

<@layout.extends name="user/userbase.ftl">
    <@layout.put block="head" type="prepend">
    <title>Aide :: <@spring.message "header.user.update"/></title>

    <!-- Summernote CSS -->
    <link rel="stylesheet" type="text/css" href="/bower_components/summernote/dist/summernote.css"/>

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    </@layout.put>

    <@layout.put block="header" type="replace">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><@spring.message "header.user.update"/></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    </@layout.put>

    <@layout.put block="contents" type="replace">
    <div class="row">
        <div class="col-lg-12">
        <#if nameError??>
            <div class="alert alert-dismissable alert-danger text-center">
                <button type="button" class="close" data-dismiss="alert">×</button>
                <p>${nameError}</p>
            </div>
        <#else>
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
        </#if>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <form role="form" action="" method="post" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="${user.id}"/>

                <div class="form-group <#if nameError??>has-error</#if>"">
                    <label><@spring.message "content.list.name"/></label>
                    <input class="form-control" type="text" name="name" id="name" placeholder="Name" value="${user.name}" required>
                </div>
                <div class="form-group">
                    <label><@spring.message "content.list.company"/></label>
                    <input class="form-control" type="text" name="company" id="company" placeholder="" value="${user.company}">
                </div>
                <div class="form-group">
                    <label><@spring.message "content.list.description"/></label>
                    <textarea class="form-control" id="summernote" name="description" placeholder=""><#if user.description?? && user.description != "">${user.description}<#else></#if></textarea>
                </div>
                <div class="form-group">
                    <label><@spring.message "content.list.thumbnail"/></label>
                    <img id="avatar" src="<#if user.assets??>${user.assets.realPath}<#else>/basic/img/user.png</#if>" alt="picture" class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                    <input type="text" readonly="" class="form-control floating-label" placeholder="Browse...">
                    <input type="file" name="file" id="inputFile" multiple="">
                </div>
                <button type="submit" class="btn btn-info"><@spring.message "user.update.profile"/></button>
            </form>
        </div>
        <!-- /.col-lg-12-->
    </div>
    <!-- /.row (nested) -->
    </@layout.put>

    <@layout.put block="footer" type="replace">
    </@layout.put>

    <@layout.put block="script" type="replace">
    <script type="text/javascript">
        $(document).ready(function() {
            $('#summernote').summernote({
                height: 300,                 // set editor height
                minHeight: null,             // set minimum height of editor
                maxHeight: null,             // set maximum height of editor
                focus: true,                 // set focus to editable area after initializing summernote
                callbacks: {
                    onImageUpload: function(files) {
                        // upload image to server and create imgNode...
                        for(var i = 0; i < files.length; i++)
                            sendFile(files[i]);
                    }
                }
            });

            function sendFile(file) {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                var data = new FormData();
                data.append("file", file);
                $.ajax({
                    url: "/upload/images",
                    data: data,
                    cache: false,
                    contentType: false,
                    processData: false,
                    type: 'POST',
                    beforeSend: function(xhr) {
                        // here it is
                        xhr.setRequestHeader(header, token);
                    },
                    success: function(data) {
                        $('#summernote').summernote('insertImage', data.assets.realPath, data.assets.name);
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log(textStatus + " " + errorThrown);
                    }

                })
            }
        });

        $(function (){
            $('input[type="file"]').change(function(){
                $("#assets").empty();
                for(var i=0; this.files.length; i++) {
                    var file = this.files[i];
                    $("#assets").append('<option>'+file.name+'</option>');
                }
            });
        });
    </script>

    <!-- Summernote Core JS -->
    <script src="/bower_components/summernote/dist/summernote.min.js"></script>
    <script src="/bower_components/summernote/lang/summernote-ko-KR.js"></script>
    </@layout.put>

</@layout.extends>