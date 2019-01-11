<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="${rapp.nginx }static/favicon.ico" type="image/x-icon" /> 
    <title>前辰无线运营平台</title>
    <!--Bootstrap-->
    <link href="${rapp.nginx }static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${rapp.nginx }static/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
    <!--[if IE 7]>
    <link href="/${rapp.nginx }static/font-awesome/css/font-awesome-ie7.min.css" rel="stylesheet" />
    <![endif]-->
    <link href="${rapp.nginx }static/sidebar-menu/sidebar-menu.css" rel="stylesheet" />

    <script src="${rapp.nginx }static/jquery-1.12.4.min.js"></script>
    <script src="${rapp.nginx }static/bootstrap/js/bootstrap.min.js"></script>
    <script src="${rapp.nginx }static/sidebar-menu/sidebar-menu.js"></script>
    <script src="${rapp.nginx }static/bootstrap/js/bootstrap-tab.js"></script>
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        body {
            font-size: 12px;
        }

        .nav > li > a {
            padding: 5px 10px;
        }

        .tab-content {
            padding-top: 5px;
        }
    </style>
</head>
<body>
    <div class="navbar navbar-default" id="navbar">
        <div class="navbar-container" id="navbar-container">
            <div class="navbar-header pull-left">
				<a href="#" class="navbar-brand"> 
					<img src="${rapp.nginx }static/img/web-logo.png" class="img-responsive" 
						style="width:35px;height:35px;display:inline">
					<small>前辰无线 </small>
				</a>
			</div>

            <div class="navbar-header pull-right" role="navigation">
			    <div style="color: #fff; height:45px; line-height:45px; font-size: 15px;">
					<span>${backUser.username}</span>
					<span>&nbsp;&nbsp;&nbsp;</span>
				    <a href="logout" style="color: #fff;"><i class="icon-off"></i> 退出</a>
				</div>
            </div>
        </div>
	    
    </div>
    <div class="main-container" id="main-container">
        <div class="main-container-inner">
            <a class="menu-toggler" id="menu-toggler" href="#">
                <span class="menu-text"></span>
            </a>
            <div class="sidebar" id="sidebar">
                <ul class="nav nav-list" id="menu"></ul>
                <div class="sidebar-collapse" id="sidebar-collapse">
                    <i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
                </div>
            </div>
            <div class="main-content">
                <div class="page-content">
                    <div class="row">
                        <div class="col-xs-12" style="padding-left:5px;">
                            <ul class="nav nav-tabs" role="tablist">
                                <li class="active"><a href="#Index" role="tab" data-toggle="tab">我的前辰</a></li>
                            </ul>
                            <div class="tab-content" style="height:865px">
                                <div role="tabpanel" class="tab-pane active" id="Index" style="height:100%">
									<iframe id="iframe_tab_apvert-list" src="" width="100%" height="100%" 
										frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes">
									</iframe>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
        <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
            <i class="icon-double-angle-up icon-only bigger-110"></i>
        </a>
    </div>
<script type="text/javascript">
    $(function () {
		$('#menu').sidebarMenu({
				data : [{
						    id : 'paltform',
						    text : '路由器',
						    icon : 'icon-rss',
						    url : '',
						    menus : [ {
							    id : 'js_index',
							    text : 'JS模版',
							    icon : 'icon-h-sign',
							    url : '/backend/js/index'
							}, {
							    id : 'firmware_index',
							    text : '固件管理',
							    icon : 'icon-columns',
							    url : '/backend/firmware/index'
							}, {
							    id : 'plugin_index',
							    text : '插件管理',
							    icon : 'icon-th',
							    url : '/backend/plugin/index'
							}, {
							    id : 'extension_template_index',
							    text : '广告模板',
							    icon : 'icon-book',
							    url : '/backend/extension/template/index'
							} ]
						}, {
						    id : 'cloud',
						    text : '云平台',
						    icon : 'icon-cloud',
						    url : '',
						    menus : [ {
							    id : 'router_index',
							    text : '设备列表',
							    icon : 'icon-columns',
							    url : '/backend/router/index'
							} ]
						}, {
						    id : 'jobs_index',
						    text : '定时任务',
						    icon : 'icon-tasks',
						    url : '/backend/job/index'
						}, {
						    id : 'cloud_log',
						    text : '系统日志',
						    icon : 'icon-globe',
						    url : '',
						    menus : [ {
							    id : 'log_api',
							    text : 'API接口日志',
							    icon : 'icon-retweet',
							    url : '/backend/system/log/api'
							} ]
						}, {
						    id : 'system',
						    text : '系统设置',
						    icon : 'icon-cogs',
						    url : '',
						    menus : [ {
						        id : 'permission_index',
						        text : '权限管理',
						        icon : 'icon-lock',
						        url : '/backend/permission/index'
						    }, {
						        id : 'role_index',
						        text : '角色管理',
						        icon : 'icon-user-md',
						        url : '/backend/role/index'
						    }, {
						        id : 'user_index',
						        text : '用户管理',
						        icon : 'icon-user',
						        url : '/backend/user/index'
						    }, {
						        id : 'system_index',
						        text : '系统配置',
						        icon : 'icon-tasks',
						        url : '/backend/system/index'
						    } ]
						} ]
			});
		});
	</script>
    <script src="${rapp.nginx }static/ace/js/ace-extra.min.js"></script>
    <script src="${rapp.nginx }static/ace/js/ace.min.js"></script>
    
</body>
</html>
