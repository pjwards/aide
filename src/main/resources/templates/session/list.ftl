<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${conference.name} :: Sessions</title>

    <!-- MetisMenu CSS -->
    <link href="/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="/lib/sb-admin/dist/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/lib/sb-admin/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

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
                        Sessions

                        <div class="btn-group pull-right">
                            <a href="/conferences/${conference.id}/admin/sessions/add" class="btn btn-primary btn">
                                Add
                            </a>
                        </div>
                    </h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-hover" id="dataTables">
                                    <thead>
                                    <tr>
                                        <th style="width: 30%">Title</th>
                                        <th style="width: 10%">Begin</th>
                                        <th style="width: 10%">End</th>
                                        <th style="width: 20%">Day</th>
                                        <th style="width: 10%">Room</th>
                                        <th style="width: 10%">Speakers</th>
                                        <th style="width: 5%">Slide</th>
                                        <th style="width: 5%">Video</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#list conference.programDates as date>
                                        <#list date.programs as program>
                                            <#list program.sessions as session>
                                                <tr id="${session.id}">
                                                    <td>${session.title}</td>
                                                    <td>${program.begin}</td>
                                                    <td>${program.end}</td>
                                                    <td>
                                                        <#if program.date??>
                                                        ${program.date.formattedScheduleDay}
                                                        <#else>
                                                            Empty
                                                        </#if>
                                                    </td>
                                                    <td>
                                                        <#if session.room??>
                                                        ${session.room.name}
                                                        <#else>
                                                            Empty
                                                        </#if>
                                                    </td>
                                                    <td>
                                                        <#if session.speakerSet?has_content>
                                                            <#list session.speakerSet as speaker>
                                                            ${speaker.name}
                                                                <#sep>,
                                                            </#list>
                                                        <#else>
                                                            Empty
                                                        </#if>
                                                    </td>
                                                    <td>
                                                        <#if session.slideUrl?? && session.slideUrl?length != 0>
                                                            Exist
                                                        <#else>
                                                            Empty
                                                        </#if>
                                                    </td>
                                                    <td>
                                                        <#if session.videoUrl?? && session.videoUrl?length != 0>
                                                            Exist
                                                        <#else>
                                                            Empty
                                                        </#if>
                                                    </td>
                                                </tr>
                                            </#list>
                                        </#list>
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

    <!-- DataTables JavaScript -->
    <script src="/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/lib/sb-admin/dist/js/sb-admin-2.js"></script>

    <script>
        $(document).ready(function () {
            $('#dataTables').DataTable({
                responsive: true
            });

            $('#dataTables tbody').on( 'click', 'tr', function () {
                var session_id = $(this).attr('id');
                location.href = '/conferences/${conference.id}/admin/sessions/' + session_id;
            } );
        });
    </script>
    </@layout.put>
</@layout.extends>
