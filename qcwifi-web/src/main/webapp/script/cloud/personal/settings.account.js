var rootURL = $("script[rootURL]").attr('rootURL');

layui.use('form', function() {
	var form = layui.form;

	form.on('select(add-province)', function(data) {
		change(1);
		form.render('select', 'add-delivery-div');
	});
	form.on('select(add-city)', function(data) {
		change(2);
		form.render('select', 'add-delivery-div');
	});
	form.on('select(add-are)', function(data) {
		change(3);
		form.render('select', 'add-delivery-div');
	});
	
	//创建配送地址
	form.on('submit(add-delivery)', function(data) {
		var load = layer.load(2);
    	$.ajax({
    		type : 'POST',  
    		data:jQuery.param(data.field),
    		dataType:'json',
	        url: rootURL + "personal/center/account/delivery",    //提交的页面，方法名
	        success : function(result) {
				if (result['success']) {
					$('.layui-layer-page').hide();
			        $('.layui-layer-shade').hide();
			        //更新则删除原数据
			        if(data.field.id){
			        	$("#address" + data.field.id).remove();
			        }
			        //默认 则去除原默认图标
			        if(data.field.isDefault){
			        	$("#address-list").find("tr").each(function(){
			                $(this).find(".address-default").remove();
			                $(this).find("#aSetDefualt").show();
			            });
			        	$("#delivery_table tbody").prepend(showDelivery(result['data']));
			        }else{
			        	$("#delivery_table tbody").append(showDelivery(result['data']));
			        }
					
			        //重置表单
					$("#deliveryForm")[0].reset();
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

$(function(){
	//获取地址
	showAddressList();
	
	//打开更新昵称弹窗
    $('#updateName').click(function(){
        $('#layui-contact').show();
        $('.layui-layer-shade').show();
    });
    //关闭弹窗
    $('.layui-layer-close,.layui-btn-reset').click(function(){
        $('.layui-layer-page').hide();
        $('.layui-layer-shade').hide();
        
        //重置表单
		$("#deliveryForm")[0].reset();
    });
    //打开创建地址
    $('#addLoaction').click(function(){
    	if ($("#address-list").find("tr").length == 10) {
    		layer.msg('您已创建10个地址，最多可创建10个', function(){});
    		return;
    	}
        $('#layuiAddLoaction').show();
        $('.layui-layer-shade').show();
    });
    
    
    //更新昵称
    $("#updateNickName").click(function(){
    	var newNickName = $("#new_nickname").val();
    	var load = layer.load(2);
		$.post(rootURL + "personal/center/account/nickname", {'nickname':newNickName}, function(result) {
			layer.close(load);
			if(result['success']){
				$('.layui-layer-page').hide();
		        $('.layui-layer-shade').hide();
				$("#new_nickname").val('');
				$('#nickname').html(newNickName);
			}else{
				layer.msg(result['msg'], function(){});
			}
		}, "json");
    });
    
});
//删除地址
function deleteDelivery(addressId){
	var load = layer.load(2);
	$.ajax({
		type : 'DELETE',  
		dataType:'json',
        url: rootURL + "personal/center/account/delivery/" + addressId,    //提交的页面，方法名
        success : function(result) {
			if (result['success']) {
				$("#address" + addressId).remove();
				$("#address-count").html($("#address-list").find("tr").length);
			}else{
				layer.msg(result['msg'], function(){});
			}
		},
		complete : function(){
			layer.close(load);
		}
    });
}
//展示地址
function showAddressList(){
	var load = layer.load(2);
	$.ajax({
		type : 'GET',  
		dataType:'json',
        url: rootURL + "personal/center/account/delivery",    //提交的页面，方法名
        success : function(result) {
			if (result['success']) {
				var html = '';
				var alist = result['data'];
				for (var i = 0; i < alist.length; i++) {
					html += showDelivery(alist[i]);
				}
				$("#address-list").html(html);
				$("#address-count").html($("#address-list").find("tr").length);
			}else{
				$("#address-list").html('<tr><td colspan="5">'+result['msg']+'</td></tr>');
			}
		},
		complete : function(){
			layer.close(load);
		}
    });
}

//编辑地址
function editDelivery(obj){
	$('#layuiAddLoaction').show();
    $('.layui-layer-shade').show();
	$("input[name=id]").val(obj.id);
	$("input[name=receiver]").val(obj.receiver);
	$("input[name=phoneNum]").val(obj.phoneNum);
	$("select[name=province]").val(obj.province);
	change(1);
	$("select[name=city]").val(obj.city);
	change(2);
	$("select[name=area]").val(obj.area);
	$("input[name=address]").val(obj.address);
	$("input[name=postCode]").val(obj.postCode);
	if(obj.isDefault){
		$("input[name=isDefault]").attr("checked", true);
	}
	layui.form.render();
}

//设置默认
function setDefault(obj){
	obj.isDefault = true;
	var load = layer.load(2);
	$.ajax({
		type : 'POST',  
		dataType:'json',
        url: rootURL + "personal/center/account/delivery/default/" + obj.id,    //提交的页面，方法名
        success : function(result) {
			if (result['success']) {
				$("#address" + obj.id).remove();
				
	        	$("#address-list").find("tr").each(function(){
	                $(this).find(".address-default").remove();
	                $(this).find("#aSetDefualt").show();
	            });
	        	$("#delivery_table tbody").prepend(showDelivery(obj));
			}else{
				layer.msg(result['msg'], function(){});
			}
		},
		complete : function(){
			layer.close(load);
		}
    });
}


//地址表格展示
function showDelivery(obj){
	var html = '<tr id="address'+obj.id+'">'
				+ '<td>'+obj.receiver;
	if(obj.isDefault){
		html += '<span class="address-default">默认</span>';
	}
	html += '</td>'
		    + '<td>' + obj.province + obj.city + obj.area + obj.address + '</td>'
		    + '<td>'+obj.postCode+'</td>'
		    + '<td>'+obj.phoneNum+'</td>'
		    + '<td>'
		    	+ '<a href="javascript:;" onclick="editDelivery('+JSON.stringify(obj).split("\"").join("'")+')" class="td-link">修改</a>'
		        + '<a href="javascript:;" onclick="deleteDelivery('+obj.id+')" class="td-link">删除</a>'
		        + '<a id="aSetDefualt" href="javascript:;" onclick="setDefault('+JSON.stringify(obj).split("\"").join("'")+')" class="td-link"'; 
		if(obj.isDefault){ 
			html += 'style="display:none;"'
		}
	html +=  '>设置为默认地址</a></td>'
				+ '</tr>';
	return html;
}