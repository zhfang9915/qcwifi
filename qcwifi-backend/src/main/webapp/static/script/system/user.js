$(function(){
	//表格初始化
	user.init();
	
	//点击搜索
	$("#user-search").click(function(){
		$('#user-table').bootstrapTable("refresh");
	});
	
	//点击创建会员
	$("#user-create").click(function(){
		//显示弹出层
		$('#user-create-modal').modal({
			show:true,//显示弹出层
            backdrop:'static',//禁止位置关闭
            keyboard:false//关闭键盘事件
		});
	});
	
	//创建会员
	$("#user-create-submit").click(function(){
		var data = {};
		data.username = $.trim($("#create-username").val());
		if(data.username == ''){
			return layer.tips('会员名不能为空', '#create-username', {tips: [3, '#000']});
		}
		$.post(rootURL + "user/validation/" + data.username, function(result) {
			if(result['success']){
				return layer.tips(data.username+'已被注册使用，请更换', '#create-username', {tips: [3, '#000']});
			}
		}, "json");
		data.nickname = $.trim($("#create-nickname").val());
		if(data.nickname == ''){
			return layer.tips('会员昵称不能为空', '#create-nickname', {tips: [3, '#000']});
		}
		data.password = $.trim($("#create-password").val());
		if(data.password == ''){
			return layer.tips('登录密码不能为空', '#create-password', {tips: [3, '#000']});
		}
		if(data.password.length < 9){
			return layer.tips('登录密码长度不符合要求', '#create-password', {tips: [3, '#000']});
		}
		var passwordr = $.trim($("#create-passwordr").val());
		if(passwordr == ''){
			return layer.tips('重复登录密码不能为空', '#create-passwordr', {tips: [3, '#000']});
		}
		if(data.password != passwordr){
			return layer.tips('两次密码不一致', '#create-passwordr', {tips: [3, '#000']});
		}
		data.locked = $('input:radio[name="create-locked"]:checked').val();
		
		var load = layer.load(2);
		$.post(rootURL + "user/create", data, function(result) {
			layer.close(load);
			if(result['success']){
				$('#user-create-modal').modal("hide");
				$("#user-create-form")[0].reset();
				$('#user-table').bootstrapTable("refresh");
				layer.msg(result['data']);
			}else{
				layer.msg(result['msg'], function(){});
			}
		}, "json");
	});
	
	//保存更新
	$("#user-edit-submit").click(function(){
		var data = {};
		data.id=$("#edit-id").val();
		data.nickname = $("#edit-nickname").val();
		if($.trim(data.nickname) == ''){
			return layer.tips('会员昵称不能为空', '#edit-nickname', {tips: [3, '#000']});
		}
		data.locked = $('input:radio[name="edit-locked"]:checked').val();
		
		var load = layer.load(2);
		$.post(rootURL + "user/update", data, function(result) {
			layer.close(load);
			if(result['success']){
				$('#user-edit-modal').modal("hide");
				$('#user-table').bootstrapTable("refresh");
				layer.msg(result['data']);
			}else{
				layer.msg(result['msg'], function(){});
			}
		}, "json");
	});
	
	//删除会员
	$("#user-delete").click(function(){
		var arr = $('#user-table').bootstrapTable('getSelections');
		if (arr.length>0) {
			var cd = layer.confirm('确定删除这'+arr.length+'个会员吗？', {
			    btn: ['确定删除','取消'], //按钮
			    shade: false //不显示遮罩
			}, function(){
				layer.close(cd);
				var load;
				$.ajax({
			        type: "post",     //提交方式
			        contentType: "application/json; charset=utf-8",   //内容类型
			        dataType: "json",     //类型
			        url: rootURL + "user/delete/batch",    //提交的页面，方法名
			        data: JSON.stringify(arr),    //参数
			        beforeSend : function(){
			        	load = layer.load(2);
			        },
			        success : function(result) {
						if (result['success']) {
							$('#user-table').bootstrapTable("refresh");
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
	
	$("#user-changepwd-submit").click(function(){
		var data = {};
		data.oldpwd = $("#change-old-pwd").val();
		if($.trim(data.oldpwd) == ''){
			return layer.tips('原密码不能为空', '#change-old-pwd', {tips: [3, '#000']});
		}
		data.newpwd = $("#change-new-pwd").val();
		if($.trim(data.newpwd) == ''){
			return layer.tips('新密码不能为空', '#change-new-pwd', {tips: [3, '#000']});
		}
		data.newpwdr = $("#change-new-pwdr").val();
		if($.trim(data.newpwdr) == ''){
			return layer.tips('重复新密码不能为空', '#change-new-pwdr', {tips: [3, '#000']});
		}
		if(data.newpwd != data.newpwdr){
			return layer.tips('两次密码不一致', '#change-new-pwdr', {tips: [3, '#000']});
		}
		data.id = $("#change-id").val();
		
		var load = layer.load(2);
		$.post(rootURL + "user/changepwd", data, function(result) {
			layer.close(load);
			if(result['success']){
				$('#user-changepwd-modal').modal("hide");
				$("#user-changepwd-form")[0].reset();
				layer.msg(result['data']);
			}else{
				layer.msg(result['msg'], function(){});
			}
		}, "json");
	});
	
	//失去焦点
	$("#create-username").blur(function(){
		var username = $(this).val();
		$.post(rootURL + "user/validation/" + username, function(result) {
			if(result['success']){
				layer.tips(username+'已被注册使用，请更换', '#create-username', {tips: [3, '#000']});
			}
		}, "json");
	});
	
	
})

var rootURL = $("script[rootURL]").attr('rootURL');
var user = {
	
	init : function() {
		$('#user-table').bootstrapTable({
			url : rootURL + "user/table", // 请求后台的URL（*）
			method : 'post', // 请求方式（*）
			queryParams : user.queryParams,// 传递参数（*）
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
				field : 'id',
				title : 'ID'
			}, {
				field : 'username',
				title : '会员名'
			}, {
				field : 'nickname',
				title : '昵称'
			}, {
				field : 'locked',
				align: 'center',
				title : '状态',
				formatter : function(value, row, index){
					if (!value) {
						return '<span class="glyphicon glyphicon-ok text-success" aria-hidden="true"></span>';
					}else {
						return '<span class="glyphicon glyphicon-remove text-default" aria-hidden="true"></span>';
					}
				}
			}, {
				field : 'lastLoginTime',
				title : '最近登录时间'
			}, {
				field : 'createTime',
				title : '注册时间'
			}, {
				title : '操作',
				align: 'center',
				formatter : function(value, row, index){
					return [
			            '<button type="button" class="btn btn-link btn-xs" onClick="user.configRole('+row.id+')">角色分配</button>',
			            '<button type="button" class="btn btn-link btn-xs" onClick="user.changePwd('+row.id+')">修改密码</button>',
			            '<button type="button" class="btn btn-link btn-xs" onClick="user.editUser('+row.id+')">编辑</button>',
						'<button type="button" class="btn btn-link btn-xs" onClick="user.deleteUser('+row.id+')">删除</button>'
			        ].join('');
				}
			} ]
		});
	},
	
	/*表格查询参数*/
	queryParams : function(params) {
		var temp = {
			pageNumber : this.pageNumber,  
            pageSize : this.pageSize,
			username : $("#search-username").val(),
			nickname : $("#search-nickname").val(),
			locked : $("#search-locked").val()
		};
		return temp;
	},
	
	//删除会员
	deleteUser : function(id){
		layer.confirm('确定删除这个会员吗？', {
		    btn: ['确定删除','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(){
			var load = layer.load(2);
			$.post(rootURL + "user/delete/" + id, function(result) {
				layer.close(load);
				if(result['success']){
					$('#user-table').bootstrapTable("refresh");
					layer.msg(result['data']);
				}else{
					layer.msg(result['msg'], function(){});
				}
			}, "json");
		}, function(){});
	},
	
	//编辑会员
	editUser : function(id){
		var load = layer.load(2);
		$.post(rootURL + "user/search/" + id, function(result) {
			layer.close(load);
			if(result['success']){
				var u = result['data'];
				$("#edit-id").val(id);
				$("#edit-nickname").val(u.nickname);
				if(!u.locked){
					$("#edit-locked-true").attr("checked","checked");
				}else{
					$("#edit-locked-false").attr("checked","checked");
				}
				//显示弹出层
				$('#user-edit-modal').modal({
					show:true,//显示弹出层
		            backdrop:'static',//禁止位置关闭
		            keyboard:false//关闭键盘事件
				});
			}else{
				layer.msg(result['msg'], function(){});
			}
		}, "json");
	},
	
	//角色分配
	configRole : function(id) {
		var index = layer.open({
			type : 2,
			title : '角色分配',
			content : rootURL + 'user/allocation/' + id,
			maxmin : true,
			anim:3
		});
		layer.full(index);
	},
	
	//修改密码
	changePwd : function(id){
		$("#change-id").val(id);
		//显示弹出层
		$('#user-changepwd-modal').modal({
			show:true,//显示弹出层
            backdrop:'static',//禁止位置关闭
            keyboard:false//关闭键盘事件
		});
	}
	
	
}