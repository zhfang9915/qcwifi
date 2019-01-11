<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>云平台-推送模版预览</title>
	<meta name="keywords" content="云平台-推送模版预览">
	<meta name="description" content="云平台-推送模版预览" />
	<meta name="HandheldFriendly" content="True" />
	<link rel="shortcut icon" href="${qc.nginx }static/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${qc.nginx }static/bootstrap/css/bootstrap.min.css">
	
	<script src="${qc.nginx }static/jquery-1.12.4.min.js"></script>
	<script src="${qc.nginx }static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${qc.nginx }static/jquery.qrcode.min.js"></script>
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
		<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	
	<script type="text/javascript">
		$(function(){
			$('#qrcode').qrcode({
				text: "${qc.rootPath}extension/template/preview/${tempId}",
				render:"canvas",
				width:210,
				height:210,
			});  
		});
	</script>
	
	<style type="text/css">
		.mobole{
			position:relative;
			height:588px;
    		background: url('${qc.nginx}static/img/preview/mobile-bg.png') no-repeat center 20px;
		}
		.qrcode{
		        text-align: center;
			    position: absolute;
			    top:50%;
			    left:50%;
			    transform:translate(-50%,-50%);
		}
	</style>
</head>

<body>
	<div class="container-fluid">
		<c:choose>
			<c:when test="${viewType=='phone' }">
				<div class="mobole">
					<div class="qrcode">
						<div id="qrcode"></div>
						<span>手机扫描二维码预览效果</span>
					</div>
				</div>
			</c:when>
			<c:when test="${viewType=='pc' }">
				<div class="row">
					${preview }
				</div>
			</c:when>
			<c:otherwise>
				<span class="text-center">生成预览失败，因为指定的预览类型不支持</span>
			</c:otherwise>
		</c:choose>
			
		
	</div>
	
</body>
</html>