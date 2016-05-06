<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${conference.name} :: main</title>

    <!-- Custom CSS -->
    <link href="/lib/grayscale/css/grayscale.css" rel="stylesheet">

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
        <@layout.extends name="conference/layouts/header/details_1.ftl">
        </@layout.extends>
    </@layout.put>

    <@layout.put block="contents">
    <!-- Intro Header -->
    <header class="intro">
        <div class="intro-body">
            <div class="container">
                <div class="row">
                    <div class="col-md-8 col-md-offset-2">
                        <h1 class="brand-heading">${conference.name}</h1>
                        <p class="intro-text">${conference.slogan}</p>

                        <#if conference.location??>
                        <p>
                            <#if conference.location??>
                                ${conference.location}
                            </#if>

                            <#if conference.locationUrl??>
                                <#--&nbsp;-->
                                <a href="${conference.locationUrl}" target="_blank">
                                    <i class="fa fa-street-view" style="font-size: 30px;" aria-hidden="true" alt="location"></i>
                                </a>
                            </#if>
                        </p>
                        </#if>

                        <a href="#about" class="btn btn-circle page-scroll">
                            <i class="fa fa-angle-double-down animated"></i>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <!-- About Section -->
    <section id="about" class="container content-section text-center">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2">
            ${conference.description}
            </div>
        </div>

        <br/>
        <br/>

        <div class="row">
            <div class="col-lg-8 col-lg-offset-2">
                <a href="${conference.id}/schedule" class="btn btn-default btn-lg">
                    SCHEDULE
                </a>
            </div>
        </div>
    </section>

    <#if conference.contacts?has_content>
    <!-- Contact Section -->
    <section id="contact" class="container content-section text-center">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2">
                <h2>Contact</h2>

                <ul class="list-inline banner-social-buttons">
                    <#list conference.contacts as contact>
                        <li>
                            <a href="<#if contact.type == "EMAIL">mailto:</#if>${contact.url}"
                               class="btn btn-circle"><i
                                    class="fa fa-${contact.type.attribute}"></i></a>
                        </li>
                    </#list>
                </ul>

            </div>
        </div>
    </section>
    </#if>


    <#if conference.lan!=0.0 || conference.lat!=0.0>
        <!-- Map Section -->
        <div id="map"></div>
    </#if>


    </@layout.put>

    <@layout.put block="footer" type="replace">
        <@layout.extends name="layouts/footer.ftl">
        </@layout.extends>
    </@layout.put>

    <@layout.put block="script">
    <!-- Plugin JavaScript -->
    <script src="/bower_components/jquery.easing/js/jquery.easing.min.js"></script>
    <script src="/bower_components/sharrre/jquery.sharrre.min.js"></script>

    <!-- Google Maps API Key - Use your own API key to enable the map feature. More information on the Google Maps API can be found at https://developers.google.com/maps/ -->
    <script type="text/javascript"
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD4PT3dSdoItwtIzCSe6E0FepFqYJM8U7Q"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/lib/grayscale/js/grayscale.js"></script>
    <script src="/lib/grayscale/js/map.js"></script>

    <#if conference.lan!=0.0 || conference.lat!=0.0>
        <script>
            var locationUrl = "<#if conference.locationUrl?? >${conference.locationUrl}<#else>https://www.google.co.kr/maps/</#if>";
            // When the window has finished loading create our google map below
            google.maps.event.addDomListener(window, 'load', init.bind(null, ${conference.lat} , ${conference.lan} , locationUrl));
            google.maps.event.addDomListener(window, 'resize', function () {
                map.setCenter(new google.maps.LatLng( ${conference.lat}, ${conference.lan}));
            });
        </script>
    </#if>
    </@layout.put>
</@layout.extends>