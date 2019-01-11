// JavaScript Document
$(document).ready(function () { 

	loadPage();
	 
	// $('.navbar-left .order-class .ord_li').click(function(){
	// 	$(this).parent().find('.second-nav').slideToggle();
	// });
	//left nav
	$('.navbar-left .order-class .ord_li').click(function(){
		if($(this).parent().find('.second-nav').is(':hidden')){
			$(this).parent().find('.second-nav').slideDown();
			// $(this).find('i').removeClass('fa-caret-right').addClass('fa-caret-down');
			$(this).parent().siblings().find('.second-nav').slideUp();
		}else{
			$(this).parent().find('.second-nav').slideUp();

		}
	});


	$('ul.second-nav li').click(function() {
		if($(this).find('.third-nav').is(':hidden')){
			$(this).find('.third-nav').slideDown();
			$(this).find('a').first().css('background','#34366f');
		}else{
			$(this).find('.third-nav').slideUp();
			$(this).find('a').first().css('background','#34366f');
		}
	});
	
	$('.navbar-left .topbtn').click(function(){
		var _W=$("body").width();
		if ($('.navbar-left .topbtn').hasClass("cls"))
		{
			$('.topbtn i').css('transform','rotate(360deg)');
			$('.navbar-left .topbtn').addClass("see");
			$('.navbar-left .topbtn').removeClass("cls");
			$('.navbar-left').css("width","180px");
			$('.navbar-left').find("span").show();
			$('.second-nav li').find("a").show();
			$(".right_col").css("width",(_W-180)+"px");
			$(".right_col").css("margin-left",180+"px");
			$('.navbar-left ul li ul.third-nav>li').css('padding-left', 15 + 'px');
			$('.navbar-left  ul  li div.dropdown').show();
		}
		else
		{
			$('.topbtn i').css('transform','rotate(90deg)');
			$('.navbar-left .topbtn').addClass("cls");
			$('.navbar-left .topbtn').removeClass("see");
			$('.navbar-left ').css("width","50px");	
			$('.navbar-left').find("span").hide();			
			$('.second-nav li').find("a").hide();
			$(".right_col").css("width",(_W-50)+"px");
			$(".right_col").css("margin-left",50+"px");
			$('.navbar-left ul li ul.third-nav>li').css('padding',0 + 'px');
			// $('[data-toggle="tooltip"]').tooltip();	
			$('.navbar-left  ul  li div.dropdown').hide();
		}
		
	});


	
	function loadPage()
	{
		var _H=$("body").height();
		var _W=$("body").width();
		$('.navbar-left').css("height",(_H - 40) + "px");
		$('.J_iframe').css("height",(_H - 79) + "px");
		$('.right_col').css("height",(_H - 79) + "px");
		// $(".navbar-left .second-nav").eq(0).show();
		// $(".ord_li").eq(0).children("i").attr("class","fa fa-sort-desc");
		$(".container").css("width",(_W-180)+"px");
		//详情页高度限制
		$('#myTabContent').css({"height":(_H - 250) + "px","overflow-y":"scroll"});
		
	}

	$('.top-menu li').click(function() {
		$(this).siblings().find('.top_down_menu').hide();
		$(this).find('.top_down_menu').slideToggle();
	});



});