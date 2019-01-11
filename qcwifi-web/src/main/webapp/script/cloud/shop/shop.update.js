var rootURL = $("script[rootURL]").attr('rootURL');

// 百度地图API功能
var map = new BMap.Map("shopMap");
var point = new BMap.Point($('#hidden_lng').val(), $('#hidden_lat').val());
map.centerAndZoom(point, 15);

change(1);
getOperate(1, 0);

layui.use('form', function() {
	var form = layui.form;
	//初始化
	$("select[name=province]").val($('#hidden_province').val());
	console.log($('#hidden_province').val());
	change(1);
	$("select[name=city]").val($('#hidden_city').val());
	change(2);
	$("select[name=area]").val($('#hidden_area').val());
	$("select[name=primaryCategory]").val($('#hidden_primaryCategory').val());
	getOperate(2, $('#primaryCategory').val());
	$("select[name=secondaryCategory]").val($('#hidden_secondaryCategory').val());
	form.render();
	idx();
	
	
	form.on('select(province)', function(data) {
		clearMap();
		change(1);
		form.render('select', 'create-shop-form');
	});
	form.on('select(city)', function(data) {
		clearMap();
		change(2);
		form.render('select', 'create-shop-form');
	});
	form.on('select(area)', function(data) {
		clearMap();
		form.render('select', 'create-shop-form');
	});
	form.on('select(primaryCategory)', function(data) {
		getOperate(2, $('#primaryCategory').val());
		form.render('select', 'create-shop-form');
	});
	
	form.on('submit(submit-shop-info)', function(data) {
		var mark = "";
		$("input:checkbox[name='mark']:checked").each(function() {
			mark += $(this).val() + ",";
		});
		data.field.mark = mark.substring(0, mark.length - 1);
		data.field.shopId = $("#hidden_shopId").val();
		var load = layer.load(2);
    	$.ajax({
    		type : 'POST',  
    		data:jQuery.param(data.field),
    		dataType:'json',
	        url: rootURL + "shop/info/update/" + data.field.shopId,    //提交的页面，方法名
	        success : function(result) {
				if (result['success']) {
					location.href=result['data'];
				}else{
					layer.msg(result['msg'], function(){});
				}
			},
			complete : function(){
				layer.close(load);
			}
	    });
		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
});

// 创建地址解析器实例
var myGeo = new BMap.Geocoder();
var searchEvent = null;

$("#address").keyup(function(evt) {
	var keyCode = evt.keyCode;
	if (keyCode != 13 && keyCode != 38 && keyCode != 40) {
		if (searchEvent != null) {
			clearTimeout(searchEvent);
			searchEvent = null;
		}
		searchEvent = setTimeout("idx()", 500);
	}
});

function clearMap() {
	$("#address").val('');
	$('#lng').val('');
	$('#lat').val('');
	map.clearOverlays(); // 清除地图上所有覆盖物
}

function idx() {
	var province = $('#province').val();
	var city = $('#city').val();
	var area = $('#area').val();
	var address = $('#address').val();
	// 将地址解析结果显示在地图上,并调整地图视野
	myGeo.getPoint(address, function(point) {
		if (point) {
			$('#lng').val(point.lng);
			$('#lat').val(point.lat);
			map.clearOverlays(); // 清除地图上所有覆盖物
			map.centerAndZoom(point, 16);
			map.addOverlay(new BMap.Marker(point, {
				icon : new BMap.Icon("http://lbsyun.baidu.com/jsdemo/img/fox.gif", new BMap.Size(200, 100))// 自定义图标
			}));
		}
	}, province + city + area);
}

function getOperate(l, p){
	$.ajax({
		type : 'POST',  
		data:{'level':l, 'parent':p},
		dataType:'json',
		async:false,
        url: rootURL + "shop/operate",
        success : function(result) {
			if (result['success']) {
				var options = '<option value="" disabled selected>请选择</option>';
				$.each(result['data'], function(v, o) {
					options += '<option value="'+o.id+'">'+o.name+'</option>';
				});
				if(p == 0){
					$('#primaryCategory').html(options);
				}else{
					$('#secondaryCategory').html(options);
				}
			}else{
				layer.msg(result['msg'], function(){});
			}
		}
    });
}
