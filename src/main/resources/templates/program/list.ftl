<#import "/spring.ftl" as spring/>

<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${conference.name} :: Programs</title>

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
        <#if conference.isHost(currentUser.user)>
            <@layout.extends name="layouts/header/admin.ftl">
            </@layout.extends>
        <#else>
            <@layout.extends name="layouts/header/admin_2.ftl">
            </@layout.extends>
        </#if>
    </@layout.put>

    <@layout.put block="contents">
    <div id="wrapper">
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        <@spring.message "program.header"/>

                        <div class="btn-group pull-right">
                            <a href="/conferences/${conference.id}/admin/programs/add" class="btn btn-primary btn">
                                <@spring.message "form.btn.add"/>
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
                                        <th style="width: 30%"><@spring.message "form.title"/></th>
                                        <th style="width: 10%"><@spring.message "form.begin"/></th>
                                        <th style="width: 10%"><@spring.message "form.end"/></th>
                                        <th style="width: 20%"><@spring.message "form.day"/></th>
                                        <th style="width: 10%"><@spring.message "form.room"/></th>
                                        <th style="width: 10%"><@spring.message "form.speaker"/></th>
                                        <th style="width: 5%"><@spring.message "form.slide"/></th>
                                        <th style="width: 5%"><@spring.message "form.video"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#if conference.isHost(currentUser.user) || conference.isManager(currentUser.user)>
                                            <#global authentication=true>
                                        <#else>
                                            <#global authentication=false>
                                        </#if>
                                    <#list conference.programDates as date>
                                        <#list date.programs as program>
                                            <#if authentication || program.isSpeakerOnlyProgram(currentUser.user)>
                                                <tr id="${program.id}">
                                                    <td>${program.title}</td>
                                                    <td>${program.begin}</td>
                                                    <td>${program.end}</td>
                                                    <td>
                                                        <#if program.date??>
                                                            ${program.date.formattedScheduleDay}
                                                        <#else>
                                                            <@spring.message "form.empty"/>
                                                        </#if>
                                                    </td>
                                                    <td>
                                                        <#if program.room??>
                                                            ${program.room.name}
                                                        <#else>
                                                            <@spring.message "form.empty"/>
                                                        </#if>
                                                    </td>
                                                    <td>
                                                        <#if program.speakerSet?has_content>
                                                                <#list program.speakerSet as speaker>
                                                                ${speaker.name}
                                                                    <#sep>,
                                                                </#list>
                                                        <#else>
                                                            <@spring.message "form.empty"/>
                                                        </#if>
                                                    </td>
                                                    <td>
                                                        <#if program.slideUrl?? && program.slideUrl?length != 0>
                                                            <@spring.message "form.exist"/>
                                                        <#else>
                                                            <@spring.message "form.empty"/>
                                                        </#if>
                                                    </td>
                                                    <td>
                                                        <#if program.videoUrl?? && program.videoUrl?length != 0>
                                                            <@spring.message "form.exist"/>
                                                        <#else>
                                                            <@spring.message "form.empty"/>
                                                        </#if>
                                                    </td>
                                                </tr>
                                            </#if>
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
                var program_id = $(this).attr('id');
                location.href = '/conferences/${conference.id}/admin/programs/' + program_id;
            } );
        });
    </script>
    </@layout.put>
</@layout.extends>
