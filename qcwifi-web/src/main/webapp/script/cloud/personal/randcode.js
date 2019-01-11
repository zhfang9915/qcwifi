//刷新验证码
function clickImage() {
	$(".img-randconde").attr("src", rootURL + "base/randcode?" + (new Date()).valueOf());
}

//开始倒计时  
var countdown;  
function settime(obj) {   
	countdown=getCookieValue("secondsremained");  
	if (countdown == 0) {   
		obj.removeAttr("disabled");      
		obj.val("发送验证码");   
		return;  
	} else {   
		obj.attr("disabled", true);   
		obj.val("重新发送(" + countdown + ")");   
		countdown--;  
		editCookie("secondsremained",countdown,countdown+1);  
	}   
	setTimeout(function() { settime(obj) },1000)
}   
//校验手机号是否合法  
function isPhone(phone){
	var preg=/^(((1[34578][0-9]{1}))+\d{8})$/;
	if(preg.test(phone)==false){
		alertMsg("手机号输入错误");
		return false;
	}
	return true;
}

//发送验证码时添加cookie  
function addCookie(name,value,expiresHours){   
	var cookieString=name+"="+escape(value);   
	if(expiresHours>0){   
		var date=new Date();   
		date.setTime(date.getTime()+expiresHours*1000);   
		cookieString=cookieString+";expires=" + date.toUTCString();   
	}   
	document.cookie=cookieString;   
}   
function editCookie(name,value,expiresHours){   
	var cookieString=name+"="+escape(value);   
	if(expiresHours>0){   
		var date=new Date();   
		date.setTime(date.getTime()+expiresHours*1000);
		cookieString=cookieString+";expires=" + date.toGMTString();   
	}   
	document.cookie=cookieString;   
}   
function getCookieValue(name){   
	var strCookie=document.cookie;   
	var arrCookie=strCookie.split("; ");   
	for(var i=0;i<arrCookie.length;i++){   
		var arr=arrCookie[i].split("=");   
		if(arr[0]==name){  
			return unescape(arr[1]);  
			break;  
		}else{  
			return "";   
			break;  
		}   
	}   
}