$(function(){
	
})

var rootURL = $("script[rootURL]").attr('rootURL');
var rp = {
		existPermissionInit : function (roleId){
			$('#table_existPermission').bootstrapTable({
				url : rootURL + "role/allocation/exist/" + roleId,
				method : 'get',
				cache : false,
				search : true,
				sidePagination : "server",
				idField : "id",
				height : 500,
				columns : [ {
					checkbox : true
				}, {
					field : 'description',
					title : '已有权限'
				}]
			});
		},
		
		optionalPermissionInit : function (roleId){
			$('#table_optionalPermission').bootstrapTable({
				url : rootURL + "role/allocation/optional/" + roleId,
				method : 'get',
				cache : false,
				search : true,
				sidePagination : "server",
				idField : "id",
				height : 500,
				columns : [ {
					checkbox : true
				}, {
					field : 'description',
					title : '可选权限'
				}]
			});
		},
		
		correlationPermission : function (roleId){
			var optionals = $('#table_optionalPermission').bootstrapTable('getSelections');
			if (optionals.length>0) {
				var load = layer.load(2);
				$.ajax({
			        type: "post",     //提交方式
			        contentType: "application/json; charset=utf-8",   //内容类型
			        dataType: "json",     //类型
			        url: rootURL + "role/config/"+roleId+"/permission/correlation",    //提交的页面，方法名
			        data: JSON.stringify(optionals),    //参数
			        success : function(result) {
						if (result['success']) {
							$('#table_existPermission').bootstrapTable("refresh");
							$('#table_optionalPermission').bootstrapTable("refresh");
						}else{
							layer.msg(result['msg'], function(){});
						}
					},
					complete : function(){
						layer.close(load);
					}
			    });
			}else{
				layer.msg("请选择需要添加的权限");
			}
			return false;
		},
		
		uncorrelationPermission : function (roleId){
			var exists = $('#table_existPermission').bootstrapTable('getSelections');
			if (exists.length>0) {
				var load = layer.load(2);
				$.ajax({
					type: "post",     //提交方式
					contentType: "application/json; charset=utf-8",   //内容类型
					dataType: "json",     //类型
					url: rootURL + "role/config/"+roleId+"/permission/uncorrelation",    //提交的页面，方法名
					data: JSON.stringify(exists),    //参数
					success : function(result) {
						if (result['success']) {
							$('#table_existPermission').bootstrapTable("refresh");
							$('#table_optionalPermission').bootstrapTable("refresh");
						}else{
							layer.msg(result['msg'], function(){});
						}
					},
					complete : function(){
						layer.close(load);
					}
				});
			}else{
				layer.msg("请选择需要移除的权限");
			}
			return false;
		}
	
}