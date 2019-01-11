<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- //不缓存 -->
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="0">
<title>登录</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link href="static/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="static/css/login.css">
<link rel="stylesheet" href="static/css/drag.css">

<style type="text/css">
.slidetounlock {
	font-size: 12px;
	background: -webkit-gradient(linear, left top, right top, color-stop(0, #4d4d4d),
		color-stop(.4, #4d4d4d), color-stop(.5, #fff), color-stop(.6, #4d4d4d),
		color-stop(1, #4d4d4d));
	-webkit-background-clip: text;
	/* -webkit-text-fill-color: transparent; */
	-webkit-animation: slidetounlock 3s infinite;
	-webkit-text-size-adjust: none
}

@-webkit-keyframes slidetounlock { 
	0%{
		background-position: -200px 0
	}
	100%{
		background-position:200px 0
	}
}
</style>
</head>
<script type="text/javascript">
	if (top.location != self.location) {
		top.location = self.location;
	}
</script>
<body>
	<div class="box">
		<div class="container">
			<div class="row">
				<div class="logo-img">
					<img src="static/images/dx_logo.png" alt="">
				</div>
				<div class="cont-login">
					<div id="logindiv" class="login-window">
						<p class="login-panel">
							<span>登录平台</span>
						</p>
						<div class="form-group">
							<input type="hidden" id="urlcs" name="channel" value="">
							<input type="text" name="phone" class="form-control" id="phone"
								placeholder="手机号" value="${iphone}">
						</div>
						<div class="form-group">
							<input type="password" name="password" class="form-control"
								id="password" placeholder="密码" value="${password}">
						</div>
						<div id="wrapper" style="position: relative; left: 0px;">
							<div id="drag">
								<div class="drag_bg"></div>
								<div class="drag_text slidetounlock"
									onselectstart="return false;" unselectable="on">
									请按住滑块，拖动到最右边</div>
								<div class="handler handler_bg"></div>
							</div>
						</div>
						<div class="form-group password">
							<input type="text" id="khdxsmsCode" name="khdxsmsCode"
								class="form-control" placeholder="短信验证码">
							<button id="huqucode" class="msgs">获取验证码</button>
						</div>
						</form>
						<div class="checkbox">
							<p id="cwxx" class="pull-right" style="color: red">${cwxx}</p>
						</div>
						<button type="button" class="btn btn-primary" id="loginVality">登录</button>
					</div>
				</div>
			</div>
		</div>
		<div class="container-fluid scroll-banner">
			<div class="row">
				<div id="carousel-example-generic" class="carousel slide"
					data-ride="carousel">
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0"
							class="active"></li>
					</ol>
					<div class="carousel-inner" role="listbox">
						<div class="item active">
							<img src="static/images/banner.jpg" alt="...">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>

	<script type="text/javascript">
		var checkSubmitFlg = false;
		function checkSubmit() {
			if (checkSubmitFlg == true) {
				return false; //当表单被提交过一次后checkSubmitFlg将变为true,根据判断将无法进行提交。
			}
			checkSubmitFlg == true;
			return true;
		}
	</script>

	<script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/login.js"></script>
	<script src="static/js/jquery.cookie.js"></script>
	<script src="static/js/md5.js"></script>
	<script>
		$('#drag').drag();
	</script>
</body>
</html>