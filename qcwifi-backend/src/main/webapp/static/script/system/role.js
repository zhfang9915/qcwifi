$(function(){
	//表格初始化
	role.init();
	
	//点击搜索
	$("#role-search").click(function(){
		$('#role-table').bootstrapTable("refresh");
	});
	
	//点击创建角色
	$("#role-create").click(function(){
		//显示弹出层
		$('#role-create-modal').modal({
			show:true,//显示弹出层
            backdrop:'static',//禁止位置关闭
            keyboard:false//关闭键盘事件
		});
	});
	
	//创建角色
	$("#role-create-submit").click(function(){
		var data = {};
		data.role = $("#create-role").val();
		if($.trim(data.role) == ''){
			return layer.tips('角色标识不能为空', '#create-role', {tips: [3, '#000']});
		}
		data.description = $("#create-description").val();
		if($.trim(data.description) == ''){
			return layer.tips('角色描述不能为空', '#create-description', {tips: [3, '#000']});
		}
		data.available = $('input:radio[name="create-available"]:checked').val();
		
		var load = layer.load(2);
		$.post(rootURL + "role/create", data, function(result) {
			layer.close(load);
			if(result['success']){
				$('#role-create-modal').modal("hide");
				$("#role-create-form")[0].reset();
				$('#role-table').bootstrapTable("refresh");
				layer.msg(result['data']);
			}else{
				layer.msg(result['msg'], function(){});
			}
		}, "json");
	});
	
	//保存更新
	$("#role-edit-submit").click(function(){
		var data = {};
		data.id=$("#edit-id").val();
		data.role = $("#edit-role").val();
		if($.trim(data.role) == ''){
			return layer.tips('角色标识不能为空', '#edit-role', {tips: [3, '#000']});
		}
		data.description = $("#edit-description").val();
		if($.trim(data.description) == ''){
			return layer.tips('角色描述不能为空', '#edit-description', {tips: [3, '#000']});
		}
		data.available = $('input:radio[name="edit-available"]:checked').val();
		
		var load = layer.load(2);
		$.post(rootURL + "role/update", data, function(result) {
			layer.close(load);
			if(result['success']){
				$('#role-edit-modal').modal("hide");
				$('#role-table').bootstrapTable("refresh");
				layer.msg(result['data']);
			}else{
				layer.msg(result['msg'], function(){});
			}
		}, "json");
	});
	
	//删除角色
	$("#role-delete").click(function(){
		var arr = $('#role-table').bootstrapTable('getSelections');
		if (arr.length>0) {
			var cd = layer.confirm('确定删除这'+arr.length+'个角色吗？', {
			    btn: ['确定删除','取消'], //按钮
			    shade: false //不显示遮罩
			}, function(){
				layer.close(cd);
				var load;
				$.ajax({
			        type: "post",     //提交方式
			        contentType: "application/json; charset=utf-8",   //内容类型
			        dataType: "json",     //类型
			        url: rootURL + "role/delete/batch",    //提交的页面，方法名
			        data: JSON.stringify(arr),    //参数
			        beforeSend : function(){
			        	load = layer.load(2);
			        },
			        success : function(result) {
						if (result['success']) {
							$('#role-table').bootstrapTable("refresh");
							layer.msg(result['data']);
						}else {
							layer.msg(result['msg'], function(){});
						}
					},
					complete : function(){
						layer.close(load);
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
var role = {
	
	init : function() {
		$('#role-table').bootstrapTable({
			url : rootURL + "role/table", // 请求后台的URL（*）
			method : 'post', // 请求方式（*）
			queryParams : role.queryParams,// 传递参数（*）
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
				field : 'role',
				title : '角色标识'
			}, {
				field : 'description',
				title : '角色描述'
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
			            '<button type="button" class="btn btn-link btn-xs" onClick="role.configPermission('+row.id+')">权限分配</button>',
			            '<button type="button" class="btn btn-link btn-xs" onClick="role.editPermission('+row.id+')">编辑</button>',
						'<button type="button" class="btn btn-link btn-xs" onClick="role.deletePermission('+row.id+')">删除</button>'
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
	
	//删除角色
	deletePermission : function(id){
		layer.confirm('确定删除这个角色吗？', {
		    btn: ['确定删除','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(){
			var load = layer.load(2);
			$.post(rootURL + "role/delete/" + id, function(result) {
				layer.close(load);
				if(result['success']){
					$('#role-table').bootstrapTable("refresh");
					layer.msg(result['data']);
				}else{
					layer.msg(result['msg'], function(){});
				}
			}, "json");
		}, function(){});
	},
	
	//编辑角色
	editPermission : function(id){
		var load = layer.load(2);
		$.post(rootURL + "role/search/" + id, function(result) {
			layer.close(load);
			if(result['success']){
				var p = result['data'];
				$("#edit-id").val(id);
				$("#edit-role").val(p.role);
				$("#edit-description").val(p.description);
				if(p.available){
					$("#edit-available-true").attr("checked","checked");
				}else{
					$("#edit-available-false").attr("checked","checked");
				}
				//显示弹出层
				$('#role-edit-modal').modal({
					show:true,//显示弹出层
		            backdrop:'static',//禁止位置关闭
		            keyboard:false//关闭键盘事件
				});
			}else{
				layer.msg(result['msg'], function(){});
			}
		}, "json");
	},
	

	configPermission : function(id) {
		var index = layer.open({
			type : 2,
			title : '权限分配',
			content : rootURL + 'role/allocation/' + id,
			maxmin : true,
			anim:3
		});
		layer.full(index);
	}
	
}