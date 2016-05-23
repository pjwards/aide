<#import "/spring.ftl" as spring/>
<#-- @ftlvariable name="conferences" type="java.util.List<com.pjwards.aide.domain.Conference>" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->
<#-- @ftlvariable name="rc" type="javax.servlet.http.HttpServletRequest" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="form2" type="com.pjwards.aide.domain.forms.SignUpForm" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${myApp.name} :: Conference :: Dummy</title>

    <!-- MetisMenu CSS -->
    <link href="/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/lib/sb-admin/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

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
        <@spring.bind "form2" />

    <section>
        <div id="wrapper">
            <div id="page-wrapper">
                <#if spring.status.error>
                    <div class="alert alert-dismissable alert-danger text-center">
                        <button type="button" class="close" data-dismiss="alert">×</button>
                        <#list spring.status.errorMessages as error>
                            <p>${error}</p>

                            <#if error?contains("email") || error?contains("Email")><#global errorEmail=true></#if>
                            <#if error?contains("Name")><#global errorName=true></#if>
                            <#if error?contains("Passwords") || error?contains("Password")><#global errorPassword=true></#if>
                            <#if error?contains("Passwords") || error?contains("PasswordRepeated")><#global errorPasswordRepeated=true></#if>
                            <#if error?contains("Company")><#global errorCompany=true></#if>
                            <#if error?contains("Description")><#global errorDescription=true></#if>
                            <#if error?contains("File")><#global errorFile=true></#if>
                        </#list>
                    </div>
                </#if>

                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Add Dummy User
                        </h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-12" style="text-align: center;">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <form role="form" action="" method="post" enctype="multipart/form-data">
                                    <div class="panel-group" id="accordion">
                                        <div class="panel panel-success">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">Basic Information</a>
                                                </h4>
                                            </div>
                                            <div id="collapseOne" class="panel-collapse collapse in">
                                                <div class="panel-body">
                                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                                    <div class="form-group <#if errorEmail??>has-error</#if>"">
                                                    <input type="email" placeholder="Email *"
                                                           class="form-email form-control"
                                                           id="email" name="email" required value="${form2.email}">
                                                    </div>

                                                    <div class="form-group <#if errorName??>has-error</#if>">
                                                        <input type="text" placeholder="Name *"
                                                               class="form-username form-control"
                                                               id="name" name="name" required value="${form2.name}">
                                                    </div>

                                                    <div class="form-group <#if errorPassword??>has-error</#if>">
                                                        <input type="password" placeholder="Password *"
                                                               class="form-password form-control"
                                                               id="password" name="password" required value=${form2.password}>
                                                    </div>
                                                    <div class="form-group <#if errorPasswordRepeated??>has-error</#if>">
                                                        <input type="password" placeholder="Password again *"
                                                               class="form-password form-control"
                                                               id="passwordRepeated" name="passwordRepeated" required value="${form2.passwordRepeated}">
                                                    </div>
                                                </div>
                                                <!-- /.panel-body -->
                                            </div>
                                            <!-- /.collapse -->
                                        </div>
                                        <!-- /.panel -->
                                    </div>

                                    <div class="panel-group" id="accordion">
                                        <div class="panel panel-warning">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">Details Information</a>
                                                </h4>
                                            </div>
                                            <div id="collapseTwo" class="panel-collapse collapse">
                                                <div class="panel-body">

                                                    <div class="form-group <#if errorCompany??>has-error</#if>">
                                                        <input type="text" placeholder="Company"
                                                               class="form-username form-control"
                                                               id="company" name="company" required value="${form2.company}">
                                                    </div>

                                                    <div class="form-group <#if errorDescription??>has-error</#if>" style="text-align: left">
                                                        <textarea class="form-control" id="summernote" name="description" placeholder="Description *"> <#if form2.description?? && form2.description != "">${form2.description}<#else>Description *</#if></textarea>
                                                    </div>

                                                    <div class="form-group <#if errorFile??>has-error</#if>">
                                                        <input type="file" name="file">
                                                    </div>
                                                </div>
                                                <!-- /.panel-body -->
                                            </div>
                                            <!-- /.collapse -->
                                        </div>
                                        <!-- /.panel -->
                                    </div>
                                    <!-- /.panel group -->
                                    <button type="submit" class="btn btn-default">Register</button>
                                </form>
                            </div>
                            <!-- /.panel-body -->
                        </div>
                        <!-- /.panel -->
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /#page-wrapper -->
        </div>
        <!-- /#wrapper -->
    </section>

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

    <script src="/js/summernote.js"></script>

    </@layout.put>
</@layout.extends>

