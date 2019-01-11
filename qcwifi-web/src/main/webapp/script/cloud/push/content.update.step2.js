var rootURL = $("script[rootURL]").attr('rootURL');

layui.use([ 'form', 'laydate', 'upload' ], function() {
	var form = layui.form; // 表单
	var upload = layui.upload;

	upload.render({ // 上传图片
		elem : '#localImgUpload',
		accept: 'images',
		exts : 'jpg|png|gif|jpeg',
		size: 2048,//KB
		url : rootURL + "img/upload",
		before: function(obj){
		    layer.load();
		},
		done : function(res, index, upload) {
			console.log(res);
			if(res.success){
				$('#localImgUpload').after('<div class="upload_imgarea"><img src="'+res.data+'"><input type="radio" name="img_check"/></div>');
			}
			
			layer.closeAll('loading');
		},
		error : function() {
			// 请求异常回调
			layer.closeAll('loading');
		}
	});

	form.on('radio(area)',function(data) {
		$(this).parent().parent().parent().find('.layui-item-boxcont').toggle();
		if ($(this).parent().parent().parent().find('.layui-item-boxcont').is(":visible")) {
			$(this).parent().parent().parent().find('.btn-close').css('transform', 'rotate(180deg)')
		} else {
			$(this).parent().parent().parent().find('.btn-close').css('transform', 'rotate(0deg)')
		}
	});
	
	$('.h-cont').on('click', function() {
		var index = $(this).index();
		$(this).addClass('h-active').siblings().removeClass('h-active');
		$('.h-description').hide().eq(index).show();
		if($(this).attr('data-val')==3){
			$('#imgarea_more').css("display","inline");
		}else {
			$('#imgarea_more').hide();
		}
	});
	
	//上传图片
	$('.T10001 .imgarea').click(function() {
		var _this = $(this);
		var load = layer.load(2);
    	$.ajax({
    		type : 'POST',  
    		dataType:'json',
	        url: rootURL + "img/list",
	        success : function(result) {
				if (result['success']) {
					var imgs = '';
					$.each(result['data'], function(v, o) {
						imgs += '<div class="upload_imgarea"><img src="'+o+'"><input type="radio" name="img_check"/></div>';
					});
					$('#listImage').html(imgs);
					layer.open({
						type : 1,
						title : '上传图片',
						area : '780px',
						content : $('#T10001_upload_img'),
						btn : [ '确定', '取消' ],
						yes : function(index, layero) {
							var checkedImg = $('input[name=img_check]:checked').siblings('img').attr("src");
							_this.find('img').attr("src", checkedImg);
							layer.close(index);
						},
						closeBtn : 2,
						scrollbar: false
					});
				}else{
					layer.msg(result['msg'], function(){});
				}
			},
			complete : function(){
				layer.close(load);
			}
	    });
	});
	
	$('#uplpadimg').click(function() {
		if ($('#img-content .img-content-item').length >= 5) {
			layer.msg("最多可传5张图", function(){});
			return false;
		}
		var load = layer.load(2);
    	$.ajax({
    		type : 'POST',  
    		dataType:'json',
	        url: rootURL + "img/list",
	        success : function(result) {
				if (result['success']) {
					var imgs = '';
					$.each(result['data'], function(v, o) {
						imgs += '<div class="upload_imgarea"><img src="'+o+'"><input type="radio" name="img_check"/></div>';
					});
					$('#listImage').html(imgs);
					layer.open({
						type : 1,
						title : '上传图片',
						area : '780px',
						content : $('#T10001_upload_img'),
						btn : [ '确定', '取消' ],
						yes : function(index, layero) {
							var checkedImg = $('input[name=img_check]:checked').siblings('img').attr("src");
							var imgContent = '<div class="img-content-item">'
												+ '<i class="layui-icon">&#x1007;</i>'
												+ '<div class="item-list">'
													+ '<img src="'+checkedImg+'"/>'
													+ '<input type="text" name="content_url" placeholder="必填：广告跳转链接" autocomplete="off" class="layui-input"/>'
													+ '<p>*</p>'
												+ '</div>'
											+ '</div>';
							$("#img-content").append(imgContent);
							layer.close(index);
						},
						closeBtn : 2,
						scrollbar: false
					});
				}else{
					layer.msg(result['msg'], function(){});
				}
			},
			complete : function(){
				layer.close(load);
			}
	    });
	});
	
	//删除图片链接信息
	$('.layui-form-item').on('click', '.img-content-item i', function(){
		$(this).parent().remove();
	});
	
	//提交表单
	form.on('submit(submit-content-choose)', function(d) {
		var data = {};
		data.templateId = $('input[name=contentTemp]:checked').val();
		if(data.templateId=='T10001'){//登陆中海报
			var cont = $('.h-cont.h-active').attr('data-val');
			if (cont == 1) {//1x5
				data.imgs = $('.layui-item-boxcont .left-cont .imgarea').eq(0).find('img').attr('src');
				if (data.imgs == "") {
					layer.msg("请上传海报图片", function(){});
					return false;
				}
				submitForm(data);
			}else if (cont == 3) {
				data.imgs = "";
				var count = 0;
				$('.layui-item-boxcont .left-cont .imgarea').each(function(){
					var url = $(this).find('img').attr('src');
					if(url != ''){
						data.imgs += url + ",";
						count ++;
					} else {
						layer.msg("请上传海报图片", function(){});
						return false;
					}
					if(count>=$('.layui-item-boxcont .left-cont .imgarea').length) {
						submitForm(data);
					}
				});
			}
		} else if (data.templateId=='T10002') {//成功推广页链接
			data.urls = $('input[name=T10002_url]').val();
			if (data.urls == "") {
				layer.msg("请输入WIFI连接成功后跳转的页面地址", function(){});
				return false;
			}
			if (!isURL(data.urls)) {
				layer.msg("请输入正确的url地址", function(){});
				return false;
			}
			submitForm(data);
		} else if (data.templateId=='T10003') {//成功推广页链接
			data.imgs = "";
			data.urls = "";
			var count = 0;
			$('#img-content .img-content-item .item-list').each(function(){
				var img = $(this).find('img').attr('src');
				var url = $(this).find('input[name=content_url]').val();
				if(url != ''){
					data.imgs += img + ",";
					data.urls += url + ",";
					count ++;
				} else {
					layer.msg("请输入广告跳转链接", function(){});
					return false;
				}
				if (!isURL(url)) {
					layer.msg("请输入正确的url地址", function(){});
					return false;
				}
				if(count >= $('#img-content .img-content-item .item-list').length) {
					submitForm(data);
				}
			});
		}
		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
});

function submitForm(data) {
	var load = layer.load(2);
	var id = $("#contentId").val();
	$.ajax({
		type : 'POST',  
		data:jQuery.param(data),
		dataType:'json',
		async : false,
        url: rootURL + "push/content/update/"+id+"/step2",    //提交的页面，方法名
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
}

function isURL(url) {
	var strRegex = '^((https|http|ftp|rtsp|mms)?://)'
			+ '?(([0-9a-z_!~*\'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?' //ftp的user@ 
			+ '(([0-9]{1,3}.){3}[0-9]{1,3}' // IP形式的URL- 199.194.52.184 
			+ '|' // 允许IP和DOMAIN（域名） 
			+ '([0-9a-z_!~*\'()-]+.)*' // 域名- www. 
			+ '([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].' // 二级域名 
			+ '[a-z]{2,6})' // first level domain- .com or .museum 
			+ '(:[0-9]{1,4})?' // 端口- :80 
			+ '((/?)|' // a slash isn't required if there is no file name 
			+ '(/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+/?)$';
	var re = new RegExp(strRegex);
	if (re.test(url)) {
		return (true);
	} else {
		return (false);
	}
}