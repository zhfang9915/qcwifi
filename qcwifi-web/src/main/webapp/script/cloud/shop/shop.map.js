var rootURL = $("script[rootURL]").attr('rootURL');
var staticURL = $("script[staticURL]").attr('staticURL');

//百度地图API功能
var map = new BMap.Map("shopsMap");
var point = new BMap.Point(121.4828866392, 31.2354794453);
//缩略图
var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT});

$(function(){
	map.centerAndZoom(point, 12);
	map.enableScrollWheelZoom();//开启滚轮缩放效果
	map.enableInertialDragging();//开启惯性拖拽效果
	map.enableContinuousZoom();//开启连续缩放效果
	map.addControl(top_right_navigation);
	
	getShopInfo();
});

//浏览器定位
function setLocalCity(cityName){
	map.setCenter(cityName);
}

// 编写自定义函数,创建标注
function addMarker(shop){
	var point = new BMap.Point(shop.lng, shop.lat);
  var cusIcon = new BMap.Icon("http://lbsyun.baidu.com/jsdemo/img/fox.gif", new BMap.Size(300,157));//自定义图标
  var marker = new BMap.Marker(point,{icon:cusIcon});
  
  //消息面板
  var searchInfoWindow = null;
  var content = '<div style="margin:0;line-height:20px;padding:2px;">' +
                '<img src="http://lbsyun.baidu.com/jsdemo/img/fox.gif" alt="" style="float:right;zoom:1;overflow:hidden;width:100px;height:100px;margin-left:3px;"/>' +
                '地址：' + shop.province + shop.city + shop.area + shop.address + 
                '<br/>手机：'+ shop.phone + 
              '</div>';
	searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
			title  : shop.shopName,      //标题
			width  : 290,             //宽度
			height : 105,              //高度
			panel  : "panel",         //检索结果面板
			enableSendToPhone:false,
			enableAutoPan : true,     //自动平移
			searchTypes   :[
				BMAPLIB_TAB_SEARCH,   //周边检索
				BMAPLIB_TAB_TO_HERE,  //到这里去
				BMAPLIB_TAB_FROM_HERE //从这里出发
			]
		});
	marker.addEventListener("click", function(e){
		searchInfoWindow.open(marker);
	})
  map.addOverlay(marker);
}


/**
 * 获取商铺，向地图洒点
 * @returns
 */
function getShopInfo(){
	var load = layer.load(2);
	$.ajax({
		type : "post",
		async : false,
		url : rootURL + "shop/map",
		success : function(result) {
			map.clearOverlays();    //清除地图上所有覆盖物
			console.log(result);
			if(result['success']){
				var shops = result['data'];
				new BMap.LocalCity().get(setLocalCity(shops[0].province+shops[0].city+shops[0].area));//定位到第一个商铺的位置
				for (var i = 0; i < shops.length; i++) {
					addMarker(shops[i]);
				}
			}else{
				layer.msg(result['msg'], function(){});
			}
		},
		complete : function(){
			layer.close(load);
		}
	});
}