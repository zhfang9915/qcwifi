$(function(){
	//表格初始化
	permission.init();
	
	//点击搜索
	$("#permission-search").click(function(){
		$('#permission-table').bootstrapTable("refresh");
	});
	
	//点击创建权限
	$("#permission-create").click(function(){
		//显示弹出层
		$('#permission-create-modal').modal({
			show:true,//显示弹出层
            backdrop:'static',//禁止位置关闭
            keyboard:false//关闭键盘事件
		});
	});
	
	//创建权限
	$("#permission-create-submit").click(function(){
		var data = {};
		data.permission = $("#create-permission").val();
		if($.trim(data.permission) == ''){
			return layer.tips('权限标识不能为空', '#create-permission', {tips: [3, '#000']});
		}
		data.description = $("#create-description").val();
		if($.trim(data.description) == ''){
			return layer.tips('权限描述不能为空', '#create-description', {tips: [3, '#000']});
		}
		data.available = $('input:radio[name="create-available"]:checked').val();
		
		var load = layer.load(2);
		$.post(rootURL + "permission/create", data, function(result) {
			layer.close(load);
			if(result['success']){
				$('#permission-create-modal').modal("hide");
				$("#permission-create-form")[0].reset();
				$('#permission-table').bootstrapTable("refresh");
				layer.msg(result['data']);
			}else{
				layer.msg(result['msg'], function(){});
			}
		}, "json");
	});
	
	//保存更新
	$("#permission-edit-submit").click(function(){
		var data = {};
		data.id=$("#edit-id").val();
		data.permission = $("#edit-permission").val();
		if($.trim(data.permission) == ''){
			return layer.tips('权限标识不能为空', '#edit-permission', {tips: [3, '#000']});
		}
		data.description = $("#edit-description").val();
		if($.trim(data.description) == ''){
			return layer.tips('权限描述不能为空', '#edit-description', {tips: [3, '#000']});
		}
		data.available = $('input:radio[name="edit-available"]:checked').val();
		
		var load = layer.load(2);
		$.post(rootURL + "permission/update", data, function(result) {
			layer.close(load);
			if(result['success']){
				$('#permission-edit-modal').modal("hide");
				$('#permission-table').bootstrapTable("refresh");
				layer.msg(result['data']);
			}else{
				layer.msg(result['msg'], function(){});
			}
		}, "json");
	});
	
	//删除权限
	$("#permission-delete").click(function(){
		var arr = $('#permission-table').bootstrapTable('getSelections');
		if (arr.length>0) {
			layer.confirm('确定删除这'+arr.length+'个权限吗？', {
			    btn: ['确定删除','取消'], //按钮
			    shade: false //不显示遮罩
			}, function(){
				$.ajax({
			        type: "post",     //提交方式
			        contentType: "application/json; charset=utf-8",   //内容类型
			        dataType: "json",     //类型
			        url: rootURL + "permission/delete/batch",    //提交的页面，方法名
			        data: JSON.stringify(arr),    //参数
			        success : function(result) {
						if (result['success']) {
							$('#permission-table').bootstrapTable("refresh");
							layer.msg(result['data']);
						}else {
							layer.msg(result['msg'], function(){});
						}
					}
			    });
			}, function(){});
		}else{
			layer.msg("请选择要删除的数据", function(){});
		}
		return false;
	});
})

var rootURL = $("script[rootURL]").attr('rootURL');
var permission = {
	
	init : function() {
		$('#permission-table').bootstrapTable({
			url : rootURL + "permission/table", // 请求后台的URL（*）
			method : 'post', // 请求方式（*）
			queryParams : permission.queryParams,// 传递参数（*）
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			pageSize : 10, // 每页的记录行数（*）
			pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
			minimumCountColumns : 2, // 最少允许的列数
			toolbar : "#toolbar",
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			showExport : true,
			columns : [ {
                checkbox: true,
                align: 'center',
                valign: 'middle'
			}, {
				field : 'permission',
				title : '权限标识'
			}, {
				field : 'description',
				title : '权限描述'
			}, {
				field : 'available',
				align: 'center',
				title : '状态',
				formatter : function(value, row, index){
					if (value) {
						return '<span class="glyphicon glyphicon-ok text-success" aria-hidden="true"></span>';
					}else {
						return '<span class="glyphicon glyphicon-remove text-default" aria-hidden="true"></span>';
					}
				}
			}, {
				title : '操作',
				align: 'center',
				formatter : function(value, row, index){
					return [
			            '<button type="button" class="btn btn-link btn-xs" onClick="permission.editPermission('+row.id+')">编辑</button>',
						'<button type="button" class="btn btn-link btn-xs" onClick="permission.deletePermission('+row.id+')">删除</button>'
			        ].join('');
				}
			} ]
		});
	},
	
	/*表格查询参数*/
	queryParams : function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			pageNumber : this.pageNumber,  
            pageSize : this.pageSize, // 页码
			description : $("#search-text").val()//搜索内容
		};
		console.log(temp);
		return temp;
	},
	
	//删除权限
	deletePermission : function(id){
		layer.confirm('确定删除这个权限吗？', {
		    btn: ['确定删除','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(){
			var load = layer.load(2);
			$.post(rootURL + "permission/delete/" + id, function(result) {
				layer.close(load);
				if(result['success']){
					$('#permission-table').bootstrapTable("refresh");
					layer.msg(result['data']);
				}else{
					layer.msg(result['msg'], function(){});
				}
			}, "json");
		}, function(){});
	},
	
	//编辑权限
	editPermission : function(id){
		var load = layer.load(2);
		$.post(rootURL + "permission/search/" + id, function(result) {
			layer.close(load);
			if(result['success']){
				var p = result['data'];
				$("#edit-id").val(id);
				$("#edit-permission").val(p.permission);
				$("#edit-description").val(p.description);
				if(p.available){
					$("#edit-available-true").attr("checked","checked");
				}else{
					$("#edit-available-false").attr("checked","checked");
				}
				//显示弹出层
				$('#permission-edit-modal').modal({
					show:true,//显示弹出层
		            backdrop:'static',//禁止位置关闭
		            keyboard:false//关闭键盘事件
				});
			}else{
				layer.msg(result['msg'], function(){});
			}
		}, "json");
	}
}