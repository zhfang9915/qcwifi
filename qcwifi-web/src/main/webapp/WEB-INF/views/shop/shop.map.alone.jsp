<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>商铺地图</title>
    <link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
	<style type="text/css">
 #shopsMap { 
     position: absolute; 
     width: 99%; 
     height: 99%; 
 } 
</style>
</head>
<body class="layout-body">
	<div id="shopsMap"></div>

	<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
	<script src="${qc.nginx }cloud/layer/layer.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=XlwywgLBmWY54EzsPyNuUCm8hhnbsAGG"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
	<script rootURL=${qc.rootPath } staticURL=${qc.nginx } src="${qc.nginx }script/cloud/shop/shop.map.js"></script>
</body>
</html>