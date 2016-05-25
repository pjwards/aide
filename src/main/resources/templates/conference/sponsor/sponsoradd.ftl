<#-- @ftlvariable name="form" type="com.pjwards.aide.domain.forms.SponsorAddForm" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="rc" type="javax.servlet.http.HttpServletRequest" -->

<#import "/spring.ftl" as spring>

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title><@spring.message "conference.sponsor.name"/> :: <@spring.message "header.details.register"/></title>

    <!-- MetisMenu CSS -->
    <link href="/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="/lib/sb-admin/dist/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/lib/sb-admin/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="http://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic" rel="stylesheet"
          type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    </@layout.put>

    <@layout.put block="header" type="prepend">
        <@layout.extends name="layouts/header/admin.ftl">
        </@layout.extends>
    </@layout.put>

    <@layout.put block="contents">
        <@spring.bind "form" />
        <#if spring.status.error>
        <div class="row">
            <div class="col-lg-12">
                <div class="alert alert-dismissable alert-danger text-center">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <#list spring.status.errorMessages as error>
                        <p>${error}</p>

                        <#if error?contains("Name")><#global errorName=true></#if>
                        <#if error?contains("Slug")><#global errorSlug=true></#if>
                        <#if error?contains("Url")><#global errorUrl=true></#if>
                        <#if error?contains("Files")><#global errorFiles=true></#if>
                        <#if error?contains("Rank")><#global errorRank=true></#if>
                    </#list>
                </div>
            </div>
        </div>
        </#if>

    <div id="wrapper">
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        <@spring.message "conference.sponsor.name"/> <@spring.message "header.details.register"/>
                    </h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <form role="form" action="" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">

                        </div>
                        <div class="form-group <#if errorName??>has-error</#if>"">
                            <label><@spring.message "content.list.name"/></label>
                            <input class="form-control" type="text" name="name" id="name" placeholder="Name" value="${form.name}" required>
                        </div>
                        <div class="form-group <#if errorSlug??>has-error</#if>"">
                            <label><@spring.message "conference.sponsor.slug"/></label>
                            <input class="form-control" type="text" name="slug" id="slug" placeholder="Slug" value="${form.slug}" required>
                        </div>
                        <div class="form-group <#if errorUrl??>has-error</#if>"">
                            <label>URL</label>
                            <input type="text" class="form-control" id="basic-url" name="url" value="${form.url}">
                        </div>
                        <div class="form-group <#if errorUrl??>has-error</#if>"">
                            <label><@spring.message "conference.sponsor.rank"/>(1 ~ 100)</label>
                            <input class="form-control" type="number" name="rank" id="rank" min="1" max="100" value="${form.rank}" required>
                        </div>
                        <div class="form-group">
                            <label><@spring.message "content.list.description"/></label>
                            <textarea class="form-control" id="summernote" name="description" placeholder=""><#if form.description?? && form.description != "">${form.description}</#if></textarea>
                        </div>
                        <div class="form-group <#if errorFiles??>has-error</#if>"">
                            <label><@spring.message "conference.sponsor.image"/></label>
                            <input type="text" readonly="" class="form-control floating-label" placeholder="Browse...">
                            <input type="file" name="assets" id="inputFile" multiple="">
                        </div>
                        <button type="submit" class="btn btn-info"><@spring.message "header.details.register"/></button>
                    </form>
                </div>
            </div>
            <!-- /.col-lg-12-->
        </div>
        <!-- /.row (nested) -->
    </div>
        <!-- /#page-wrapper -->
    <!-- /#wrapper -->
    </@layout.put>

    <@layout.put block="footer" type="replace">
        <@layout.extends name="layouts/footer.ftl">
        </@layout.extends>
    </@layout.put>

    <@layout.put block="script">
    <!-- Metis Menu Plugin JavaScript -->
    <script src="/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/lib/sb-admin/dist/js/sb-admin-2.js"></script>

    <script>
        $(document).ready(function () {
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
    </@layout.put>
</@layout.extends>
