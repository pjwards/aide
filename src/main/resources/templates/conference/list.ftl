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
    <div id="dialog-confirm" title="Change Role">
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
                                        <#if roles.userSet??>
                                        <#list roles.userSet as user>
                                        <tr id="${user.id}">
                                            <td>${user.email}</td>
                                            <td>${user.name}</td>
                                            <td>${user.lastDate?string("yyyy-MM-dd HH:mm")}</td>
                                            <td>
                                                <a href="#" id="change" class="btn btn-info change_role" role="button" val="${roles.id}" val2="${user.id}">${roles.conferenceRole}</a>
                                            </td>
                                            <td>
                                                <#if user.company??>
                                                ${user.company}
                                                <#else>
                                                    Empty
                                                </#if>
                                            </td>
                                        </tr>
                                        </#list>
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
            var data2 = $(this).attr("val2");
            console.log(this);
            console.log(data);
            console.log(data2);
            $this = $(this);
            $( "#dialog-confirm" ).dialog({
                resizable: false,
                height:140,
                modal: true,
                buttons: {
                    "PARTICIPANT": function() {
                        $.ajax({
                            type:"POST",
                            cache: false,
                            contentType: "application/json; charset=UTF-8",
                            processData: false,
                            url:"/conferences/${conference.id}/admin/edit_role",
                            data : JSON.stringify({
                                "j_data" : data,
                                "j_role" : "PARTICIPANT",
                                "j_data2": data2
                            }),
                            dataType : "json",
                            beforeSend: function(xhr) {
                                // here it is
                                xhr.setRequestHeader(header, token);
                            },
                            success:function(result){
                                if(result.message !== "200"){
                                    alert("Error Occurred");
                                }else if(result.message === "200"){
                                    alert("Successfully Changed");
                                    console.log($this);
                                    $this.text("PARTICIPANT");
                                }
                            }
                        });
                    },
                    "MANAGER": function() {
                        $.ajax({
                            type:"POST",
                            cache: false,
                            contentType: "application/json; charset=UTF-8",
                            processData: false,
                            url:"/conferences/${conference.id}/admin/edit_role",
                            data : JSON.stringify({
                                "j_data" : data,
                                "j_role" : "MANAGER",
                                "j_data2": data2
                            }),
                            dataType : "json",
                            beforeSend: function(xhr) {
                                // here it is
                                xhr.setRequestHeader(header, token);
                            },
                            success:function(result){
                                if(result.message !== "200"){
                                    alert("Error Occurred");
                                }else if(result.message === "200"){
                                    alert("Successfully Changed");
                                    console.log($this);
                                    $this.text("MANAGER");
                                }
                            }
                        });
                    },
                    "SPEAKER": function() {
                        $.ajax({
                            type:"POST",
                            cache: false,
                            contentType: "application/json; charset=UTF-8",
                            processData: false,
                            url:"/conferences/${conference.id}/admin/edit_role",
                            data : JSON.stringify({
                                "j_data" : data,
                                "j_role" : "SPEAKER",
                                "j_data2": data2
                            }),
                            dataType : "json",
                            beforeSend: function(xhr) {
                                // here it is
                                xhr.setRequestHeader(header, token);
                            },
                            success:function(result){
                                if(result.message !== "200"){
                                    alert("Error Occurred");
                                }else if(result.message === "200"){
                                    alert("Successfully Changed");
                                    console.log($this);
                                    $this.text("SPEAKER");
                                }
                            }
                        });
                    },
                    "HOST": function() {
                        $.ajax({
                            type:"POST",
                            cache: false,
                            contentType: "application/json; charset=UTF-8",
                            processData: false,
                            url:"/conferences/${conference.id}/admin/edit_role",
                            data : JSON.stringify({
                                "j_data" : data,
                                "j_role" : "HOST",
                                "j_data2": data2
                            }),
                            dataType : "json",
                            beforeSend: function(xhr) {
                                // here it is
                                xhr.setRequestHeader(header, token);
                            },
                            success:function(result){
                                if(result.message !== "200"){
                                    alert("Error Occurred");
                                }else if(result.message === "200"){
                                    alert("Successfully Changed");
                                    console.log($this);
                                    $this.text("HOST");
                                }
                            }
                        });
                    },
                    Close: function() {
                        $( this ).dialog( "close" );
                    }
                }
            });
        });

    </script>
    </@layout.put>


</@layout.extends>
