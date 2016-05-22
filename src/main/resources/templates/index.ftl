<#import "/spring.ftl" as spring/>
<#-- @ftlvariable name="conferences" type="org.springframework.data.domain.Page<com.pjwards.aide.domain.Conference>" -->
<#-- @ftlvariable name="advertisements" type="java.util.List<com.pjwards.aide.domain.Conference>" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->
<#-- @ftlvariable name="k" type="java.lang.String" -->

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
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne"><@spring.message "content.index.participant"/></a>
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
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"><@spring.message "content.index.speaker"/></a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse">
                            <div class="panel-body">
                                <div class="list-group">
                                    <#list currentUser.user.programSet as program>
                                        <a href="/conferences/${program.date.conference.id}/admin/programs/${program.id}" class="list-group-item">${program.title}</a>
                                    </#list>
                                    <#list currentUser.user.sessionSet as session>
                                        <a href="/conferences/${session.program.date.conference.id}/admin/sessions/${session.id}" class="list-group-item">${session.title}</a>
                                    </#list>
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
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree"><@spring.message "content.index.manager"/></a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse">
                            <div class="panel-body">
                                <#list currentUser.user.roomSet as room>
                                    <a href="/conferences/${room.conference.id}/rooms/admin/${room.id}" class="list-group-item">${room.name}</a>
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
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour"><@spring.message "content.index.host"/></a>
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
                <form action="" id="search_form" method="get" role="form">
                    <div class="input-group custom-search-form" style="margin-bottom: 20px;">
                            <input type="text" id="search_input" name="k" class="form-control" placeholder="<@spring.message "content.index.search"/>..." value="<#if k??>&k=${k}</#if>">
                            <span class="input-group-btn">
                                <button id="search_btn" class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                    </div>
                </form>

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

                    <#list conferences.content as conference>
                        <div class="col-sm-4 col-lg-4 col-md-4">
                            <div class="thumbnail">
                                <img src="<#if conference.assetsSet?has_content>${conference.assetsSet?first.realPath}<#else>/lib/grayscale/img/intro-bg.jpg</#if>" style="width: 320px; height: 150px;" alt="">
                                <div class="caption">
                                    <h4 class="pull-right"><#if conference.charge == "FREE"><@spring.message "content.index.charge.free"/><#else><@spring.message "content.index.price"/>${conference.price}</#if></h4>
                                    <h4><a href="conferences/${conference.id}">${conference.name}</a>
                                    </h4>
                                    <p>${conference.slogan}</p>
                                </div>
                                <div class="ratings" style="margin-bottom: 10px;">
                                    <span class="label label-${conference.status.attribute}" style="margin-right: 5px;">
                                    <#if conference.status == "OPEN">
                                        <@spring.message "content.index.status.label.open"/>
                                    <#elseif conference.status == "CLOSED">
                                        <@spring.message "content.index.status.label.closed"/>
                                    <#else>
                                        <@spring.message "content.index.status.label.progress"/>
                                    </#if>
                                    </span>
                                    <span class="label label-${conference.charge.color}">
                                    <#if conference.charge == "FREE">
                                        <@spring.message "content.index.charge.label.free"/>
                                    <#else>
                                        <@spring.message "content.index.charge.label.charged"/>
                                    </#if>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
                <div class="row text-center">
                    <nav>
                        <ul class="pagination">
                            <li class="page-item <#if conferences.first>disabled</#if>">
                                <a class="page-link" href="?page=${0}<#if k??>&k=${k}</#if>" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                    <span class="sr-only">Previous</span>
                                </a>
                            </li>
                            <#list 0..conferences.totalPages-1 as page>
                                <li class="page-item <#if page == conferences.number>active</#if>"><a class="page-link" href="?page=${page}<#if k??>&k=${k}</#if>">${page + 1} <#if page == conferences.number><span class="sr-only">(current)</span></#if></a></li>
                            </#list>
                            <li class="page-item <#if conferences.last>disabled</#if>">
                                <a class="page-link" href="?page=${conferences.totalPages-1}<#if k??>&k=${k}</#if>" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
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

            $("#search_input").keypress(function (event) {
                var key_code = event.keyCode || window.event.keyCode;
                if (key_code == 13) document.getElementById('search_form').submit();
            });

            $("#search_btn").on("click", function (event) {
                document.getElementById('search_form').submit();
            });
        });
    </script>
    </@layout.put>
</@layout.extends>