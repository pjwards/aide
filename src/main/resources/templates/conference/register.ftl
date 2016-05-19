<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->
<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${conference.name} :: register</title>

    <!-- Custom CSS -->
    <link href="/lib/creative/css/creative.css" rel="stylesheet">

    <!-- Plugin CSS -->
    <link rel="stylesheet" href="/bower_components/animate.css/animate.min.css" type="text/css">

    <!-- Custom Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
          rel='stylesheet' type='text/css'>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    </@layout.put>

    <@layout.put block="header" type="replace">
        <@layout.extends name="layouts/header/details_2.ftl">
        </@layout.extends>
    </@layout.put>

    <@layout.put block="contents">
    <header>
        <div class="header-content">
            <div class="header-content-inner">
                <h1>${conference.name}</h1>
                <hr>
                <p>${conference.slogan}</p>
            </div>
        </div>
    </header>

    <section>
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 text-center">
                    <h2 class="section-heading">Register</h2>
                    <hr class="primary">
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <form role="form" action="" method="post" enctype="multipart/form-data">
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                                <button type="submit" class="btn btn-default">Register</button>
                                            </form>
                                        </div>
                                    </div>
                                    <!-- /.row (nested) -->
                                </div>
                                <!-- /.panel-body -->
                            </div>
                            <!-- /.panel -->
                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                </div>
            </div>
        </div>
    </section>

    </@layout.put>

    <@layout.put block="footer" type="replace">
        <@layout.extends name="layouts/footer.ftl">
        </@layout.extends>
    </@layout.put>

    <@layout.put block="script">
    <!-- Plugin JavaScript -->
    <script src="/bower_components/jquery.easing/js/jquery.easing.min.js"></script>
    <script src="/bower_components/FitText.js/jquery.fittext.js"></script>
    <script src="/bower_components/wow/dist/wow.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/lib/creative/js/creative.js"></script>

        <#if conference.assetsSet?has_content>
            <script>
                var images=[<#list conference.assetsSet as assets>'${assets.realPath}' <#sep>,</#list>];
                var next_image=0;

                $(function() {
                    $('header').css("background", "url(" + images[0] + ") no-repeat bottom center scroll");
                });

                window.setInterval(function() {
                    $('header').css("background", "url(" + images[next_image++] + ") no-repeat bottom center scroll");
                    if(next_image>=images.length)
                        next_image=0;
                }, 5000);
            </script>
        </#if>
    </@layout.put>
</@layout.extends>
