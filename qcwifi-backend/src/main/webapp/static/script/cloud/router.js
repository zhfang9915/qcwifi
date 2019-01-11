var rootURL = $("script[rootURL]").attr('rootURL');
var router = {
		/*表格查询参数*/
		queryParams : function(params) {
			var temp = { 
				pageNumber : this.pageNumber,  
                pageSize : this.pageSize
			};
			return temp;
		}
	
		
}

$(function() {
		$('#router-table').bootstrapTable({
			url : rootURL + "router/table", // 请求后台的URL（*）
			method : 'post', // 请求方式（*）
			queryParams : router.queryParams,// 传递参数（*）
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1,
			pageSize : 15, // 每页的记录行数（*）
			pageList : [ 15, 25, 50, 100 ], // 可供选择的每页的行数（*）
			minimumCountColumns : 2, // 最少允许的列数
			toolbar : "#toolbar",
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			showExport : true,
			columns : 
			[ {
				field : 'devNo',
				title : '设备编码'
			}, {
				field : 'mac',
				title : 'MAC'
			}, {
				field : 'version',
				title : '版本'
			}, {
				field : 'model',
				title : '型号'
			}, {
				field : 'fwv',
				title : '固件版本'
			}, {
				field : 'gccv',
				title : '交叉编译版本'
			}, {
				field : 'pv',
				title : '插件版本'
			}, {
				field : 'shopId',
				title : '绑定商铺ID'
			}, {
				field : 'createBy',
				title : '所属账号'
			} ]
		});
		
		//点击搜索
		$("#router-search").click(function(){
			$('#router-table').bootstrapTable("refresh");
		});


});