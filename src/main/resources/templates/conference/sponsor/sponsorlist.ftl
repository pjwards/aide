<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->
<#-- @ftlvariable name="sponsorList" type="java.util.List<net.study.domain.Sponsor>" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "/spring.ftl" as spring/>

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${conference.name} :: <@spring.message "conference.sponsor.list"/></title>

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
    <div id="wrapper">
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        <@spring.message "conference.sponsor.list"/>

                        <div class="btn-group pull-right">
                            <a href="/conferences/${conference.id}/admin/sponsor/add" class="btn btn-primary btn">
                                <@spring.message "header.details.register"/>
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
                                        <th style="width: 30%"></th>
                                        <th style="width: 10%"><@spring.message "content.list.name"/></th>
                                        <th style="width: 10%"><@spring.message "conference.sponsor.slug"/></th>
                                        <th style="width: 20%"><@spring.message "conference.sponsor.rank"/></th>
                                        <th style="width: 10%">URL</th>
                                        <th style="width: 10%"><@spring.message "conference.sponsor.delete"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list sponsorList as list>
                                            <tr id="${list.id}">
                                                <td><img src="<#if list.assets??>${list.assets.realPath}<#else>http://placehold.it/100x50</#if>" style="width: 100px; height: 50px" alt="picture"></td>
                                                <td>${list.name}</td>
                                                <td>${list.slug}</td>
                                                <td>${list.rank}</td>
                                                <td>
                                                    <#if list.url??>
                                                    ${list.url}
                                                    <#else>
                                                        Empty
                                                    </#if>
                                                </td>
                                                <td><button type="button" class="btn btn-danger" onclick="sendDelete(${list.id})"><@spring.message "conference.sponsor.delete"/></button></td>
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

    <!-- DataTables JavaScript -->
    <script src="/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $('#dataTables').DataTable({
                responsive: true
            });

            $('#dataTables tbody').on( 'click', 'tr', function () {
                var list_id = $(this).attr('id');
                location.href = "/conferences/${conference.id}/admin/sponsor/"+list_id+"/update";
            } );
        });

        function sendDelete(param) {
            if(!confirm('Do you want to delete this?'))
                return;

            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            console.log(param);
            var url = "/conferences/${conference.id}/admin/sponsor/"+param+"/delete";
            console.log(url);

            $.ajax({
                url: url,
                cache: false,
                contentType: false,
                processData: false,
                type: 'DELETE',
                beforeSend: function(xhr) {
                // here it is
                    xhr.setRequestHeader(header, token);
                }
            });
            setTimeout(function(){ location.reload(); }, 1000);
        }
    </script>
    </script>
    </@layout.put>


</@layout.extends>
