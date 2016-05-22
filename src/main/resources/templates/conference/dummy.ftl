<#import "/spring.ftl" as spring/>
<#-- @ftlvariable name="conferences" type="java.util.List<com.pjwards.aide.domain.Conference>" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->
<#-- @ftlvariable name="rc" type="javax.servlet.http.HttpServletRequest" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="form2" type="com.pjwards.aide.domain.forms.SignUpForm" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->

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
        <@spring.bind "form2" />

    <section>
        <#if spring.status.error>
            <div class="alert alert-dismissable alert-danger text-center">
                <button type="button" class="close" data-dismiss="alert">×</button>
                <#list spring.status.errorMessages as error>
                    <p>${error}</p>

                    <#if error?contains("email") || error?contains("Email")><#global errorEmail=true></#if>
                    <#if error?contains("Name")><#global errorName=true></#if>
                    <#if error?contains("Passwords") || error?contains("Password")><#global errorPassword=true></#if>
                    <#if error?contains("Passwords") || error?contains("PasswordRepeated")><#global errorPasswordRepeated=true></#if>
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
                            <form role="form" method="post" action="" class="login-form">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                <div class="form-group <#if errorEmail??>has-error</#if>"">
                                    <label>Email</label>
                                    <input type="email" placeholder="Email address"
                                           class="form-email form-control"
                                           id="email" name="email" required>
                                </div>

                                <div class="form-group <#if errorName??>has-error</#if>">
                                    <label>Name</label>
                                    <input type="text" placeholder="Name"
                                           class="form-username form-control"
                                           id="name" name="name" required>
                                </div>

                                <div class="form-group <#if errorPassword??>has-error</#if>">
                                    <label>Password</label>
                                    <input type="password" placeholder="password"
                                           class="form-password form-control"
                                           id="password" name="password" required>
                                </div>
                                <div class="form-group <#if errorPasswordRepeated??>has-error</#if>">
                                    <label>Password confirmation</label>
                                    <input type="password" placeholder="password again"
                                           class="form-password form-control"
                                           id="passwordRepeated" name="passwordRepeated" required>
                                </div>

                                <button type="submit" class="btn">Sign up</button>
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
    </@layout.put>
</@layout.extends>