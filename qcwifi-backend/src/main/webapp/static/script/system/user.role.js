$(function(){
	
})

var rootURL = $("script[rootURL]").attr('rootURL');
var ur = {
		existRoleInit : function (userId){
			$('#table_existRole').bootstrapTable({
				url : rootURL + "user/allocation/exist/" + userId,
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
					title : '已有角色'
				}]
			});
		},
		
		optionalRoleInit : function (userId){
			$('#table_optionalRole').bootstrapTable({
				url : rootURL + "user/allocation/optional/" + userId,
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
					title : '可选角色'
				}]
			});
		},
		
		correlationRole : function (userId){
			var optionals = $('#table_optionalRole').bootstrapTable('getSelections');
			if (optionals.length>0) {
				$.ajax({
			        type: "post",     //提交方式
			        contentType: "application/json; charset=utf-8",   //内容类型
			        dataType: "json",     //类型
			        url: rootURL + "user/config/"+userId+"/role/correlation",    //提交的页面，方法名
			        data: JSON.stringify(optionals),    //参数
			        success : function(result) {
						if (result['success']) {
							$('#table_existRole').bootstrapTable("refresh");
							$('#table_optionalRole').bootstrapTable("refresh");
						}else{
							layer.msg(result['msg'], function(){});
						}
					}
			    });
			}else{
				layer.msg("请选择需要添加的角色");
			}
			return false;
		},
		
		uncorrelationRole : function (userId){
			var exists = $('#table_existRole').bootstrapTable('getSelections');
			if (exists.length>0) {
				$.ajax({
					type: "post",     //提交方式
					contentType: "application/json; charset=utf-8",   //内容类型
					dataType: "json",     //类型
					url: rootURL + "user/config/"+userId+"/role/uncorrelation",    //提交的页面，方法名
					data: JSON.stringify(exists),    //参数
					success : function(result) {
						if (result['success']) {
							$('#table_existRole').bootstrapTable("refresh");
							$('#table_optionalRole').bootstrapTable("refresh");
						}else{
							layer.msg(result['msg'], function(){});
						}
					}
				});
			}else{
				layer.msg("请选择需要移除的角色");
			}
			return false;
		}
	
}