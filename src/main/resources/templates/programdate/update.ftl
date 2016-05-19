<#import "/spring.ftl" as spring/>
<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->
<#-- @ftlvariable name="programDate" type="com.pjwards.aide.domain.ProgramDate" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->
<#-- @ftlvariable name="form" type="com.pjwards.aide.domain.forms.ProgramDateForm" -->
<#-- @ftlvariable name="rc" type="javax.servlet.http.HttpServletRequest" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${myApp.name} :: Days :: Update</title>

    <!-- Custom CSS -->
    <link href="/lib/sb-admin/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- jQuery UI CSS -->
    <link href="/bower_components/jquery-ui/themes/smoothness/jquery-ui.min.css" rel="stylesheet">

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
        <@spring.bind "form" />

    <section>
        <div id="wrapper">
            <div id="page-wrapper">
                <#if spring.status.error>
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="alert alert-dismissable alert-danger text-center">
                                <button type="button" class="close" data-dismiss="alert">×</button>
                                <#list spring.status.errorMessages as error>
                                    <p>${error}</p>

                                    <#if error?contains("Name")><#global errorName=true></#if>
                                    <#if error?contains("Day")><#global errorDay=true></#if>
                                </#list>
                            </div>
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                </#if>
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Day Update
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
                                                    <input type="hidden" name="conferenceId" value="${conference.id}"/>

                                                    <div class="form-group <#if errorName??>has-error</#if>">
                                                        <input class="form-control" name="name" placeholder="Name *" value="${form.name}">
                                                    </div>

                                                    <div class="form-group <#if errorDay??>has-error</#if>">
                                                        <input class="form-control" name="day" id="day" placeholder="Day *" value="${form.day}">
                                                    </div>
                                                </div>
                                                <!-- /.panel-body -->
                                            </div>
                                            <!-- /.collapse -->
                                        </div>
                                        <!-- /.panel -->
                                    </div>
                                    <!-- /.panel group -->
                                    <button type="button" class="btn btn-danger" onclick="sendDelete()">Delete</button>
                                    <button type="submit" class="btn btn-default">Update</button>
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
    <!-- jQuery UI -->
    <script src="/bower_components/jquery-ui/jquery-ui.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/lib/sb-admin/dist/js/sb-admin-2.js"></script>

    <script>
        $(function () {
            $("#day").datepicker({
                dateFormat: 'yy-mm-dd',
                showMonthAfterYear: true,
                autoSize: true,
                showOtherMonths: true,
                selectOtherMonths: true,
                changeMonth: true,
                changeYear: true
            });
        });

        function sendDelete() {
            if(!confirm('Do you want to delete this?'))
                    return;

            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                url: "/conferences/${conference.id}/admin/days/${programDate.id}",
                cache: false,
                contentType: false,
                processData: false,
                type: 'DELETE',
                beforeSend: function(xhr) {
                    // here it is
                    xhr.setRequestHeader(header, token);
                }
            });
            location.href = "/conferences/" + ${conference.id} + "/admin/days";
        }
    </script>
    </@layout.put>
</@layout.extends>
