<#import "/spring.ftl" as spring/>

<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${conference.name} :: schedule</title>

    <!-- Custom CSS -->
    <link href="/lib/creative/css/creative.css" rel="stylesheet">
    <link href="/css/conference/schedule.css" rel="stylesheet">

    <!-- Plugin CSS -->
    <link rel="stylesheet" href="/bower_components/animate.css/animate.min.css" type="text/css">

    <!-- Custom Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
          rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="http://cdn.jsdelivr.net/xeicon/1.0.4/xeicon.min.css">

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
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading"><@spring.message "conference.schedule.schedule"/></h2>
                    <hr class="primary">
                    <#if !conference.programDates?has_content>
                        <!-- /.row -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-body">
                                        <h3><@spring.message "conference.schedule.preparation"/></h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </#if>
                </div>
            </div>
        </div>

        <#list conference.programDates as date>
        <div class="lay_w section3">
            <div class="ttlb_area">
                <h3 class="scd_tit">${date.name}<span class="ttlb_date">${date.formattedScheduleDay}</span></h3>

                <div class="ttlb_bx end">
                    <ul class="ttlb_lst_area">
                        <#list date.programs as program>
                        <li class="time_lst">
                            <dl>
                                <dt class="time">${program.begin} ~ ${program.end}</dt>
                                <dd class="lecture_lst">
                                    <ul>
                                        <#if program.programType == "SESSION">
                                            <#list program.sessions as session>
                                            <li class="lecture_ct ${program.programType.attribute}">
                                                <div class="lecture_info">
                                                    <p class="tit">
                                                        <span>${session.title}</span>
                                                    </p>
                                                </div>
                                                <div class="txt_area">
                                                    <p>${session.description}</p>
                                                </div>
                                                <#list session.speakerSet as speaker>
                                                    <div class="spk_info">
                                                    <span class="pic">
                                                        <span class="msk">
                                                            <img src="/data/img/conference/spk_msk.png" width="50" height="50" alt="">
                                                        </span>
                                                        <img src="<#if speaker.assets??>${speaker.assets.realPath}</#if>" width="50" height="50" alt="speaker picture"
                                                             onerror="if (this.src != '/data/img/conference/no_picture.jpg') this.src = '/data/img/conference/no_picture.jpg'">
                                                    </span>

                                                        <dl>
                                                    <dt class="name">${speaker.name}</dt>
                                                    <#if speaker.company??>
                                                        <dd class="blong_to">${speaker.company}</dd>
                                                    </#if>
                                                    <dd class="sns_links">
                                                        <a href="mailto:${speaker.email}" class="social" target="_blank"><i class="xi-envelope"><span class="blind">이메일</span></i></a>
                                                    </dd>
                                                    <dd class="etc_info">${speaker.description}</dd>
                                                </dl>
                                                </div>
                                                </#list>

                                                <div class="data">
                                                    <a href="/sessions/${session.id}" target="_blank">
                                                        <i class="fa fa-comments-o" aria-hidden="true"><span class="blind">상세페이지</span></i>
                                                    </a>

                                                    <#if session.slideUrl?? && session.slideUrl != "">
                                                        <a href="${session.slideUrl}" target="_blank">
                                                            <i class="xi-slideshare"><span class="blind">프리젠테이션</span></i>
                                                        </a>
                                                    </#if>

                                                    <#if session.videoUrl?? && session.videoUrl != "">
                                                        <a href="${session.videoUrl}" target="_blank">
                                                            <i class="xi-video-camera"><span class="blind">동영상</span></i>
                                                        </a>
                                                    </#if>
                                                </div>
                                            </li>
                                            </#list>
                                        <#elseif program.programType == "KEYNOTE">
                                        <li class="lecture_ct ${program.programType.attribute}">
                                            <div class="lecture_info">
                                                <p class="tit">
                                                    <span>${program.title}</span>
                                                </p>
                                            </div>
                                            <div class="txt_area">
                                                <p>${program.description}</p>
                                            </div>
                                            <#list program.speakerSet as speaker>
                                                <div class="spk_info">
                                                <span class="pic">
                                                    <span class="msk">
                                                        <img src="/data/img/conference/spk_msk.png" width="50" height="50" alt="">
                                                    </span>
                                                    <img src="<#if speaker.assets??>${speaker.assets.realPath}</#if>" width="50" height="50" alt="speaker picture"
                                                         onerror="if (this.src != '/data/img/conference/no_picture.jpg') this.src = '/data/img/conference/no_picture.jpg'">
                                                </span>

                                                    <dl>
                                                <dt class="name">${speaker.name}</dt>
                                                <#if speaker.company??>
                                                    <dd class="blong_to">${speaker.company}</dd>
                                                </#if>
                                                <dd class="sns_links">
                                                    <a href="mailto:${speaker.email}" class="social" target="_blank"><i class="xi-envelope"><span class="blind">이메일</span></i></a>
                                                </dd>
                                            <dd class="etc_info">${speaker.description}</dd>
                                            <#-- TODO -->
                                            </dl>
                                            </div>
                                            </#list>

                                            <div class="data">
                                                <a href="/programs/${program.id}" target="_blank">
                                                    <i class="fa fa-comments-o" aria-hidden="true"><span class="blind">상세페이지</span></i>
                                                </a>

                                                <#if program.slideUrl?? && program.slideUrl != "">
                                                    <a href="${program.slideUrl}" target="_blank">
                                                        <i class="xi-slideshare"><span class="blind">프리젠테이션</span></i>
                                                    </a>
                                                </#if>

                                                <#if program.videoUrl?? && program.videoUrl != "">
                                                    <a href="${program.videoUrl}" target="_blank">
                                                        <i class="xi-video-camera"><span class="blind">동영상</span></i>
                                                    </a>
                                                </#if>
                                            </div>
                                        </li>
                                        <#else>
                                        <li class="lecture_ct ${program.programType.attribute}">
                                            <div class="lecture_info">
                                                <p class="tit">
                                                    <span>${program.title}</span>
                                                </p>
                                            </div>

                                            <#if program.programType = "REGISTER">
                                                <div class="data"><i class="xi-devices"><span class="blind">참가등록 아이콘</span></i></div>
                                            <#elseif program.programType = "LUNCH">
                                                <div class="data"><i class="xi-restaurants"><span class="blind">점심시간 아이콘</span></i></div>
                                            <#elseif program.programType = "BOF">
                                                <div class="data"><i class="xi-community"><span class="blind">BOF 아이콘</span></i></div>
                                            </#if>
                                        </li>
                                        </#if>
                                    </ul>
                                </dd>
                            </dl>
                        </li>
                        </#list>
                    </ul>
                </div>
            </div>
        </div>
        </#list>

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

    <script>
        $(function () {
            $('.lecture_ct').bind('click', function (event) {
                if (!$(this).hasClass('data_on')) {
                    $(this).toggleClass('sum_up');
                }
            });
        });
    </script>

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
