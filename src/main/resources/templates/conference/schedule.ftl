<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${myApp.name} :: schedule</title>

    <!-- Custom CSS -->
    <link href="/lib/creative/css/creative.css" rel="stylesheet">
    <#--<link href="/css/conference/deview2015a6d0.css" rel="stylesheet">-->
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
    <!-- Navigation -->
    <nav id="mainNav" class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand page-scroll" href="#page-top">AIDE Project</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a class="page-scroll" href="#about">About</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#services">Services</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#portfolio">Portfolio</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#contact">Contact</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
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
                    <h2 class="section-heading">Schedule</h2>
                    <hr class="primary">
                </div>
            </div>
        </div>

        <#list conference.programDates as date>
        <div class="lay_w section3">
            <div class="ttlb_area">
                <h3 class="scd_tit">${date.name}<span class="ttlb_date">${date.formattedDay}</span></h3>

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
                                                        <img src="" width="50" height="50" alt="">
                                                    </span>

                                                        <dl>
                                                    <dt class="name">${speaker.name}</dt>
                                                    <#if speaker.company??>
                                                        <dd class="blong_to">${speaker.company}</dd>
                                                    </#if>
                                                    <dd class="sns_links">
                                                        <a href="mailto:${speaker.email}" class="social" target="_blank"><i class="xi-envelope"><span class="blind">이메일</span></i></a>
                                                    </dd>
                                                <#--<dd class="etc_info">${speaker.profile}</dd>-->
                                                <#-- TODO -->
                                                </dl>
                                                </div>
                                                </#list>

                                                <#--<div class="data">-->
                                                <#--<i class="xi-devices">-->
                                                <#--<span class="blind">참가등록 아이콘</span>-->
                                                <#--</i>-->
                                                <#--</div>-->
                                                <#-- TODO -->
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
                                                    <img src="" width="50" height="50" alt="">
                                                </span>

                                                    <dl>
                                                <dt class="name">${speaker.name}</dt>
                                                <#if speaker.company??>
                                                    <dd class="blong_to">${speaker.company}</dd>
                                                </#if>
                                                <dd class="sns_links">
                                                    <a href="mailto:${speaker.email}" class="social" target="_blank"><i class="xi-envelope"><span class="blind">이메일</span></i></a>
                                                </dd>
                                            <#--<dd class="etc_info">${speaker.profile}</dd>-->
                                            <#-- TODO -->
                                            </dl>
                                            </div>
                                            </#list>

                                            <#--<div class="data">-->
                                            <#--<i class="xi-devices">-->
                                            <#--<span class="blind">참가등록 아이콘</span>-->
                                            <#--</i>-->
                                            <#--</div>-->
                                            <#-- TODO -->
                                        </li>
                                        <#else>
                                        <li class="lecture_ct ${program.programType.attribute}">
                                            <div class="lecture_info">
                                                <p class="tit">
                                                    <span>${program.title}</span>
                                                </p>
                                            </div>

                                            <#--<div class="data">-->
                                            <#--<i class="xi-devices">-->
                                            <#--<span class="blind">참가등록 아이콘</span>-->
                                            <#--</i>-->
                                            <#--</div>-->
                                            <#-- TODO -->
                                        </li>
                                        </#if>
                                    </ul>
                                </dd>
                            </dl>
                        </li>
                        </#list>

                        <li class="time_lst">
                            <dl>
                                <dt class="time">09:20 ~ 09:40</dt>
                                <dd class="lecture_lst">
                                    <ul>
                                        <li class="lecture_ct keynote" id="keynote">
                                            <div class="lecture_info"><p class="tit"><span>키노트 · </span><span
                                                    id="_keynote_name" class="name">송창현</span>
                                                <span id="_keynote_belong" class="blong_to">NAVER CTO</span></p>
                                            </div>
                                            <div class="data"><a
                                                    href="http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=161A4D6E7C3D2FC586BB5508428F425CE66C&amp;outKey=V12223fad06938b8f00b25b948eb16f4259538cdf26e9552b30e45b948eb16f425953&amp;controlBarMovable=true&amp;jsCallable=true&amp;skinName=tvcast_white"
                                                    target="_blank"><i class="xi-video-camera"><span
                                                    class="blind">동영상</span></i></a>
                                            </div>
                                        </li>
                                    </ul>
                                </dd>
                            </dl>
                        </li>

                        <li class="time_lst">
                            <dl>
                                <dt class="time">10:00 ~ 10:50</dt>
                                <dd class="lecture_lst">
                                    <ul>
                                        <li class="lecture_ct" data-categorycode="21,28" id="115"><a
                                                href="#session/115" class="lecture_links"><span class="blind">상세요약 열기</span></a>
                                            <div class="lecture_info"><p class="tit">
                                                <span>네이버 효과툰은 어떻게 만들어졌나?</span><a href="#"
                                                                                   class="track_check off"
                                                                                   id="_check"><i
                                                    class="xi-check-circleout"><span
                                                    class="blind">트랙 선택 하기</span></i></a></p>
                                                <p></p></div>
                                            <div class="txt_area"><p>"고고고", "악의는없다", "소름"에 적용된 새로운 네이버 효과툰의
                                                아키텍쳐와
                                                동작원리에 대해 소개합니다. 효과툰 장르의 정의, 스크롤에 따른 모션, 비개발자인 작가가 쓰는 저작도구 등에 대한
                                                고민과
                                                그 결과물의 속사정을 모두 공개합니다. 프로젝트의 A-Z를 고민하는 하이브리드 개발자에게 도움이 되길 바랍니다.
                                                <br>
                                                본 세션에서는 스크롤 툰에서의 효과에 대해 효과의 종류와 구현 방법에 대해 고민한 내용을 공유하고, 효과툰 뷰어와
                                                페이지,
                                                레이어 구조에 대해 설명합니다. </p></div>
                                            <div class="spk_info"><span class="pic"><span class="msk"><img
                                                    src="/img/spk_msk.png" width="50" height="50" alt=""></span><img
                                                    src="http://deview.kr/data/deview/acnr/150.jpg?20160226152249"
                                                    width="50" height="50" alt="김효 강연자 사진"></span>
                                                <dl>
                                <dt class="name">김효</dt>
                                <dd class="blong_to">NAVER LABS</dd>
                                <dd class="sns_links"><a href="mailto:h.kim@navercorp.com" class="social"
                                                         target="_blank"><i class="xi-envelope"><span
                                        class="blind">이메일</span></i></a>
                                </dd>
                                <dd class="etc_info">클러스터 파일시스템, NoSQL, 웹브라우저 엔진 개발자였던 네이버 웹기술 프로덕트 매니저</dd>
                            </dl>
                </div>
                <div class="spk_info"><span class="pic"><span class="msk"><img src="/img/spk_msk.png" width="50"
                                                                               height="50" alt=""></span><img
                        src="http://deview.kr/data/deview/acnr/160.jpg" width="50" height="50"
                        alt="이현철 강연자 사진"></span>
                    <dl>
                        <dt class="name">이현철</dt>
                        <dd class="blong_to">NAVER</dd>
                        <dd class="sns_links"><a href="mailto:andrew.lee@navercorp.com" class="social"
                                                 target="_blank"><i class="xi-envelope"><span
                                class="blind">이메일</span></i></a></dd>
                        <dd class="etc_info">컴퓨터 그래픽스&amp;음악, 웹브라우저 엔진 개발을 했고, 현재는 네이버 웹UI 개발을 하고 있습니다</dd>
                    </dl>
                </div>
                <ul class="filter_ico_lst"></ul>
                <div class="data"><a href="http://www.slideshare.net/deview/111-52720751 " target="_blank"><i
                        class="xi-slideshare"><span class="blind">프리젠테이션</span></i></a><a
                        href="http://serviceapi.rmcnmv.naver.com/flash/outKeyPlayer.nhn?vid=35B8A72DD833B489B8A0D3B32D2F617EC61B&amp;outKey=V1299d502e9b3c47bbc4fcd6e857318eed4cffd85be8521631b9acd6e857318eed4cf&amp;controlBarMovable=true&amp;jsCallable=true&amp;skinName=tvcast_white"
                        target="_blank"><i class="xi-video-camera"><span class="blind">동영상</span></i></a></div>
                </li>


                </ul>
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
    </@layout.put>
</@layout.extends>
