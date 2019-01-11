$(function(){
	
	$(document).ajaxStart(function(){
		common.blockUI();
    });
	$(document).ajaxStop($.unblockUI);
	
});

var rootPath = $("script[rootPath]").attr('rootPath');

var common = {
		URL:{
	        randCode:function(){
	            return rootPath + "base/randcode?" + (new Date()).valueOf();
	        }
	    },
	    
	    //刷新验证码
		clickImage : function() {
			$(".img-randconde").attr("src", common.URL.randCode());
		},
	    
	    blockUI : function(){
	    	$.blockUI({
	        	message:'<h4><img src="' + rootPath + 'img/loading.gif" /><br/><br/>正在处理，请稍候...</h4>',
	            overlayCSS: {
	                opacity:"0"
	            },
	            css: { 
	            	border: 'none', 
	            	backgroundColor:'none'
	            }
	        });
	    }
	    
};