<!DOCTYPE html>
<html lang="ko">

<head>
<@layout.block name="head">
    <meta charset="utf-8">

    <!-- Register CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" type="text/css" href="/bower_components/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/bower_components/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/lib/register/css/form-elements.css" />
    <link rel="stylesheet" type="text/css" href="/lib/register/css/style.css" />

    <!-- Custom CSS -->

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

</@layout.block>
</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <@layout.block name="header">
                    </@layout.block>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <@layout.block name="content-head-left">
                            </@layout.block>
                        </div>
                        <div class="form-top-right">
                            <@layout.block name="content-head-right">
                            </@layout.block>
                        </div>
                    </div>

                    <div class="form-bottom">
                        <@layout.block name="contents">
                        </@layout.block>
                        <@layout.block name="content-related">
                        </@layout.block>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 social-login">
                    <@layout.block name="footer">
                    </@layout.block>
                </div>
            </div>
        </div>
    </div>

</div>

<!-- Javascript -->
<script src="/bower_components/jquery/dist/jquery.min.js"></script>
<script src="/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/bower_components/jquery-backstretch/jquery.backstretch.min.js"></script>
<script src="/lib/register/js/scripts.js"></script>

<!--[if lt IE 10]>
        <script src="/lib/register/js/placeholder.js"></script>
    <![endif]-->

<script>
    $(function () {
        /*
         Fullscreen background
         */
        $.backstretch("/lib/register/img/1.jpg");
    });
</script>

<@layout.put block="footer">
</@layout.put>

<@layout.block name="script">
</@layout.block>
</body>
</html>