var rootURL = $("script[rootURL]").attr('rootURL');
var load;

$(function() {
	// 企业上传照片
    var tmpl = '<li class="weui-uploader__file" style="background-image:url(#url#)"><i class="file-close"></i></li>',
    $uploaderInput = $("#uploaderInput"),
    $uploaderFiles = $("#uploaderFiles")
    $uploaderInput.on("change", function(e) {
    	$uploaderFiles.find('li').remove();
        var src, url = window.URL || window.webkitURL || window.mozURL, files = e.target.files;
        for (var i = 0, len = files.length; i < len; ++i) {
            var file = files[i];
            
            if (url) {
                src = url.createObjectURL(file);
            } else {
                src = e.target.result;
            }
            console.log(file);
            $uploaderFiles.append($(tmpl.replace('#url#', src)));
        }
	});
    
    $uploaderFiles.on("click", ".file-close", function() {
  		$(this).parent('li').remove();
  	});
      
	// 切换
	$('.layui-form-item').on('click', '.layui-form-radio', function(event) {
		if ($(this).find('span').text()=="企业") {		  
			$('#company-label').css("display", "block");
			$('#personal-label').css("display", "none");
			$('#company-prompt-div').css("display", "block");
			$('#personal-prompt-div').css("display", "none");
			$('#name').attr("name","company");
			
			$('#idCard-div').css('display','none');
			$('#idCard').removeAttr("lay-verify","required");
			
			$('#company-pic-label').css("display", "block");
			$('#personal-pic-label').css("display", "none");
			$('#company-pic-prompt').css("display", "block");
			$('#personal-pic-prompt').css("display", "none");
			
			$('#company-ower-div').css("display", "block");
			$('#companyOwer').attr("name", "realName");
			$('#companyOwer').attr("lay-verify","required");
			$('#company-phone-div').css("display", "block");
			$('#phoneNum').attr("lay-verify","required");
		}
		else if ($(this).find('span').text()=="个人") {	  
			$('#company-label').css("display", "none");
			$('#personal-label').css("display", "block");
			$('#company-prompt-div').css("display", "none");
			$('#personal-prompt-div').css("display", "block");
			$('#name').attr("name","realName");
			
			$('#idCard-div').css('display','block');
			$('#idCard').attr("lay-verify","required");
			
			$('#company-pic-label').css("display", "none");
			$('#personal-pic-label').css("display", "block");
			$('#company-pic-prompt').css("display", "none");
			$('#personal-pic-prompt').css("display", "block");
			
			$('#company-ower-div').css("display", "none");
			$('#companyOwer').removeAttr("name", "realName");
			$('#companyOwer').removeAttr("lay-verify","required");
			$('#company-phone-div').css("display", "none");
			$('#phoneNum').removeAttr("lay-verify","required");
		}
	});
});

layui.use('form', function() {
	var form = layui.form;

	form.on('select(add-province)', function(data) {
		change(1);
		form.render('select', 'user-info-div');
	});
	form.on('select(add-city)', function(data) {
		change(2);
		form.render('select', 'user-info-div');
	});
	form.on('select(add-area)', function(data) {
		change(3);
		form.render('select', 'user-info-div');
	});
	
	form.on('submit(submit-info)', function(data) {
		// 同意协议
		if (!$('#agreeProtocol').is(':checked')) {
			layer.msg("请先阅读并且遵守《小楼无线账户资料认证服务协议》", function(){});
			return false;
		}
		
		load = layer.load(2);
		
		var data = new FormData($("#userInfoForm")[0]);
		
		var xhr = new XMLHttpRequest();
		xhr.open("POST", rootURL + "personal/center/userInfoAuth/index", true);
		xhr.upload.addEventListener("progress", null, false);
		xhr.addEventListener("load", submitComplete, false);
		xhr.addEventListener("error", submitFailed, false);
		xhr.send(data);
		
		// 阻止表单跳转。如果需要表单跳转，去掉这段即可。
		return false;
	});
});

function submitComplete(event) {
	layer.close(load);
	var responseText = event.target.responseText;
	console.log("hhhh = " + responseText)
	var result = JSON.parse(responseText);
	if (result['success']) {
		window.location.reload();
	} else {
		layer.msg(result['msg'], function(){});
	}
}

function submitFailed(event) {
	layer.close(load);
}
