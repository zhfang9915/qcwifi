var rootURL = $("script[rootURL]").attr('rootURL');

layui.use(['form', 'laydate', 'layer'], function() {
	var form = layui.form; //表单
	var layer = layui.layer;
	
	form.verify({
		limitConfim : function(value, item) { // value：表单的值、item：表单的DOM对象
			var day = $('input[name=dayLimit]').val();
			if (day > value) {
				return '总限额不能小于日展示限额';
			}
		},
		
		limit : function (value, item) {
			if (value && !/^[1-9]\d*$/.test(value)) {
				return '此处只允许输入数字';
			}
		}

	});
	
	$("#weight_range").ionRangeSlider({
        min: 1,
        max: 100,
        step: 1,
		type: 'single',
        hasGrid: !0,
		onFinish: function(obj){
			$('#weightHidden').val(obj.fromNumber);
		}
    });
	
	//新建广告主
	$('.create').on('click', function() {
		layer.open({
			type: 1,
			title: '新建广告主',
			area: ['420px', 'auto'],
			content: '<div class="layui-form-item">'
						+'<label class="layui-form-label">广告主名称</label>'
						+'<div class="layui-input-inline">'
							+'<input type="text" id="create_content_group_name" placeholder="请输入广告主名称，限30字" autocomplete="off" class="layui-input">'
						+'</div>'
						+'<span style="line-height:35px">'+($("#content_group option").length-1)+'/30</span>'
					+'</div>',
			btn: ['确定', '取消'], //可以无限个按钮
			yes: function(index, layero) {
				var groupName = $('#create_content_group_name').val();
				if (groupName == "") {
					return false;
				}
				$.ajax({
					type : "post",
					async : false,
					data : {'name': groupName},
					url : rootURL + "/push/content/group/create",
					success : function(r) {
						if(r['success']){
							$("#content_group").append('<option value="'+r['data'].id+'" selected="selected">'+r['data'].name+'</option>');  
							form.render('select');
			                $(layero).find("input").val('');
							layer.close(index);
						} else {
							layer.msg(result['msg'], function(){});
						}
					}
				});
			}
		});
	});
	
	//提交表单
	form.on('submit(submit-content-base)', function(data) {
		data.field.weight = $('#weightHidden').val();
		var id = $('#contentId').val();
		var load = layer.load(2);
    	$.ajax({
    		type : 'POST',  
    		data:jQuery.param(data.field),
    		dataType:'json',
	        url: rootURL + "push/content/update/"+id+"/step1",
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
		return false;
	});
});