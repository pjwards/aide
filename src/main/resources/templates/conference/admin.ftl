<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->
<#-- @ftlvariable name="currentUser" type="com.pjwards.aide.domain.CurrentUser" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${conference.name} :: Admin</title>

    <!-- Bootstrap 3 Datepicker CSS -->
    <link rel="stylesheet" href="/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css" />

    <!-- Timeline CSS -->
    <link href="/lib/sb-admin/dist/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/lib/sb-admin/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Flip Clock CSS -->
    <link href="/bower_components/FlipClock/compiled/flipclock.css" rel="stylesheet">
    <link href="/css/conference/clock.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="/bower_components/morris.js/morris.css" rel="stylesheet">

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

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    </@layout.put>

    <@layout.put block="header" type="prepend">
        <@layout.extends name="layouts/header/admin.ftl">
        </@layout.extends>
    </@layout.put>

    <@layout.put block="contents">
    <div id="wrapper">
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Dashboard</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- Nav tabs -->
            <ul class="nav nav-tabs">
                <li class="active"><a href="#statistics" data-toggle="tab">Statistics</a>
                </li>
                <#list conference.rooms as room>
                    <li><a href="#${room.id}" data-toggle="tab">${room.name}</a>
                    </li>
                </#list>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div class="tab-pane fade in active" id="statistics">
                    <br>
                    <div class="row">
                        <div class="col-lg-4">
                            <!-- /.panel -->
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <i class="fa fa-bar-chart-o fa-fw"></i> Participants
                                </div>
                                <div class="panel-body">
                                    <div id="morris-donut-chart"></div>
                                    <a href="#" class="btn btn-default btn-block">View Details</a>
                                </div>
                                <!-- /.panel-body -->
                            </div>
                            <!-- /.panel -->
                        </div>
                    </div>
                </div>
                <#list conference.rooms as room>
                    <div class="tab-pane fade" id="${room.id}">
                        <br>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="form-group">
                                    <select id="select_${room.id}" class="form-control">
                                        <option></option>
                                        <#list room.getPrograms(conference.id) as program>
                                        <option value="program_${program.id}">${program.date.formattedScheduleDay} - ${program.title} (${program.begin} ~ ${program.end})</option>
                                        </#list>
                                        <option value="blank" disabled>--------------------</option>
                                        <#list room.getSessions(conference.id) as sessions>
                                            <option value="session_${sessions.id}">${sessions.program.date.formattedScheduleDay} - ${sessions.title} (${sessions.program.begin} ~ ${sessions.program.end})</option>
                                        </#list>
                                    </select>
                                </div>
                                <!-- /select-group -->
                            </div>
                        </div>
                        <!-- /.row -->

                        <div class="row">
                            <div class="col-lg-12">

                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        <div class="row">
                                            <div class="col-xs-9">
                                                <span class="huge">${room.name}</span>
                                                <span id="program_time_${room.id}" class="huge"></span>
                                            </div>
                                            <div class="col-xs-3 text-right">
                                                <i class="fa fa-comments fa-3x"><span class="badge"><a id="program_badge_${room.id}" href="#">Link</a></span></i>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-body">
                                        <div class="col-lg-12">
                                            <div class="chat-panel panel panel-default">
                                                <div class="panel-heading">
                                                    <i class="fa fa-clock-o fa-fw"></i>
                                                    Timer
                                                    <div class="btn-group pull-right">
                                                        <button type="button" class="btn btn-default btn-xs" onclick="refresh_${room.id}()">
                                                            <i class="fa fa-refresh"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div class='input-group date' id='datetimepicker${room.id}'>
                                                        <input type='text' class="form-control" />
                                                        <span class="input-group-addon">
                                                            <span class="glyphicon glyphicon-calendar"></span>
                                                        </span>
                                                    </div>
                                                </div>

                                                <div id="clock_${room.id}" class="clock" style="margin:2em;"></div>
                                                <div id="message_${room.id}" style="text-align: center; color: lightcoral;"></div>
                                            </div>
                                        </div>
                                        <!-- /.col-lg-12 -->
                                        <div class="col-lg-12">
                                            <div class="chat-panel panel panel-default">
                                                <div class="panel-heading">
                                                    <i class="fa fa-comments fa-fw"></i>
                                                    Chat
                                                    <div class="btn-group pull-right">
                                                        <button type="button" class="btn btn-default btn-xs dropdown-toggle"
                                                                data-toggle="dropdown">
                                                            <i class="fa fa-chevron-down"></i>
                                                        </button>
                                                        <ul class="dropdown-menu slidedown">
                                                            <li>
                                                                <a href="#" onclick="getMessages('/messages/rooms/${room.id}', 'chat_${room.id}')">
                                                                    <i class="fa fa-refresh fa-fw"></i> Refresh
                                                                </a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <!-- /.panel-heading -->
                                                <div class="panel-body">
                                                    <ul id="chat_${room.id}" class="chat">
                                                    </ul>
                                                </div>
                                                <!-- /.panel-body -->
                                                <#if currentUser??>
                                                    <div class="panel-footer">
                                                        <div class="input-group">
                                                            <input id="chat_${room.id}_message" type="text" class="form-control input-sm"
                                                                   placeholder="Type your message here..."/>
                                                        <span class="input-group-btn">
                                                            <button class="btn btn-warning btn-sm" id="chat_${room.id}_btn">
                                                                Send
                                                            </button>
                                                        </span>
                                                        </div>
                                                    </div>
                                                    <!-- /.panel-footer -->
                                                </#if>
                                            </div>
                                            <!-- /.panel .chat-panel -->
                                        </div>
                                        <!-- /.col-lg-12 -->
                                    </div>
                                </div>
                            </div>
                            <!-- /.col-lg-12 -->
                        </div>
                        <!-- /.row -->
                    </div>
                </#list>

            </div>
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
    </@layout.put>

    <@layout.put block="footer" type="replace">
        <@layout.extends name="layouts/footer.ftl">
        </@layout.extends>
    </@layout.put>

    <@layout.put block="script">

    <!-- Bootstrap 3 Datepicker JavaScript -->
    <script type="text/javascript" src="/bower_components/moment/min/moment.min.js"></script>
    <script type="text/javascript" src="/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="/bower_components/raphael/raphael.min.js"></script>
    <script src="/bower_components/morris.js/morris.min.js"></script>
    <script src="/lib/sb-admin/js/morris-data.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/lib/sb-admin/dist/js/sb-admin-2.js"></script>
    <script src="/js/utils.js"></script>

    <!-- Flip Clock Javascript -->
    <script src="/bower_components/FlipClock/compiled/flipclock.min.js"></script>

    <!-- Disqus Javascript -->
    <script id="dsq-count-scr" src="//pjwards-aide.disqus.com/count.js" async></script>

    <script>
        <#list conference.rooms as room>
            var clock_${room.id};
            var before_time_${room.id} = ajaxGet("/conferences/${conference.id}/admin/rooms/${room.id}/timer")["timer"];
        </#list>
        $(document).ready(function () {
            <#list conference.rooms as room>
                clock_${room.id} = $('#clock_${room.id}').FlipClock({
                    clockFace: 'HourCounter',
                    countdown: true,
                    autoStart: false,
                    callbacks: {
                        stop: function() {
                            $('#message_${room.id}').html('<h2>Please finish your session.</h2>')
                        }
                    }
                });

                if(before_time_${room.id}) {
                    var before_time = moment(before_time_${room.id}).toDate();
                    var diff_time = diff(before_time, new Date());
                    if(diff_time > 0) {
                        clock_${room.id}.setTime(parseInt(diff_time));
                        clock_${room.id}.start();
                    }
                }

                $('#datetimepicker${room.id}').datetimepicker({
                    defaultDate: before_time_${room.id}? moment(before_time_${room.id}) : false
                });

                $('#datetimepicker${room.id}').on("dp.hide", function (e) {
                    sendTimer("/conferences/${conference.id}/admin/rooms/${room.id}/timer", e.date.toISOString());
                    before_time_${room.id} = ajaxGet("/conferences/${conference.id}/admin/rooms/${room.id}/timer")["timer"];

                    if(before_time_${room.id}) {
                        var before_time = moment(before_time_${room.id}).toDate();
                        var diff_time = diff(before_time, new Date());
                        if(diff_time > 0) {
                            clock_${room.id}.setTime(parseInt(diff_time));
                            clock_${room.id}.start();
                        }
                    }
                });

                getMessages("/messages/rooms/${room.id}", "chat_${room.id}");

                $('#select_${room.id}').change(function () {
                    var selected_item = $(this).val();
                    getProgram(selected_item, "program_time_${room.id}", "program_badge_${room.id}");
                });

                $('#chat_${room.id}_btn').on("click", function () {
                    sendMessage("/messages/rooms/${room.id}", "chat_${room.id}_message");
                    $('#chat_${room.id}_message').val('');
                    getMessages("/messages/rooms/${room.id}", "chat_${room.id}");
                });

                setInterval(function () {
                    getMessages("/messages/rooms/${room.id}", "chat_${room.id}");
                    refresh_${room.id}();
                }, 120000);
            </#list>
        });

        function sendTimer(url, date) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            var data = new FormData();
            data.append("timer", date);

            $.ajax({
                url: url,
                cache: false,
                contentType: false,
                processData: false,
                data: data,
                type: 'POST',
                async:false,
                beforeSend: function(xhr) {
                    // here it is
                    xhr.setRequestHeader(header, token);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(textStatus + " " + errorThrown);
                }
            });
        }

        function ajaxGet(url) {
            var response = $.ajax({
                url: url,
                cache: false,
                contentType: false,
                processData: false,
                async:false,
                type: 'GET',
                dataType: "JSON"
            }).responseText;

            return JSON.parse(response);
        }

        function diff(future, current) {
            return future.getTime() / 1000 - current.getTime() / 1000;
        }

        <#list conference.rooms as room>
            function refresh_${room.id}() {
                var new_time = ajaxGet("/conferences/${conference.id}/admin/rooms/${room.id}/timer")["timer"];
                console.log(new_time)
                console.log(before_time_${room.id})
                if(new_time && before_time_${room.id} != new_time) {
                    var before_time = moment(before_time_${room.id}).toDate();
                    var diff_time = diff(before_time, new Date());
                    if(diff_time > 0) {
                        clock_${room.id}.setTime(parseInt(diff_time));
                        clock_${room.id}.start();
                    }
                    before_time_${room.id} = new_time;
                }
            }
        </#list>

        function getMessages(url, id) {
            $.ajax({
                url: url,
                cache: false,
                contentType: false,
                processData: false,
                type: 'GET',
                success: function(data) {
                    printMessage(data, id);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(textStatus + " " + errorThrown);
                }

            })
        }

        function sendMessage(url, id) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            var data = new FormData();
            data.append("message", $('#' + id).val());

            $.ajax({
                url: url,
                cache: false,
                contentType: false,
                processData: false,
                data: data,
                type: 'POST',
                async:false,
                beforeSend: function(xhr) {
                    // here it is
                    xhr.setRequestHeader(header, token);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(textStatus + " " + errorThrown);
                }
            });
        }

        function printMessage(source, id) {
            var $Chat = $('#' + id);
            $Chat.empty();

            for(var i=0; i< source.length; i++) {
                var message = source[i];
                var direction = "left";
                var $Header;
                if(message.sender.id == <#if currentUser??>${currentUser.id}<#else>-1</#if>) {
                    direction = "right";
                    $Header = $("<div/>")
                            .attr("class", "header")
                            .append($("<strong/>")
                                    .attr("class", "primary-font pull-right")
                                    .text(message.sender.name)
                            )
                            .append($("<small/>")
                                    .attr("class", "text-muted")
                                    .append($("<i/>")
                                            .attr("class", "fa fa-clock-o fa-fw")
                                    )
                                    .append(timeSince(message.date))
                            );
                } else {
                    $Header = $("<div/>")
                            .attr("class", "header")
                            .append($("<small/>")
                                    .attr("class", "text-muted pull-right")
                                    .append($("<i/>")
                                            .attr("class", "fa fa-clock-o fa-fw")
                                    )
                                    .append(timeSince(message.date))
                            )
                            .append($("<strong/>")
                                    .attr("class", "primary-font")
                                    .text(message.sender.name)
                            );
                }


                $Chat.prepend($("<li/>")
                        .attr("class", "clearfix " + direction)
                        .append($("<span/>")
                                .attr("class", "chat-img pull-" + direction)
                                .append($("<img/>")
                                        .attr("src", message.sender.assets.realPath)
                                        .attr("alt", "User Avatar")
                                        .attr("class", "img-circle")
                                        .attr("style", "width: 50px; height: 50px;")
                                )
                        ).append($("<div/>")
                                .attr("class", "chat-body clearfix")
                                .append($Header)
                                .append($("<p/>")
                                        .text(message.message)
                                )
                        )
                )
            }
        }

        function getProgram(selected_program, program_time_id ,program_badge_id) {
            selected_program = selected_program.split('_');

            var source;
            if(selected_program[0] == "program") {
                source = ajaxGet("/api/programs/" + selected_program[1]);
                $("#" + program_time_id).text("(" + source.begin + " ~ " + source.end + ")");
                $("#" + program_badge_id).attr("href", "${myApp.address}/programs/" + selected_program[1] + "#disqus_thread");
            } else if(selected_program[0] == "session") {
                source = ajaxGet("/api/sessions/" + selected_program[1]);
                $("#" + program_time_id).text("(" + source.program.begin + " ~ " + source.program.end + ")");
                $("#" + program_badge_id).attr("href", "${myApp.address}/sessions/" + selected_program[1] + "#disqus_thread");
            }
        }

    </script>
    </@layout.put>
</@layout.extends>
