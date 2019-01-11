<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>个人中心</title>
    <link rel="shortcut icon" href="${qc.nginx }favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${qc.nginx }cloud/css/modules/layer/default/layer.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/layui.css">

    <link rel="stylesheet" href="${qc.nginx }cloud/css/css.css">
    <link rel="stylesheet" href="${qc.nginx }cloud/css/account.settings.css">
</head>
<body class="layout-body">
    <div class="layout-wrap">
        <div class="layout-header">
            <%@include file="../common/header.jsp"%>
        </div>
        <div class="layout-main layui-clear">
            <%@include file="../common/menu-left.jsp"%>
            <div class="layout-in-main">
                <div class="in-toptit">
                    <div class="in-text">基本信息</div>
                </div>
                <div class="in-single">
                    <div class="layui-form layui-basic-form">
                        <div class="layui-form-item">
                          <label class="layui-form-label">用户名： </label>
                          <div class="layui-input-block">
                            <div class="layui-text">
                                ${user.username }
                            </div>
                          </div>
                        </div>
                        <div class="layui-form-item">
                          <label class="layui-form-label">昵称： </label>
                          <div class="layui-input-block">
                            <div class="layui-text">
                                <span id="nickname">${user.nickname }</span>　　
                                <button id="updateName" class="layui-btn">修改昵称</button>
                            </div>
                          </div>
                        </div>
                        <div class="layui-form-item">
                          <label class="layui-form-label">注册时间：</label>
                          <div class="layui-input-block">
                            <div class="layui-text">
                                <fmt:formatDate value="${user.createTime }" type="BOTH"/>
                            </div>
                          </div>
                        </div>
                    </div>
                </div>
                <div class="in-toptit">
                    <div class="in-text">收货地址</div>
                </div>
                <div class="in-single">
                    <div class="table-wrap table-adds-wrap layout-center">
                        <table class="layui-table" lay-size="lg" lay-skin="line" id="delivery_table">
                            <thead>
                                <tr>
                                    <th>收件人</th>
                                    <th>地址</th>
                                    <th>邮编</th>
                                    <th>联系电话</th>
                                    <th>收件人</th>
                                </tr>
                            </thead>
                            <tbody id="address-list"></tbody>
                        </table>
                    </div>

                    <!-- 使用新地址 -->
                    <div class="inner-footer">
                        <button id="addLoaction" class="layui-btn layui-lg-btn">使用新地址</button>
                        <span class="inner-text">您已创建<i id="address-count">0</i>个地址，最多可创建<i>10</i>个</span>
                    </div>
                </div>
            </div>
        </div>
        <!--footer-->
        <%@include file="../common/footer.jsp"%>
    </div>

<!--联系人-->
<div class="layui-layer layui-layer-page" id="layui-contact" style="display: none;">
  <div class="layui-layer-title" style="cursor: move;">修改昵称</div>
  <div id="layerDemoauto" class="layui-layer-content">
        <div class="layui-form">
          <div class="layui-form-item">
              <input id="new_nickname" type="text" name="title" required  lay-verify="required" class="layui-input">
          </div>
          <div class="layui-form-item">
            <div class="layui-button-block">
              <button class="layui-btn layui-lg-btn" id="updateNickName">确认</button>
              <button type="reset" class="layui-btn layui-lg-btn layui-btn-reset">取消</button>
            </div>
          </div>
        </div>
  </div>
  <span class="layui-layer-setwin">
    <a class="layui-layer-ico layui-layer-close layui-layer-close1" href="javascript:;"></a>
  </span>
</div>

<!--新地址-->
<div class="layui-layer layui-layer-page" id="layuiAddLoaction" style="display: none;">
  <div class="layui-layer-title" style="cursor: move;">收货地址</div>
  <div id="addDeliveryDiv" class="layui-layer-content">
        <form id="deliveryForm" class="layui-form layui-ads-form" lay-filter="add-delivery-div">
                    <div class="layui-form-item">
                      <label class="layui-form-label">收货人：</label>
                      <div class="layui-input-block">
                        <input type="hidden" name="id">
                        <input type="text" name="receiver" lay-verify="required" autocomplete="off" class="layui-input">
                      </div>
                    </div>
                    <div class="layui-form-item">
                      <label class="layui-form-label">联系电话：</label>
                      <div class="layui-input-block">
                        <input type="text" name="phoneNum" lay-verify="required|phone" maxlength="11" autocomplete="off" class="layui-input">
                      </div>
                    </div>
                    
                    <div class="layui-form-item">
                      <label class="layui-form-label">所在地区：</label>
                      <div class="layui-input-block">
                            <select id="add-province" name="province" lay-filter="add-province" lay-verify="required"></select>
                            <select id="add-city" name="city" lay-filter="add-city" lay-verify="required"></select>
                            <select id="add-area" name="area" lay-filter="add-area" lay-verify="required"></select>
                      </div>
                    </div>

                    <div class="layui-form-item">
                      <label class="layui-form-label">详细地址：</label>
                      <div class="layui-input-block">
                        <input type="text" name="address" lay-verify="required" autocomplete="off" class="layui-input">
                      </div>
                    </div>
                    <div class="layui-form-item">
                      <label class="layui-form-label">邮政编码：</label>
                      <div class="layui-input-block">
                        <input type="text" name="postCode" lay-verify="required|number" maxlength="6" autocomplete="off" class="layui-input">
                      </div>
                    </div>
                    <div class="layui-form-item">
                      <div class="layui-input-block">
                        <span class="layui-check" style="margin-left: 0">
                            <input type="checkbox" name="isDefault"> <span>设置为默认地址</span>
                        </span>
                      </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-button-block">
                          <button lay-submit lay-filter="add-delivery" class="layui-btn layui-lg-btn">确认</button>
                          <button type="reset" class="layui-btn layui-lg-btn layui-btn-reset">取消</button>
                        </div>
                      </div>
              </form>
  </div>
  <span class="layui-layer-setwin">
    <a class="layui-layer-ico layui-layer-close layui-layer-close1" href="javascript:;"></a>
  </span>
</div>

<div class="layui-layer-shade" style="display: none;"></div>



<script src="${qc.nginx }cloud/jquery-2.1.4.js"></script>
<script src="${qc.nginx }cloud/layui.js"></script>
<script src="${qc.nginx }cloud/layer/layer.js"></script>
<script type="text/javascript">
var adds=["add-province","add-city","add-area"];
</script>
<script type="text/javascript" src="${qc.nginx }script/cloud/places.js"></script>
<script type="text/javascript">
_init_area(adds);
</script>
<script rootURL=${qc.rootPath } src="${qc.nginx }script/cloud/personal/settings.account.js"></script>
</body>
</html>
