<#-- @ftlvariable name="userList" type="java.util.List<net.study.domain.User>" -->
<#-- @ftlvariable name="hasUser" type="java.lang.Boolean" -->

<@layout.extends name="user/userbase.ftl">
    <@layout.put block="head" type="prepend">
    <title>Aide :: Users List</title>
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
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    DataTables Advanced Tables
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
                                    <td>${list.role}</td>
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
    </@layout.put>

</@layout.extends>