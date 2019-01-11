var regex = {
		
		/**
		 * 是否中文
		 */
		isChinese : function (text){
			var pattern = /^[\u4E00-\u9FA5]*$/;
			return pattern.test(text);
		},
		
		/**
		 * 是否邮箱
		 */
		isEmail : function (text) {
			var pattern = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
			return pattern.test(text);
		}
		
}