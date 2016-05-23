<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->
<#-- @ftlvariable name="conferenceRoles" type="java.util.List<net.study.domain.ConferenceRole>" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${conference.name} :: User list</title>

    <!-- MetisMenu CSS -->
    <link href="/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="/lib/sb-admin/dist/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/lib/sb-admin/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="/bower_components/datatables-responsive/css/responsive.dataTables.scss" rel="stylesheet">

    <!-- jquery-ui css -->
    <link rel="stylesheet" href="/bower_components/jquery-ui/themes/smoothness/jquery-ui.css">

    <!-- Custom Fonts -->
    <link href="http://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic" rel="stylesheet"
          type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">

    <style>
        .ui-widget-header,.ui-state-default{
            background:#b9cd6d;
            border: 1px solid #b9cd6d;
            color: #FFFFFF;
            font-weight: bold;
        }

        #participant.ui-button span.ui-button-text {
            font-weight: bold;
            color: #fff;
            background-color: #5bc0de;
            border-color: #46b8da;
        }

        #speakers.ui-button span.ui-button-text {
            font-weight: bold;
            color: #fff;
            background-color: #337ab7;
            border-color: #2e6da4;
        }

        #manager.ui-button span.ui-button-text {
            font-weight: bold;
            color: #fff;
            background-color: #5cb85c;
            border-color: #4cae4c;
        }

        #host.ui-button span.ui-button-text {
            font-weight: bold;
            color: #fff;
            background-color: #f0ad4e;
            border-color: #eea236;
        }

        #close.ui-button span.ui-button-text {
            font-weight: bold;
            color: #fff;
            background-color: #d9534f;
            border-color: #d43f3a;
        }
    </style>

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
    <div id="dialog-confirm" title="Edit Role">
        <p>Which role do you want to edit?</p>
    </div>

    <div id="wrapper">
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        User Manage Page

                        <div class="btn-group pull-right">
                            <a href="/conferences/${conference.id}/admin/dummy" class="btn btn-primary btn">
                                Register
                            </a>
                        </div>
                    </h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-hover" id="dataTables">
                                    <thead>
                                    <tr>
                                        <th style="width: 30%">Email</th>
                                        <th style="width: 10%">Name</th>
                                        <th style="width: 10%">Last Date</th>
                                        <th style="width: 20%">Role</th>
                                        <th style="width: 10%">Company</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list conferenceRoles as roles>
                                        <#if roles.user??>
                                        <tr id="${roles.user.id}">
                                            <td>${roles.user.email}</td>
                                            <td>${roles.user.name}</td>
                                            <td>${roles.user.lastDate?string("yyyy-MM-dd HH:mm")}</td>
                                            <td>
                                                <a href="#" id="change" class="btn btn-${roles.conferenceRole.color} change_role" role="button" val="${roles.id}">${roles.conferenceRole}</a>
                                            </td>
                                            <td>
                                                <#if roles.user.company??>
                                                ${roles.user.company}
                                                <#else>
                                                    Empty
                                                </#if>
                                            </td>
                                        </tr>
                                        </#if>
                                        </#list>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
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

    <!---jQuery-ui-->
    <script src="/bower_components/jquery-ui/jquery-ui.js"></script>

    <!-- DataTables JavaScript -->
    <script src="/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $('#dataTables').DataTable({
                responsive: true
            });
        });

        $(".change_role").click(function(){
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var data = $(this).attr("val");
            $this = $(this);
            $( "#dialog-confirm" ).dialog({
                resizable: false,
                height: 200,
                width: 500,
                modal: true,
                buttons: [
                    {
                        text: "PARTICIPANT",
                        click: function () {
                            $fthis = $(this);
                            $.ajax({
                                type: "POST",
                                cache: false,
                                contentType: "application/json; charset=UTF-8",
                                processData: false,
                                url: "/conferences/${conference.id}/admin/edit_role",
                                data: JSON.stringify({
                                    "j_data": data,
                                    "j_role": "PARTICIPANT"
                                }),
                                dataType: "json",
                                beforeSend: function (xhr) {
                                    // here it is
                                    xhr.setRequestHeader(header, token);
                                },
                                success: function (result) {
                                    if (result.message !== "200") {
                                        alert("Error Occurred");
                                    } else if (result.message === "200") {
                                        alert("Successfully Changed");
                                        $this.removeClass();
                                        $this.addClass("btn btn-info change_role");
                                        $this.text("PARTICIPANT");
                                        $fthis.dialog("close");
                                    }
                                }
                            });
                        },
                        id: "participant"
                    },
                    {
                        text: "MANAGER",
                        click: function () {
                            $fthis = $(this);
                            $.ajax({
                                type: "POST",
                                cache: false,
                                contentType: "application/json; charset=UTF-8",
                                processData: false,
                                url: "/conferences/${conference.id}/admin/edit_role",
                                data: JSON.stringify({
                                    "j_data": data,
                                    "j_role": "MANAGER"
                                }),
                                dataType: "json",
                                beforeSend: function (xhr) {
                                    // here it is
                                    xhr.setRequestHeader(header, token);
                                },
                                success: function (result) {
                                    if (result.message !== "200") {
                                        alert("Error Occurred");
                                    } else if (result.message === "200") {
                                        alert("Successfully Changed");
                                        $this.removeClass();
                                        $this.addClass("btn btn-primary change_role");
                                        $this.text("MANAGER");
                                        $fthis.dialog("close");
                                    }
                                }
                            });
                        },
                        id: "manager"
                    },
                    {
                        text: "SPEAKER",
                        click: function () {
                            $fthis = $(this);
                            $.ajax({
                                type: "POST",
                                cache: false,
                                contentType: "application/json; charset=UTF-8",
                                processData: false,
                                url: "/conferences/${conference.id}/admin/edit_role",
                                data: JSON.stringify({
                                    "j_data": data,
                                    "j_role": "SPEAKER"
                                }),
                                dataType: "json",
                                beforeSend: function (xhr) {
                                    // here it is
                                    xhr.setRequestHeader(header, token);
                                },
                                success: function (result) {
                                    if (result.message !== "200") {
                                        alert("Error Occurred");
                                    } else if (result.message === "200") {
                                        alert("Successfully Changed");
                                        $this.removeClass();
                                        $this.addClass("btn btn-success change_role");
                                        $this.text("SPEAKER");
                                        $fthis.dialog("close");
                                    }
                                }
                            });
                        },
                        id: "speakers"
                    },
                    {
                        text: "HOST",
                        click: function () {
                            $fthis = $(this);
                            $.ajax({
                                type: "POST",
                                cache: false,
                                contentType: "application/json; charset=UTF-8",
                                processData: false,
                                url: "/conferences/${conference.id}/admin/edit_role",
                                data: JSON.stringify({
                                    "j_data": data,
                                    "j_role": "HOST"
                                }),
                                dataType: "json",
                                beforeSend: function (xhr) {
                                    // here it is
                                    xhr.setRequestHeader(header, token);
                                },
                                success: function (result) {
                                    if (result.message !== "200") {
                                        alert("Error Occurred");
                                    } else if (result.message === "200") {
                                        alert("Successfully Changed");
                                        $this.removeClass();
                                        $this.addClass("btn btn-warning change_role");
                                        $this.text("HOST");
                                        $fthis.dialog("close");
                                    }
                                }
                            });
                        },
                        id: "host"
                    },
                    {
                        text:"Close",
                        click: function() {
                            $( this ).dialog( "close" );
                        },
                        id: "close"
                    }

                ]
            });
        });

    </script>
    </@layout.put>


</@layout.extends>
