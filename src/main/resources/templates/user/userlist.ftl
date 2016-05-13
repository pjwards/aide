<#-- @ftlvariable name="userList" type="java.util.List<net.study.domain.User>" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->

<@layout.extends name="user/userbase.ftl">
    <@layout.put block="head" type="prepend">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title>Aide :: Users List</title>

    <!-- jquery-ui css -->
    <link rel="stylesheet" href="/bower_components/jquery-ui/themes/smoothness/jquery-ui.css">

    </@layout.put>

    <@layout.put block="header" type="replace">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Users List</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    </@layout.put>

    <@layout.put block="contents" type="replace">
    <div id="dialog-confirm" title="Change Role">
        <p>Which role would you change?</p>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    User List
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <div class="dataTable_wrapper">
                        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                            <tr>
                                <th>Email</th>
                                <th>Name</th>
                                <th>Created Date</th>
                                <th>Last Access Date</th>
                                <th>Role</th>
                            </tr>
                            </thead>

                            <tbody>
                            <#if hasUser == false>
                                <tr>
                                    <td colspan="5">
                                        Can not found users.
                                    </td>
                                </tr>
                            <#else>
                                <#list userList as list>
                                <tr>
                                    <td>${list.email}</td>
                                    <td>${list.name}</td>
                                    <td>${list.createdDate?string("yyyy-MM-dd HH:mm")}</td>
                                    <td>${list.lastDate?string("yyyy-MM-dd HH:mm")}</td>
                                    <td><a href="#" id="change" class="btn btn-info change_role" role="button" val="${list.email}">${list.role}</a></td>
                                </tr>
                                </#list>
                            </#if>
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

    <script type="text/javascript">
        $(".change_role").click(function(){
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var emailed = $(this).attr("val");
            $this = $(this);
            $( "#dialog-confirm" ).dialog({
                resizable: false,
                height:140,
                modal: true,
                buttons: {
                    "Admin": function() {
                        $.ajax({
                            type:"POST",
                            cache: false,
                            contentType: "application/json; charset=UTF-8",
                            processData: false,
                            url:"/settings/edit_role",
                            data : JSON.stringify({
                                "j_username" : emailed,
                                "j_role" : "Admin"
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
                                    $this.text("ADMIN");
                                }
                            }
                        });
                    },
                    "User": function() {
                        $.ajax({
                            type:"POST",
                            cache: false,
                            contentType: "application/json; charset=UTF-8",
                            processData: false,
                            url:"/settings/edit_role",
                            data : JSON.stringify({
                                "j_username" : emailed,
                                "j_role" : "User"
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
                                    $this.text("USER");
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