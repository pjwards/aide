<#import "/spring.ftl" as spring/>
<#-- @ftlvariable name="conferences" type="java.util.List<com.pjwards.aide.domain.Conference>" -->
<#-- @ftlvariable name="advertisements" type="java.util.List<com.pjwards.aide.domain.Conference>" -->
<#-- @ftlvariable name="rc" type="javax.servlet.http.HttpServletRequest" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${myApp.name} :: main</title>

    <!-- Custom CSS -->
    <link href="/lib/shop-homepage/css/shop-homepage.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
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
        <@layout.extends name="layouts/header.ftl">
        </@layout.extends>
    </@layout.put>

    <@layout.put block="contents">
    <#--Language : <a href="?lang=en_US">English</a>|<a href="?lang=ko_KR">Korea</a>-->
    <#--<h3><@spring.message "hello.test"/>, world</h3>-->
    <#--Current Locale: ${rc.locale}-->

    <!-- Page Content -->
    <div class="container">

        <div class="row">

        <#if !currentUser??>
            <div class="col-md-2">
            </div>
        <#else>
            <div class="col-md-3">
                <div class="panel-group" id="accordion">
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">Participant</a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse in">
                            <div class="panel-body">
                                <#list currentUser.user.conferenceSet as conference>
                                    <a href="/conferences/${conference.id}" class="list-group-item">${conference.name}</a>
                                </#list>
                            </div>
                            <!-- /.panel-body -->
                        </div>
                        <!-- /.collapse -->
                    </div>
                    <!-- /.panel -->

                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">Speaker</a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse">
                            <div class="panel-body">
                                <div class="list-group">
                                    <#--<#list currentUser.user.programSet as program>-->
                                        <#--<a href="/conferences/${program.date.conference.id}" class="list-group-item">${program.date.conference.name}</a>-->
                                    <#--</#list>-->
                                </div>
                            </div>
                            <!-- /.panel-body -->
                        </div>
                        <!-- /.collapse -->
                    </div>
                    <!-- /.panel -->

                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">Manager</a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse">
                            <div class="panel-body">
                                <#--<#list currentUser.user.rooms as room>-->
                                    <#--<a href="/conferences/${conference.id}" class="list-group-item">${conference.name}</a>-->
                                <#--</#list>-->
                            </div>
                            <!-- /.panel-body -->
                        </div>
                        <!-- /.collapse -->
                    </div>
                    <!-- /.panel -->

                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour">Host</a>
                            </h4>
                        </div>
                        <div id="collapseFour" class="panel-collapse collapse">
                            <div class="panel-body">
                                <#list currentUser.user.conferenceHostSet as conference>
                                    <a href="/conferences/${conference.id}/admin" class="list-group-item">${conference.name}</a>
                                </#list>
                            </div>
                            <!-- /.panel-body -->
                        </div>
                        <!-- /.collapse -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.panel group -->
            </div>
        </#if>

            <div class="col-md-9">
                <div class="row carousel-holder">

                    <div class="col-md-12">
                        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <#list advertisements as advertisement>
                                    <li data-target="#carousel-example-generic" data-slide-to="${advertisement_index}" <#if advertisement_index == 0>class="active"></#if></li>
                                </#list>
                            </ol>
                            <div class="carousel-inner" style="text-align: center;">
                                <#list advertisements as advertisement>
                                    <div class="item <#if advertisement_index == 0>active</#if>">
                                        <img class="slide-image" src="<#if advertisement.assetsSet?has_content>${advertisement.assetsSet?first.realPath}<#else>/lib/grayscale/img/intro-bg.jpg</#if>" alt="">
                                        <div class="carousel-content">
                                            <div>
                                                <a href="/conferences/${advertisement.id}">
                                                    <h3>${advertisement.name}</h3>
                                                    <p>${advertisement.slogan}</p>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </#list>
                            </div>
                            <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left"></span>
                            </a>
                            <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right"></span>
                            </a>
                        </div>
                    </div>

                </div>

                <div class="row">

                    <#list conferences?reverse as conference>
                        <div class="col-sm-4 col-lg-4 col-md-4">
                            <div class="thumbnail">
                                <img src="<#if conference.assetsSet?has_content>${conference.assetsSet?first.realPath}<#else>/lib/grayscale/img/intro-bg.jpg</#if>" style="width: 320px; height: 150px;" alt="">
                                <div class="caption">
                                    <h4 class="pull-right"><#if conference.charge == "FREE">Free<#else>$${conference.price}</#if></h4>
                                    <h4><a href="conferences/${conference.id}">${conference.name}</a>
                                    </h4>
                                    <p>${conference.slogan}</p>
                                </div>
                                <div class="ratings" style="margin-bottom: 10px;">
                                    <span class="label label-${conference.status.attribute}">${conference.status.getTitle()}</span>
                                    <span class="label label-${conference.charge.color}">${conference.charge.getTitle()}</span>
                                </div>
                                <#--<div class="ratings">-->
                                    <#--<p class="pull-right">15 reviews</p>-->
                                    <#--<p>-->
                                        <#--<span class="glyphicon glyphicon-star"></span>-->
                                        <#--<span class="glyphicon glyphicon-star"></span>-->
                                        <#--<span class="glyphicon glyphicon-star"></span>-->
                                        <#--<span class="glyphicon glyphicon-star"></span>-->
                                        <#--<span class="glyphicon glyphicon-star-empty"></span>-->
                                    <#--</p>-->
                                <#--</div>-->
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
    <!-- /.container -->

    </@layout.put>

    <@layout.put block="footer" type="replace">
        <@layout.extends name="layouts/footer.ftl">
        </@layout.extends>
    </@layout.put>

    <@layout.put block="script">
    <script>
        $(function() {
            setCarouselHeight('#carousel-example-generic');

            function setCarouselHeight(id)
            {
                var slideHeight = [];
                $(id+' .item').each(function()
                {
                    // add all slide heights to an array
                    slideHeight.push($(this).height());
                });

                // find the tallest item
                max = Math.max.apply(null, slideHeight);

                // set the slide's height
                $(id+' .carousel-content').each(function()
                {
                    $(this).css('height',max+'px');
                });
            }
        });
    </script>
    </@layout.put>
</@layout.extends>