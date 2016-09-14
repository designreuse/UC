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
	system_common_confirm_delete_the_records:"\u786e\u5b9a\u5220\u9664\u6240\u9009\u62e9\u7684\u8bb0\u5f55\u5417\uff1f",

	//======================\u767b\u5f55\u6a21\u5757begin==========================================================//
	login_tips_capslock:"\u5927\u5199\u9501\u5b9a\u952e\u5df2\u6253\u5f00",
	login_tips_fill_username:"\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a",
	login_tips_fill_password:"\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a",
	login_message_contain_captcha:"\u9a8c\u8bc1\u7801",
	login_tips_captcha_error:"\u9a8c\u8bc1\u7801\u9519\u8bef\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165\uff01",
	login_tips_fill_captcha:"\u9a8c\u8bc1\u7801\u4e0d\u80fd\u4e3a\u7a7a",
	//======================\u767b\u5f55\u6a21\u5757end===========================================================//

	//======================\u90ae\u7bb1\u914d\u7f6estart========================================================//
	mail_tips_fill_server:"\u670d\u52a1\u5668\u5730\u5740\u4e0d\u80fd\u4e3a\u7a7a",
	mail_tips_fill_port:"\u670d\u52a1\u5668\u7aef\u53e3\u4e0d\u80fd\u4e3a\u7a7a",
	mail_tips_port_number_only:"\u8bf7\u8f93\u5165\u6570\u5b57",
	mail_tips_port_invalid:"\u65e0\u6548\u7684\u670d\u52a1\u5668\u5ea6\u7aef\u53e3",
	mail_tips_fill_username:"\u53d1\u4ef6\u4eba\u5730\u5740\u4e0d\u80fd\u4e3a\u7a7a",
	mail_tips_username_invalid:"\u65e0\u6548\u7684\u90ae\u4ef6\u5730\u5740",
	mail_tips_test_connection_success:"\u6d4b\u8bd5\u8fde\u63a5\u6210\u529f",
	//======================\u90ae\u7bb1\u914d\u7f6eend==========================================================//
	
	enterprise_tips_fill_firstname:"\u8bf7\u8f93\u5165firstName",
	enterprise_tips_invalid_firstname:"firstName\u5305\u542b\u65e0\u6548\u5b57\u7b26",
	enterprise_tips_fill_lastname:"\u8bf7\u8f93\u5165lastName",
	enterprise_tips_invalid_lastname:"lastName\u5305\u542b\u65e0\u6548\u5b57\u7b26",
	enterprise_tips_fill_company:"\u8bf7\u8f93\u5165\u516c\u53f8\u540d\u79f0",
	enterprise_tips_invalid_company:"\u516c\u53f8\u540d\u79f0\u5305\u542b\u65e0\u6548\u5b57\u7b26",
	enterprise_tips_invalid_email:"email\u683c\u5f0f\u65e0\u6548",
	enterprise_tips_fill_email:"\u8bf7\u8f93\u5165email",
	enterprise_tips_phone_invalid:"\u7535\u8bdd\u683c\u5f0f\u9519\u8bef",
	enterprise_tips_fill_phone:"\u8bf7\u8f93\u5165\u7535\u8bdd\u53f7\u7801",
	enterprise_tips_password_fill_password:"\u8bf7\u8f93\u5165\u5bc6\u7801",
	enterprise_tips_password_8_characters_min:"\u5bc6\u7801\u81f3\u5c118\u4f4d",
	enterprise_tips_password_word_num_symnol:"\u5bc6\u7801\u81f3\u5c11\u5305\u542b\u6570\u5b57\u3001\u5b57\u6bcd\u3001\u7279\u6b8a\u5b57\u7b26",
	enterprise_tips_email_exist:"email\u5df2\u7ecf\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165",
	please_choose_correct_file:"\u652f\u6301\u56fe\u7247\u683c\u5f0f\uff1ajpg/jpeg/png/bmp/gif",

	//===============================\u6ce8\u518c\u670d\u52a1\u5668start====================//
	freeswitch_list_confirm_delete_the_records:"\u786e\u5b9a\u5220\u9664\u8be5\u6761\u6ce8\u518c\u670d\u52a1\u5668\u6570\u636e\uff1f",
	freeswitch_tips_fill_server:"\u6ce8\u518c\u670d\u52a1\u5668\u5730\u5740\u4e0d\u80fd\u4e3a\u7a7a",
	freeswitch_tips_invalid_server:"\u65e0\u6548\u7684\u6ce8\u518c\u670d\u52a1\u5668\u5730\u5740",
	freeswitch_tips_exist_server:"\u6ce8\u518c\u670d\u52a1\u5668\u5730\u5740\u5df2\u5b58\u5728",
	freeswitch_tips_fill_port:"ESL\u7aef\u53e3\u4e0d\u80fd\u4e3a\u7a7a",
	freeswitch_tips_invalid_port:"\u65e0\u6548\u7684ESl\u7aef\u53e3",
	freeswitch_tips_fill_password:"\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a",
	freeswitch_tips_save_connecting:"\u4fdd\u5b58\u6ce8\u518c\u670d\u52a1\u5668\u8fde\u63a5\u4e2d...",
	freeswitch_tips_test_connecting:"\u6d4b\u8bd5\u6ce8\u518c\u670d\u52a1\u5668\u8fde\u63a5\u4e2d...",
	freeswitch_tips_test_connected_success:"\u6d4b\u8bd5\u8fde\u63a5\u6210\u529f",
	freeswitch_tips_save_connected_success:"\u4fdd\u5b58\u6ce8\u518c\u670d\u52a1\u5668\u8fde\u63a5\u6210\u529f",
	//===============================\u6ce8\u518c\u670d\u52a1\u5668end======================//

	account_tips_fill_email:"\u90ae\u7bb1\u4e0d\u80fd\u4e3a\u7a7a",
	account_tips_invalid_email:"\u65e0\u6548\u7684\u90ae\u7bb1\u683c\u5f0f",
	account_tips_invalid_email:"\u90ae\u7bb1\u683c\u5f0f\u4e0d\u6b63\u786e",
	account_tips_password_fill_repeatPwd:"\u8bf7\u518d\u6b21\u8f93\u5165\u5bc6\u7801!",
	account_tips_password_not_consistent:"\u4e24\u6b21\u8f93\u5165\u7684\u5bc6\u7801\u4e0d\u4e00\u81f4",
	account_tips_password_fill_newPwd:"\u65b0\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a!",
	account_tips_password_8_characters_min:"\u5bc6\u7801\u81f3\u5c118\u4e2a\u5b57\u7b26",
	account_tips_password_word_num_symnol:"\u9700\u8981\u4e00\u4e2a\u5b57\u6bcd\u3001\u6570\u5b57\u3001\u7b26\u53f7",
	account_tips_password_fill_currentPwd:"\u5f53\u524d\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a!",
	account_tips_fill_username:"\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a!",
	account_tips_fmt_username:"\u8bf7\u8f93\u5165\u6570\u5b57\u3001\u5b57\u6bcd\u3001_\u3001@\u3001-\u3001.",
	account_tips_exist_username:"\u8be5\u7528\u6237\u540d\u5df2\u7ecf\u5b58\u5728!",
	account_tips_phone_invalid:"\u53f7\u7801\u53ea\u80fd\u4e3a\u6570\u5b57",
	account_tips_fill_phone:"\u53f7\u7801\u4e0d\u80fd\u4e3a\u7a7a",
	account_tips_capslock:"\u5927\u5199\u9501\u5b9a\u952e\u5df2\u5f00\u542f",
	account_tips_fill_name:"\u8bf7\u8f93\u5165\u59d3\u540d",
	account_tips_invalid_first_name:"\u65e0\u6548\u7684\u59d3",
	account_tips_fill_first_name:"\u59d3\u4e0d\u80fd\u4e3a\u7a7a",
	account_tips_invalid_last_name:"\u65e0\u6548\u7684\u540d",
	account_tips_fill_last_name:"\u540d\u4e0d\u80fd\u4e3a\u7a7a",
	account_tips_invalid_company:"\u65e0\u6548\u7684\u516c\u53f8\u540d\u79f0",
	account_tips_fill_company:"\u516c\u53f8\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a",
	account_tips_invalid_name:"\u59d3\u540d\u683c\u5f0f\u9519\u8bef",
	//------------------------------------------------------//
	user_tips_fill_name:"\u8bf7\u8f93\u5165\u59d3\u540d",
	user_tips_invalid_name:"\u59d3\u540d\u683c\u5f0f\u4e0d\u6b63\u786e",
	user_tips_phone_existed:"\u7535\u8bdd\u53f7\u7801\u5df2\u7ecf\u5b58\u5728"
}