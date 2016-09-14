var languageCode='zh_cn';

function Lang(key) {
	if (L[key]) {
		return L[key];
	} else {
		return key;
	}
}

var L = {
	system_common_prompt_please_select_a_recored:"\u8bf7\u9009\u62e9\u9700\u8981\u5220\u9664\u7684\u8bb0\u5f55\uff01",
	mail_tips_fil_password:"\u53d1\u4ef6\u4eba\u8ba4\u8bc1\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a\uff01"

}