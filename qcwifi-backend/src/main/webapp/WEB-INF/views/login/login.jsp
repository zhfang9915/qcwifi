<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="${qc.nginx }static/favicon.ico" type="image/x-icon" />
<title>前辰无线运维平台</title>

<!-- Bootstrap -->
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<link href="http://cdn.bootcss.com/bootstrap-validator/0.5.3/css/bootstrapValidator.min.css" rel="stylesheet">
<link rel="stylesheet" href="${qc.nginx }static/css/login/login.css">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="box">
		<div class="container">
			<div class="row">
				<dov class="logo-img"> 
					<img src="${qc.nginx }static/img/login/qclogo3.png" alt=""> 
<%-- 					<a class="btn btn-default pull-right" href="${qc.nginx }download.html" target="_blank" style="margin-top: 16px;">固件下载</a> --%>
				</dov>
				<div class="cont-login">
					<div class="login-window">
						<h3>
							<img src="${qc.nginx }static/img/login/qclogo.png" alt=""><span>登录云平台</span>
						</h3>
						<form action="${qc.rootPath }login/doLogin" method="post" autocomplete="off">
							<div class="form-group">
								<input name="username" type="text" class="form-control" placeholder="用户名" value="${username }">
							</div>
							<div class="form-group">
								<input name="password" type="password" class="form-control" placeholder="密码" value="${password }">
							</div>
							<div class="form-group code">
								<input name="rand" type="text" class="form-control" placeholder="验证码" value="${rand }">
								<img src="${qc.rootPath }base/randcode?"+(new Date()).valueOf() class="img-randconde pull-right"
									onclick="common.clickImage();" style="cursor: pointer;">
							</div>
							<div class="checkbox">
								<label><input type="checkbox" name="rememberMe"/>下次自动登录 </label>
								<p class="pull-right" style="color: red">${errorMsg }</p>
							</div>
							<button type="submit" class="btn btn-primary">登录</button>
							<p class="forgetpassword text-right">
								<a href="${qc.rootPath }user/find">忘记密码</a> <span>|</span> 
								<a href="${qc.rootPath }user/register">用户注册</a>
							</p>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- 滚动图 -->
		<div class="container-fluid scroll-banner">
			<div class="row">

				<div id="carousel-example-generic" class="carousel slide"
					data-ride="carousel">
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0"
							class="active"></li>
<!-- 						<li data-target="#carousel-example-generic" data-slide-to="1"></li> -->
<!-- 						<li data-target="#carousel-example-generic" data-slide-to="2"></li> -->
					</ol>

					<!-- Wrapper for slides -->
					<div class="carousel-inner" role="listbox">
						<div class="item active">
							<img src="${qc.nginx }static/img/login/banner.jpg" alt="...">
							<div class="carousel-caption">
							    <p class="text-left" style="color:#666;font-size:22px"></p>
							</div>
						</div>
<!-- 						<div class="item"> -->
<%-- 							<img src="${qc.nginx }static/img/login/banner.jpg" alt="..."> --%>
<!-- 							<div class="carousel-caption"> -->
<!-- 							    <p class="text-left" style="color:#666;font-size:22px">卓越性能</p> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="item"> -->
<%-- 							<img src="${qc.nginx }static/img/login/banner.jpg" alt="..."> --%>
<!-- 							<div class="carousel-caption"> -->
<!-- 							    <p class="text-left" style="color:#666;font-size:22px">个性服务</p> -->
<!-- 							</div> -->
<!-- 						</div> -->

					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<!-- foot -->
	<div class="container-fluid footer">
		<div class="container">
			<div class="row text-center">
				<p style="heigh: 70px"></p>
			</div>
		</div>
	</div>

	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="http://cdn.bootcss.com/bootstrap-validator/0.5.3/js/bootstrapValidator.min.js"></script>
	<script rootPath="${qc.nginx }" type="text/javascript" src="${qc.nginx }static/script/common.js"></script>
	<script type="text/javascript">
		$(function() {
			try {
				var _href = window.location.href + "";
				if (_href && _href.indexOf('?kickout') != -1) {
					layer.msg('您已经被踢出，请重新登录！');
				}
			} catch (e) {

			}
			
			$('form').bootstrapValidator({
		        message: '登录信息初始判断',
		        feedbackIcons: {/*输入框不同状态，显示图片的样式*/
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {/*验证*/
		        	username: {
		                validators: {
		                    notEmpty: {/*非空提示*/
		                        message: ' '
		                    }
		                }
		            },
		            pwd: {
		                validators: {
		                    notEmpty: {
		                        message: ' '
		                    }
		                }
		            },
		            rand: {
		            	validators: {
		            		notEmpty: {
		            			message: ' '
		            		},
		            		stringLength: {
		            			min: 4,
		            			max: 4,
		            			message: ' '
		            		}
		            	}
		            }
		        }
		    });
		});
	</script>
</body>
</html>