<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>个人中心</title>
    <link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
    
    <link rel="stylesheet" href="${qc.nginx }cloud/css/layui.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/css.css">
</head>
<body class="layout-body">
    <div class="layout-wrap">
        <div class="layout-header">
            <div class="layout-hd">
                <div class="lay-toplk layui-clear">
                    <ul class="toplk layout-left">
                        <li><a href="">商铺</a></li>
                        <li><a href="">推广管家</a></li>
                    </ul>
                    <ul class="toplk layout-right">
                        <li><a href="" class="lk-icon lk-msg"></a></li>
                        <li><a href="" class="lk-icon lk-user"></a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="layout-main layui-clear">
			<%@include file="../common/menu-left.jsp"%>
            <div class="layout-in-main">
                <div class="in-toptit">
                	<div class="in-text">资产管理</div>
                </div>
                <div class="in-single">
                	<!-- top -->
                    <div class="zc-top">
                        <div class="zc-text">可提现余额：<span class="text-red"><strong id="availableBalance" style="font-size: 28px">0.000</strong></span> 金币</div>
                        <div class="zc-action">
                            <a class="layui-btn  layui-btn-disable" id="withdrawBtn">提现</a>　<span>最低 <strong id="withdrawMinBalance"> 0 </strong> 金币起提</span>
                        </div>
                    </div>
                    <div class="zc-total-info">
                        <div class="zc-row">
                            <span class="zc-item">总余额：<strong id="totalBalance" style="font-size: 20px">0.000</strong></span>
                            <span class="zc-item">冻结余额：<strong id="freezeBalance" style="font-size: 20px">0.000</strong></span>
                        </div>
                        <div class="zc-row">
                            您当前为试用，升级为正式版并开具发票后可解冻，<a href="" class="zc-more">查看详情>></a>
                        </div>
                    </div>
                    <!-- 余额还可以： -->
                    <div class="blance-wrap">
                        <div class="hd">余额还可以：</div>
                        <ul class="blance-list">
                            <li>
                                <a href="商铺升级.html" class="b-box">
                                    <p>升级商铺</p>
                                    <img src="${qc.nginx }cloud/images/s1.png" alt="">
                                </a>
                            </li>
                            <li>
                                <a href="" class="b-box">
                                    <p>购买设备</p>
                                    <img src="${qc.nginx }cloud/images/s2.png" alt="">
                                </a>
                            </li>
                            <li>
                                <a href="短信充值-充值记录.html" class="b-box">
                                    <p>购买短信</p>
                                    <img src="${qc.nginx }cloud/images/s3.png" alt="">
                                </a>
                            </li>
                            <li>
                                <a href="" class="b-box">
                                    <p>定制平台</p>
                                    <img src="${qc.nginx }cloud/images/s4.png" alt="">
                                </a>
                            </li>
                            <li>
                                <a href="" class="b-box">
                                    <p>定制平台</p>
                                    <img src="${qc.nginx }cloud/images/s5.png" alt="">
                                </a>
                            </li>
                        </ul>
                    </div>
                    <!-- 收支明细 -->
                    <div class="zc-details">
                        <div class="sz-text">收支明细</div>
  						<div class="layui-form layui-txt-form" action="" style="width:350px">
                            <input type="text" class="layui-input layui-lg-date" readonly id="searchByDate" value="" placeholder="请选择日期" lay-key="7">
                            <i class="layui-icon layui-icon-date">&#xe637;</i>
                        </div>
                        <div class="sz-text">收入：+0.000　支出-0.000</div>
                    </div>
                    <table class="layui-table" id="paymentsDetailsTable" lay-size="lg" lay-skin="line"></table>
                </div>
            </div>
        </div>
        <!--footer-->
        <div class="layout-footer layui-clear">
            <div class="erweima">
                <img src="${qc.nginx }cloud/images/erweima.png" alt="">
                <div class="layout-ftbox">
                    <h3>关注我们</h3>
                    <p>扫码关注官方微信:</p>
                    <p>新浪微博：XXX</p>
                </div>
            </div>
            <div class="layout-ftbox">
                <h3>论坛交流</h3>
                <ul class="ft-list">
                    <li><a href="">官方公告</a></li>
                    <li><a href="">技术交流</a></li>
                    <li><a href="">建议反馈</a></li>
                </ul>
            </div>
            <div class="layout-tel">联系我们：689780809-000</div>
        </div>
    </div>


<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layer/layer.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }/script/cloud/personal/asset.management.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		assetManagement.query.balance();
	});
</script>

</body>
</html>
