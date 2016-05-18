<#import "/spring.ftl" as spring/>
<#-- @ftlvariable name="conferences" type="java.util.List<com.pjwards.aide.domain.Conference>" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->
<#-- @ftlvariable name="form" type="com.pjwards.aide.domain.forms.ConferenceForm" -->
<#-- @ftlvariable name="rc" type="javax.servlet.http.HttpServletRequest" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${myApp.name} :: Conference :: Add</title>

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

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    </@layout.put>

    <@layout.put block="header" type="prepend">
        <@layout.extends name="layouts/header.ftl">
        </@layout.extends>
    </@layout.put>

    <@layout.put block="contents">
        <@spring.bind "form" />

    <section>
        <#if spring.status.error>
            <div class="alert alert-dismissable alert-danger text-center">
                <button type="button" class="close" data-dismiss="alert">×</button>
                <#list spring.status.errorMessages as error>
                    <p>${error}</p>

                    <#if error?contains("Name")><#global errorName=true></#if>
                    <#if error?contains("Slogan")><#global errorSlogan=true></#if>
                    <#if error?contains("Description")><#global errorDescription=true></#if>
                    <#if error?contains("Location")><#global errorLocation=true></#if>
                    <#if error?contains("Location Url")><#global errorLocationUrl=true></#if>
                    <#if error?contains("Email")><#global errorEmail=true></#if>
                    <#if error?contains("Facebook")><#global errorFacebook=true></#if>
                    <#if error?contains("Twitter")><#global errorTwitter=true></#if>
                    <#if error?contains("Github")><#global errorGithub=true></#if>
                    <#if error?contains("Google")><#global errorGoogle=true></#if>
                    <#if error?contains("price")><#global errorPrice=true></#if>
                    <#if error?contains("disqus")><#global errorDisqus=true></#if>
                </#list>
            </div>
        </#if>
    </section>

    <section>
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 text-center">
                    <h2 class="section-heading">Conference Register</h2>
                    <hr class="primary">
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-lg-12">
                            <form role="form" action="" method="post" enctype="multipart/form-data">
                                <div class="panel-group" id="accordion">
                                    <div class="panel panel-success">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">Basic Information</a>
                                            </h4>
                                        </div>
                                        <div id="collapseOne" class="panel-collapse collapse in">
                                            <div class="panel-body">
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                                <div class="form-group <#if errorName??>has-error</#if>">
                                                    <input class="form-control" name="name" placeholder="Name *" value="${form.name}">
                                                </div>

                                                <div class="form-group <#if errorSlogan??>has-error</#if>">
                                                    <input class="form-control" name="slogan" placeholder="Slogan *" value="${form.slogan}">
                                                </div>

                                                <div class="form-group <#if errorDescription??>has-error</#if>" style="text-align: left">
                                                    <textarea class="form-control" id="summernote" name="description" placeholder="Description *"> <#if form.description?? && form.description != "">${form.description}<#else>Description *</#if></textarea>
                                                </div>

                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h4 class="panel-title">
                                                            Status
                                                        </h4>
                                                    </div>
                                                    <div class="panel-body">
                                                        <div class="form-group">
                                                            <#list form.statusList as list>
                                                                <label class="radio-inline">
                                                                    <input type="radio" name="status" id="status_${list_index}" value="${list}" <#if form.status == list >checked</#if>>
                                                                ${list.title}
                                                                </label>
                                                            </#list>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h4 class="panel-title">
                                                            Charge
                                                        </h4>
                                                    </div>
                                                    <div class="panel-body">
                                                        <div class="form-group">
                                                            <#list form.chargeList as list>
                                                                <label class="radio-inline">
                                                                    <input type="radio" name="charge" id="charge_${list_index}" value="${list}" <#if form.charge == list >checked</#if>>
                                                                ${list.title}
                                                                </label>
                                                            </#list>
                                                        </div>

                                                        <div id="price_form" class="form-group input-group <#if errorPrice??>has-error</#if>" style="display: <#if form.charge != "CHARGED" >none;</#if>">
                                                            <span class="input-group-addon">$</span>
                                                            <input type="text" class="form-control" name="price" placeholder="Price *" value="${form.price}">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- /.panel-body -->
                                        </div>
                                        <!-- /.collapse -->
                                    </div>
                                    <!-- /.panel -->

                                    <div class="panel panel-warning">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">Location Information</a>
                                            </h4>
                                        </div>
                                        <div id="collapseTwo" class="panel-collapse collapse">
                                            <div class="panel-body">
                                                <div class="form-group <#if errorLocation??>has-error</#if>">
                                                    <input class="form-control" name="location" placeholder="Location" value="${form.location}">
                                                </div>

                                                <div class="form-group <#if errorLocationUrl??>has-error</#if>">
                                                    <input class="form-control" name="locationUrl" placeholder="Location Url" value="${form.locationUrl}">
                                                </div>

                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h4 class="panel-title">
                                                            Google Maps
                                                        </h4>
                                                    </div>
                                                    <div class="panel-body">
                                                        <div class="col-lg-6">
                                                            <div class="form-group input-group">
                                                                <span class="input-group-addon">Lat</span>
                                                                <input type="text" class="form-control" name="lat" placeholder="Lat" value="${form.lat}">
                                                            </div>
                                                        </div>

                                                        <div class="col-lg-6">
                                                            <div class="form-group input-group">
                                                                <span class="input-group-addon">Lan</span>
                                                                <input type="text" class="form-control" name="lan" placeholder="Lan" value="${form.lan}">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- /.panel-body -->
                                        </div>
                                        <!-- /.collapse -->
                                    </div>
                                    <!-- /.panel -->

                                    <div class="panel panel-warning">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">Contact Information</a>
                                            </h4>
                                        </div>
                                        <div id="collapseThree" class="panel-collapse collapse">
                                            <div class="panel-body">
                                                <div class="form-group input-group <#if errorEmail??>has-error</#if>">
                                                    <span class="input-group-addon"><i class="fa fa-envelope" style="width: 20px"></i></span>
                                                    <input type="text" class="form-control" name="email" placeholder="Email" value="${form.email}">
                                                </div>

                                                <div class="form-group input-group <#if errorFacebook??>has-error</#if>">
                                                    <span class="input-group-addon"><i class="fa fa-facebook" style="width: 20px"></i></span>
                                                    <input type="text" class="form-control" name="facebook" placeholder="Facebook" value="${form.facebook}">
                                                </div>

                                                <div class="form-group input-group <#if errorTwitter??>has-error</#if>">
                                                    <span class="input-group-addon"><i class="fa fa-twitter" style="width: 20px"></i></span>
                                                    <input type="text" class="form-control" name="twitter" placeholder="Twitter" value="${form.twitter}">
                                                </div>

                                                <div class="form-group input-group <#if errorGithub??>has-error</#if>">
                                                    <span class="input-group-addon"><i class="fa fa-github" style="width: 20px"></i></span>
                                                    <input type="text" class="form-control" name="github" placeholder="Github" value="${form.github}">
                                                </div>

                                                <div class="form-group input-group <#if errorGoogle??>has-error</#if>">
                                                    <span class="input-group-addon"><i class="fa fa-google-plus" style="width: 20px"></i></span>
                                                    <input type="text" class="form-control" name="googlePlus" placeholder="Google Plus" value="${form.googlePlus}">
                                                </div>
                                            </div>
                                            <!-- /.panel-body -->
                                        </div>
                                        <!-- /.collapse -->
                                    </div>
                                    <!-- /.panel -->

                                    <div class="panel panel-warning">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour">Images Information</a>
                                            </h4>
                                        </div>
                                        <div id="collapseFour" class="panel-collapse collapse">
                                            <div class="panel-body">
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h4 class="panel-title">
                                                            Images
                                                        </h4>
                                                    </div>
                                                    <div class="panel-body">
                                                        <div class="form-group">
                                                            <select id="assets" disabled multiple class="form-control">
                                                            </select>
                                                        </div>
                                                        <div class="form-group">
                                                            <input type="file" name="assets" multiple>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- /.panel-body -->
                                        </div>
                                        <!-- /.collapse -->
                                    </div>
                                    <!-- /.panel -->

                                    <div class="panel panel-warning">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseFive">Disqus Information</a>
                                            </h4>
                                        </div>
                                        <div id="collapseFive" class="panel-collapse collapse">
                                            <div class="panel-body">
                                                <div class="form-group <#if errorDisqus??>has-error</#if>">
                                                    <input class="form-control" name="disqus" placeholder="Disqus Site" value="${form.disqus}">
                                                </div>
                                            </div>
                                            <!-- /.panel-body -->
                                        </div>
                                        <!-- /.collapse -->
                                    </div>
                                    <!-- /.panel -->
                                </div>
                                <!-- /.panel group -->
                                <a class="btn btn-danger" href="/">Cancel</a>
                                <button type="submit" class="btn btn-default">Register</button>
                            </form>
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
    <script src="/js/summernote.js"></script>

    <script>
        $(function (){
            $("input[name=charge]").change(function() {
                var radioValue = $(this).val();
                if (radioValue == "CHARGED") {
                    $("#price_form").show();
                } else {
                    $("#price_form").hide();
                }
            });

            $('input[type="file"]').change(function(){
                $("#assets").empty();
                for(var i=0; this.files.length; i++) {
                    var file = this.files[i];
                    $("#assets").append('<option>'+file.name+'</option>');
                }
            });
        });
    </script>
    </@layout.put>
</@layout.extends>