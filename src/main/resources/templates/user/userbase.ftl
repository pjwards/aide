<!DOCTYPE html>
<html lang="ko">
<head>
<@layout.block name="head">
    <meta charset="utf-8">

    <!-- Fonts -->
    <link rel="stylesheet" type="text/css" href="/bower_components/font-awesome/css/font-awesome.min.css">

    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" type="text/css" href="/bower_components/bootstrap/dist/css/bootstrap.min.css"/>

    <!-- Custom CSS -->
    <link href="/lib/sb-admin-2-1.0.8/dist/css/sb-admin-2.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</@layout.block>
</head>
<body>
<div id="wrapper">
    <@layout.block name="navi">
        <@layout.extends name="user/usersidebar.ftl">
        </@layout.extends>
    </@layout.block>
    <div id="page-wrapper">
        <@layout.block name="header">
        </@layout.block>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Contents
                    </div>
                    <div class="panel-body">
                        <@layout.block name="contents">
                        </@layout.block>
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <@layout.block name="footer">
        </@layout.block>
    </div>
    <!-- /#page-wrapper -->
</div>
<!-- /#wrapper -->

<!-- jQuery -->
<script src="/bower_components/jquery/dist/jquery.min.js"></script>

<!-- Bootstrap Core JS -->
<script src="/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="/bower_components/metisMenu/dist/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="/lib/sb-admin-2-1.0.8/dist/js/sb-admin-2.js"></script>

<@layout.block name="script">
</@layout.block>

</body>
</html>