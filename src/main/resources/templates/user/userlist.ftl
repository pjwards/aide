<#-- @ftlvariable name="userList" type="java.util.List<net.study.domain.User>" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "/spring.ftl" as spring/>

<@layout.extends name="user/userbase.ftl">
    <@layout.put block="head" type="prepend">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title>Aide :: <@spring.message "header.user.list"/></title>

    <!-- jquery-ui css -->
    <link rel="stylesheet" href="/bower_components/jquery-ui/themes/smoothness/jquery-ui.css">

    <!-- DataTables CSS -->
    <link href="/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="/bower_components/datatables-responsive/css/responsive.dataTables.scss" rel="stylesheet">

    <style>
        .ui-widget-header,.ui-state-default{
            background:#b9cd6d;
            border: 1px solid #b9cd6d;
            color: #FFFFFF;
            font-weight: bold;
        }

        #admin.ui-button span.ui-button-text {
            font-weight: bold;
            color: #fff;
            background-color: #5cb85c;
            border-color: #4cae4c;
        }

        #user.ui-button span.ui-button-text {
            font-weight: bold;
            color: #fff;
            background-color: #5bc0de;
            border-color: #46b8da;
        }

        #close.ui-button span.ui-button-text {
            font-weight: bold;
            color: #fff;
            background-color: #d9534f;
            border-color: #d43f3a;
        }
    </style>

    </@layout.put>

    <@layout.put block="header" type="replace">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><@spring.message "header.user.list"/></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    </@layout.put>

    <@layout.put block="contents" type="replace">
    <div id="dialog-confirm" title="<@spring.message "content.list.change"/>">
        <p><@spring.message "content.list.account"/></p>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="dataTable_wrapper">
                        <table class="table table-hover" id="dataTables">
                            <thead>
                            <tr>
                                <th style="width: 30%"><@spring.message "content.list.email"/></th>
                                <th style="width: 10%"><@spring.message "content.list.name"/></th>
                                <th style="width: 10%"><@spring.message "content.list.create"/></th>
                                <th style="width: 20%"><@spring.message "content.list.last"/></th>
                                <th style="width: 10%"><@spring.message "content.list.role"/></th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list userList as list>
                                <tr id="${list.id}">
                                    <td>
                                        <a href="/settings/admin/update/${list.id}">${list.email}
                                    </td>
                                    <td>${list.name}</td>
                                    <td>${list.createdDate?string("yyyy-MM-dd HH:mm")}</td>
                                    <td>${list.lastDate?string("yyyy-MM-dd HH:mm")}</td>
                                    <td>
                                        <a href="#" id="change" class="btn btn-${list.role.color} change_role" role="button" val="${list.email}">${list.role}</a>
                                    </td>
                                </tr>
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
    </@layout.put>

    <@layout.put block="footer" type="replace">
    </@layout.put>

    <@layout.put block="script" type="replace">
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
            var emailed = $(this).attr("val");
            $this = $(this);
            $( "#dialog-confirm" ).dialog({
                resizable: false,
                height:140,
                modal: true,
                buttons: [
                    {
                        text: "Admin",
                        click: function () {
                            $fthis = $(this);
                            $.ajax({
                                type: "POST",
                                cache: false,
                                contentType: "application/json; charset=UTF-8",
                                processData: false,
                                url: "/settings/edit_role",
                                data: JSON.stringify({
                                    "j_username": emailed,
                                    "j_role": "Admin"
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
                                        $this.text("ADMIN");
                                        $fthis.dialog("close");
                                    }
                                }
                            });
                        },
                        id: "admin"
                    },
                    {
                        text: "User",
                        click: function () {
                            $fthis = $(this);
                            $.ajax({
                                type: "POST",
                                cache: false,
                                contentType: "application/json; charset=UTF-8",
                                processData: false,
                                url: "/settings/edit_role",
                                data: JSON.stringify({
                                    "j_username": emailed,
                                    "j_role": "User"
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
                                        $this.text("USER");
                                        $fthis.dialog("close");
                                    }
                                }
                            });
                        },
                        id: "user"
                    },
                    {
                        text: "Close",
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