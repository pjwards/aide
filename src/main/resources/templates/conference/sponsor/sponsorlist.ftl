<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->
<#-- @ftlvariable name="sponsorList" type="java.util.List<net.study.domain.Sponsor>" -->
<#-- @ftlvariable name="hasSponsor" type="java.lang.Boolean" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${conference.name} :: Sponosr list</title>

    <!-- MetisMenu CSS -->
    <link href="/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="/lib/sb-admin/dist/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/lib/sb-admin/dist/css/sb-admin-2.css" rel="stylesheet">

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
                                        <th class="col-xs-1 col-sm-1 col-md-1 col-lg-1"></th>
                                        <th>Name</th>
                                        <th>Slug</th>
                                        <th>Rank</th>
                                        <th>Url</th>
                                        <th>Delete</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                        <#if hasSponsor == false>
                                        <tr>
                                            <td colspan="5">
                                                Can not found sponsors.
                                            </td>
                                        </tr>
                                        <#else>
                                            <#list sponsorList as list>
                                            <tr>
                                                <td><img id="avatar" src="<#if list.assets??>${list.assets.realPath}<#else>/basic/img/user.png</#if>" alt="picture"></td>
                                                <td><a href="/conferences/${conference.id}/admin/sponsor/${list.id}/update" id="update">${list.name}</a></td>
                                                <td>${list.slug}</td>
                                                <td>${list.rank}</td>
                                                <td>${list.url}</td>
                                                <td><button type="button" class="btn btn-danger" onclick="sendDelete(${list.id})">Delete</button></td>
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

    <script type="text/javascript">
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
    </@layout.put>


</@layout.extends>
