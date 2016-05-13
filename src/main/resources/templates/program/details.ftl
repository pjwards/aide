<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="program" type="com.pjwards.aide.domain.Program" -->
<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${conference.name} :: ${program.title} :: details</title>

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
                <h1>
                    ${program.title}
                    <#if program.slideUrl??>
                        <a href="${program.slideUrl}" target="_blank">
                            <i class="fa fa-slideshare"></i>
                        </a>
                    </#if>
                    <#if program.videoUrl??>
                        <a href="${program.videoUrl}" target="_blank">
                            <i class="fa fa-video-camera"></i>
                        </a>
                    </#if>
                </h1>
                <hr>
                <p>
                    ${program.begin} ~ ${program.end}
                    <br>
                    <#list program.speakerSet as speaker>
                        <a data-toggle="modal" data-target="#speaker_${speaker.id}">${speaker.name}</a><#sep>,
                    </#list>
                </p>
            </div>
        </div>
    </header>

    <section>
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 text-center">
                    <h2 class="section-heading">Contents</h2>
                    <hr class="primary">
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    ${program.description}
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

            <br>
            <br>

            <#if program.slideEmbed??>
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2 text-center">
                        <h2 class="section-heading">Slide</h2>
                        <hr class="primary">
                        <!-- /.row -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-body">
                                        ${program.slideEmbed}
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

                <br>
                <br>
            </#if>

            <#if program.videoEmbed??>
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2 text-center">
                        <h2 class="section-heading">Video</h2>
                        <hr class="primary">
                        <!-- /.row -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-body">
                                    ${program.videoEmbed}
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

                <br>
                <br>
            </#if>

            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 text-center">
                    <h2 class="section-heading">
                        Comments
                    </h2>
                    <hr class="primary">
                    <!-- /.row -->
                    <div class="row">
                        <div id="disqus_thread"></div>
                    </div>
                    <!-- /.row -->
                </div>
            </div>
        </div>
    </section>

        <#list program.speakerSet as speaker>
            <div id="speaker_${speaker.id}" class="modal fade" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">
                                ${speaker.name}
                                <#if speaker.company??>
                                    <span class="text-muted">${speaker.company}</span>
                                </#if>
                                <a href="mailto:${speaker.email}" class="text-muted" target="_blank"><i class="fa fa-envelope-o"></i></a>
                            </h4>
                        </div>
                        <div class="modal-body">
                            <p>${speaker.description}</p>
                        </div>
                    </div>

                </div>
            </div>
        </#list>
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

    <script>
        /**
         * RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
         * LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables
         */
        /*
        var disqus_config = function () {
        this.page.url = PAGE_URL; // Replace PAGE_URL with your page's canonical URL variable
        this.page.identifier = PAGE_IDENTIFIER; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
        };
        */
        (function() { // DON'T EDIT BELOW THIS LINE
            var d = document, s = d.createElement('script');

            s.src = '//<#if conference.disqus?? && conference.disqus != "">${conference.disqus}<#else>pjwards-aide</#if>.disqus.com/embed.js';

            s.setAttribute('data-timestamp', +new Date());
            (d.head || d.body).appendChild(s);
        })();
    </script>
    <noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript" rel="nofollow">comments powered by Disqus.</a></noscript>
    </@layout.put>
</@layout.extends>
