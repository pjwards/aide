<#-- @ftlvariable name="conference" type="com.pjwards.aide.domain.Conference" -->

<@layout.extends name="layouts/default.ftl">
    <@layout.put block="head">
    <title>${conference.name} :: main</title>

    <!-- MetisMenu CSS -->
    <link href="/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="/lib/sb-admin/dist/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/lib/sb-admin/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="/bower_components/morris.js/morris.css" rel="stylesheet">

    <!-- Flip Clock CSS -->
    <link href="/bower_components/FlipClock/compiled/flipclock.css" rel="stylesheet">
    <link href="/css/conference/clock.css" rel="stylesheet">

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
                                    <i class="fa fa-bar-chart-o fa-fw"></i> Donut Chart Example
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
                                        <#list room.getPrograms(conference.id) as program>
                                            <option value="program_${program.id}">${program.date.formattedScheduleDay} - ${program.title}</option>
                                        </#list>
                                        <option value="blank" disabled>--------------------</option>
                                        <#list room.getSessions(conference.id) as sessions>
                                            <option value="session_${sessions.id}">${sessions.program.date.formattedScheduleDay} - ${sessions.title}</option>
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
                                            </div>
                                            <div class="col-xs-3 text-right">
                                                <i class="fa fa-comments fa-3x"><span class="badge">5</span></i>
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
                                                        <button type="button" class="btn btn-default btn-xs">
                                                            <i class="fa fa-cogs"></i>
                                                        </button>
                                                        <button type="button" class="btn btn-default btn-xs">
                                                            <i class="fa fa-play"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                                <div id="clock_${room.id}" class="clock" style="margin:2em;"></div>
                                                <div id="message_${room.id}" class="message"></div>
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
                                                                <a href="#">
                                                                    <i class="fa fa-refresh fa-fw"></i> Refresh
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a href="#">
                                                                    <i class="fa fa-check-circle fa-fw"></i> Available
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a href="#">
                                                                    <i class="fa fa-times fa-fw"></i> Busy
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a href="#">
                                                                    <i class="fa fa-clock-o fa-fw"></i> Away
                                                                </a>
                                                            </li>
                                                            <li class="divider"></li>
                                                            <li>
                                                                <a href="#">
                                                                    <i class="fa fa-sign-out fa-fw"></i> Sign Out
                                                                </a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <!-- /.panel-heading -->
                                                <div class="panel-body">
                                                    <ul class="chat">
                                                        <li class="left clearfix">
                                    <span class="chat-img pull-left">
                                        <img src="http://placehold.it/50/55C1E7/fff" alt="User Avatar"
                                             class="img-circle"/>
                                    </span>
                                                            <div class="chat-body clearfix">
                                                                <div class="header">
                                                                    <strong class="primary-font">Jack Sparrow</strong>
                                                                    <small class="pull-right text-muted">
                                                                        <i class="fa fa-clock-o fa-fw"></i> 12 mins ago
                                                                    </small>
                                                                </div>
                                                                <p>
                                                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum
                                                                    ornare dolor, quis ullamcorper ligula sodales.
                                                                </p>
                                                            </div>
                                                        </li>
                                                        <li class="right clearfix">
                                    <span class="chat-img pull-right">
                                        <img src="http://placehold.it/50/FA6F57/fff" alt="User Avatar"
                                             class="img-circle"/>
                                    </span>
                                                            <div class="chat-body clearfix">
                                                                <div class="header">
                                                                    <small class=" text-muted">
                                                                        <i class="fa fa-clock-o fa-fw"></i> 13 mins ago
                                                                    </small>
                                                                    <strong class="pull-right primary-font">Bhaumik Patel</strong>
                                                                </div>
                                                                <p>
                                                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum
                                                                    ornare dolor, quis ullamcorper ligula sodales.
                                                                </p>
                                                            </div>
                                                        </li>
                                                        <li class="left clearfix">
                                    <span class="chat-img pull-left">
                                        <img src="http://placehold.it/50/55C1E7/fff" alt="User Avatar"
                                             class="img-circle"/>
                                    </span>
                                                            <div class="chat-body clearfix">
                                                                <div class="header">
                                                                    <strong class="primary-font">Jack Sparrow</strong>
                                                                    <small class="pull-right text-muted">
                                                                        <i class="fa fa-clock-o fa-fw"></i> 14 mins ago
                                                                    </small>
                                                                </div>
                                                                <p>
                                                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum
                                                                    ornare dolor, quis ullamcorper ligula sodales.
                                                                </p>
                                                            </div>
                                                        </li>
                                                        <li class="right clearfix">
                                    <span class="chat-img pull-right">
                                        <img src="http://placehold.it/50/FA6F57/fff" alt="User Avatar"
                                             class="img-circle"/>
                                    </span>
                                                            <div class="chat-body clearfix">
                                                                <div class="header">
                                                                    <small class=" text-muted">
                                                                        <i class="fa fa-clock-o fa-fw"></i> 15 mins ago
                                                                    </small>
                                                                    <strong class="pull-right primary-font">Bhaumik Patel</strong>
                                                                </div>
                                                                <p>
                                                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum
                                                                    ornare dolor, quis ullamcorper ligula sodales.
                                                                </p>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                </div>
                                                <!-- /.panel-body -->
                                                <div class="panel-footer">
                                                    <div class="input-group">
                                                        <input id="btn-input" type="text" class="form-control input-sm"
                                                               placeholder="Type your message here..."/>
                                <span class="input-group-btn">
                                    <button class="btn btn-warning btn-sm" id="btn-chat">
                                        Send
                                    </button>
                                </span>
                                                    </div>
                                                </div>
                                                <!-- /.panel-footer -->
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
    <!-- Metis Menu Plugin JavaScript -->
    <script src="/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="/bower_components/raphael/raphael.min.js"></script>
    <script src="/bower_components/morris.js/morris.min.js"></script>
    <script src="/lib/sb-admin/js/morris-data.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/lib/sb-admin/dist/js/sb-admin-2.js"></script>

    <!-- Flip Clock Javascript -->
    <script src="/bower_components/FlipClock/compiled/flipclock.min.js"></script>

    <script>
        $(document).ready(function () {
            <#list conference.rooms as room>
                var clock_${room.id};
                clock_${room.id} = $('#clock_${room.id}').FlipClock({
                    clockFace: 'HourCounter',
                    autoStart: false,
                    callbacks: {
                        stop: function() {
                            $('#message_${room.id}').html('The clock has stopped!')
                        }
                    }
                });

                clock_${room.id}.setTime(100);
                clock_${room.id}.setCountdown(true);
                clock_${room.id}.start();
            </#list>
        });
    </script>
    </@layout.put>
</@layout.extends>
