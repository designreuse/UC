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
	system_common_confirm_send_email_to_records:"\u4f60\u786e\u5b9a\u8981\u53d1\u9001\u90ae\u4ef6\u5417\uff1f",
	system_common_confirm_reset_records_certificate:"\u4f60\u786e\u5b9a\u8981\u91cd\u7f6e\u5bc6\u7801\u5417\uff1f",
	system_common_confirm_revoke_records:"\u4f60\u786e\u5b9a\u8981\u64a4\u9500\u9009\u4e2d\u7684\u7528\u6237\uff1f",

	system_common_please_select_a_recored:"\u8bf7\u9009\u62e9\u4e00\u9879\u8fdb\u884c\u64cd\u4f5c",
	system_common_tips_captcha_error:"\u9a8c\u8bc1\u7801\u9519\u8bef\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165\uff01",
	system_common_tips_fill_captcha:"\u9a8c\u8bc1\u7801\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	system_common_tips_capslock:"\u5927\u5199\u9501\u5b9a\u952e\u5df2\u6253\u5f00",
	system_common_please_wait:"\u8bf7\u7a0d\u7b49...",
	system_common_check_file_format_size:"\u4e0a\u4f20\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u683c\u5f0f\u548c\u5927\u5c0f\u662f\u5426\u6b63\u786e\uff01",
	system_common_file:"\u6587\u4ef6\uff1a",
	system_common_size:"\u5927\u5c0f\u4e3a",
	system_common_file_size_limit:"\u6587\u4ef6\u603b\u5927\u5c0f\u9650\u5236\uff1a",
	system_common_confirm_delete_file:"\u662f\u5426\u9700\u8981\u5220\u9664\u6587\u4ef6\uff1a",
	system_common_delete_success:"\u5220\u9664\u6210\u529f\uff01",
	system_common_delete_failed:"\u5220\u9664\u5931\u8d25\uff01",
	system_common_error:"\u8bf7\u6c42\u6267\u884c\u5931\u8d25\uff0c\u8bf7\u91cd\u65b0\u5c1d\u8bd5\uff01",
	system_common_tips_pwd_number:"\u81f3\u5c11\u9700\u8981\u4e00\u4e2a\u6570\u5b57\uff01",
	system_common_tips_pwd_word:"\u81f3\u5c11\u9700\u8981\u4e00\u4e2a\u5b57\u6bcd\uff01",
	system_common_tips_pwd_symnol:"\u81f3\u5c11\u9700\u8981\u4e00\u4e2a\u7279\u6b8a\u5b57\u7b26\uff01",
	system_common_confirm_send_email_to_all:"\u4f60\u786e\u5b9a\u8981\u7ed9\u4f01\u4e1a\u4e0b\u6240\u6709\u4eba\u53d1\u9001\u90ae\u4ef6\u5417\uff1f",
	system_common_confirm_reset_all_certificate:"\u4f60\u786e\u5b9a\u8981\u91cd\u7f6e\u4f01\u4e1a\u4e0b\u6240\u6709\u4eba\u7684\u5bc6\u7801/pin\u5417\uff1f",
	system_common_confirm_revoke_all:"\u4f60\u786e\u5b9a\u8981\u64a4\u9500\u4f01\u4e1a\u4e0b\u6240\u6709\u7528\u6237\u5417\uff1f\uff1f",
	system_common_file_can_not_over_5:"\u6587\u4ef6\u5fc5\u987b\u5c0f\u4e8e5M\uff01",
	system_common_no_results_match:"\u6ca1\u6709\u7ed3\u679c\u5339\u914d\uff01",
	system_common_add_success_and_confirm_send_email_to_records:"\u6dfb\u52a0\u6210\u529f\uff0c\u662f\u5426\u9a6c\u4e0a\u53d1\u9001\u90ae\u4ef6\uff1f",
	system_common_confirm_reset_records_pin:"\u4f60\u786e\u5b9a\u8981\u91cd\u7f6epin?",
	//======================\u767b\u5f55\u6a21\u5757begin==========================================================//
	login_tips_fill_username:"\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	login_tips_fill_password:"\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	login_message_contain_captcha:"\u9a8c\u8bc1\u7801",
	login_tips_username_invalid:"\u7528\u6237\u540d\u65e0\u6548\uff01",
	login_recommend_mail_subject:"\u4ebf\u8054\u4e91\u670d\u52a1\u4ecb\u7ecd",
	login_recommend_mail_line1:"\u600e\u6837\u624d\u80fd\u4f7f\u7528Yealink\u4e91\u670d\u52a1\uff1f",
	login_recommend_mail_line3:"1\u3001\u76f4\u63a5\u70b9\u51fbhttp://www.yealinkvc.com \u6216\u8005\u8fdb\u5165Yealink\u5b98\u7f51http://www.yealink.com \u8fdb\u884c\u4f01\u4e1a\u53f7\u7801\u7533\u8bf7\uff1b",
	login_recommend_mail_line4:"2\u3001\u767b\u5f55\u7ba1\u7406\u5e73\u53f0\uff0c\u521b\u5efa\u5c5e\u4e8e\u60a8\u7ec4\u7ec7\u5185\u90e8\u7684\u6210\u5458\u8d26\u53f7\uff1b",
	login_recommend_mail_line5:"3\u3001\u6210\u5458\u4f7f\u7528\u8d26\u53f7\u5bc6\u7801\u5728VCS\u3001T49G\u3001VC Dsektop\u4e0a\u767b\u5f55\u3002",
	login_recommend_mail_line6:"\u5373\u523b\u5f00\u542f\u89c6\u9891\u4f1a\u8bae\u7684\u6781\u81f4\u4f53\u9a8c\u5427\uff01",
	login_recommend_mail_yealink_group1:"\u4ebf\u8054\u56e2\u961f",
	login_recommend_mail_yealink_group2:"www.yealink.com",
	//======================\u767b\u5f55\u6a21\u5757end===========================================================//

	//======================\u90ae\u7bb1\u914d\u7f6estart========================================================//
	mail_tips_fill_server:"\u670d\u52a1\u5668\u5730\u5740\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	mail_tips_fill_port:"\u670d\u52a1\u5668\u7aef\u53e3\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	mail_tips_port_number_only:"\u8bf7\u8f93\u5165\u6570\u5b57",
	mail_tips_port_invalid:"\u65e0\u6548\u7684\u670d\u52a1\u5668\u5ea6\u7aef\u53e3\uff01",
	mail_tips_fill_username:"\u53d1\u4ef6\u4eba\u5730\u5740\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	mail_tips_username_invalid:"\u65e0\u6548\u7684\u90ae\u4ef6\u5730\u5740\uff01",
	mail_tips_connection_success:"\u8fde\u63a5\u6210\u529f\uff01",
	mail_tips_saving:"\u4fdd\u5b58\u4e2d...",
	mail_tips_test_connecting:"\u8fde\u63a5\u4e2d...",
	//======================\u90ae\u7bb1\u914d\u7f6eend==========================================================//

	enterprise_tips_fill_fullname:"\u59d3\u540d\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	enterprise_tips_invalid_fullname:"\u59d3\u540d\u5305\u542b\u65e0\u6548\u5b57\u7b26\uff01",
	enterprise_tips_fill_company:"\u516c\u53f8\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	enterprise_tips_invalid_company:"\u516c\u53f8\u540d\u79f0\u5305\u542b\u65e0\u6548\u5b57\u7b26\uff01",
	enterprise_tips_invalid_email:"\u90ae\u7bb1\u683c\u5f0f\u65e0\u6548\uff01",
	enterprise_tips_fill_email:"\u90ae\u7bb1\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	enterprise_tips_password_fill_password:"\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	enterprise_tips_password_8_characters_min:"\u5bc6\u7801\u81f3\u5c118\u4f4d\uff01",
	enterprise_tips_email_exist:"\u90ae\u7bb1\u5df2\u7ecf\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165\uff01",
	enterprise_tips_revoke_reason_not_null:"\u64a4\u9500\u539f\u56e0\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	please_choose_correct_file:"\u652f\u6301\u56fe\u7247\u683c\u5f0f\uff1ajpg/jpeg/png/bmp/gif\uff01",

	//===============================\u6ce8\u518c\u670d\u52a1\u5668start====================//
	freeswitch_list_confirm_delete_the_records:"\u786e\u5b9a\u5220\u9664\u8be5\u6761\u6ce8\u518c\u670d\u52a1\u5668\u6570\u636e\uff1f",
	freeswitch_tips_fill_server:"\u6ce8\u518c\u670d\u52a1\u5668\u5730\u5740\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	freeswitch_tips_invalid_server:"\u65e0\u6548\u7684\u6ce8\u518c\u670d\u52a1\u5668\u5730\u5740\uff01",
	freeswitch_tips_fill_port:"ESL\u7aef\u53e3\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	freeswitch_tips_invalid_port:"\u65e0\u6548\u7684ESl\u7aef\u53e3\uff01",
	freeswitch_tips_fill_password:"\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	freeswitch_tips_save_connecting:"\u4fdd\u5b58\u6ce8\u518c\u670d\u52a1\u5668\u8fde\u63a5\u4e2d...",
	freeswitch_tips_test_connecting:"\u6d4b\u8bd5\u6ce8\u518c\u670d\u52a1\u5668\u8fde\u63a5\u4e2d...",
	freeswitch_tips_test_connected_success:"\u6d4b\u8bd5\u8fde\u63a5\u6210\u529f\uff01",
	freeswitch_tips_save_connected_success:"\u4fdd\u5b58\u6ce8\u518c\u670d\u52a1\u5668\u8fde\u63a5\u6210\u529f\uff01",
	//===============================\u6ce8\u518c\u670d\u52a1\u5668end======================//

	attachment_invalid_file_name_too_long:"\u6587\u4ef6\u540d\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7256\u4f4d\uff01",

	account_tips_fill_email:"\u90ae\u7bb1\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	account_tips_invalid_email:"\u65e0\u6548\u7684\u90ae\u7bb1\u683c\u5f0f\uff01",
	account_tips_password_fill_repeatPwd:"\u8bf7\u518d\u6b21\u8f93\u5165\u5bc6\u7801\uff01",
	account_tips_password_not_consistent:"\u4e24\u6b21\u8f93\u5165\u7684\u5bc6\u7801\u4e0d\u4e00\u81f4\uff01",
	account_tips_password_fill_newPwd:"\u65b0\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	account_tips_password_8_characters_min:"\u5bc6\u7801\u81f3\u5c118\u4e2a\u5b57\u7b26\uff01",
	account_tips_password_fill_currentPwd:"\u5f53\u524d\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	account_tips_fill_username:"\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	account_tips_fmt_username:"\u8bf7\u8f93\u5165\u6570\u5b57\u3001\u5b57\u6bcd\u3001_\u3001@\u3001-\u3001.",
	account_tips_capslock:"\u5927\u5199\u9501\u5b9a\u952e\u5df2\u5f00\u542f",
	account_tips_fill_name:"\u8bf7\u8f93\u5165\u59d3\u540d",
	account_tips_invalid_full_name:"\u65e0\u6548\u7684\u59d3\u540d\uff01",
	account_tips_fill_full_name:"\u59d3\u540d\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	account_tips_invalid_company:"\u65e0\u6548\u7684\u516c\u53f8\u540d\u79f0\uff01",
	account_tips_fill_company:"\u516c\u53f8\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	account_tips_invalid_name:"\u59d3\u540d\u683c\u5f0f\u9519\u8bef\uff01",
	account_tips_fill_extension:"\u5206\u673a\u53f7\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	account_tips_invalid_extension:"\u5206\u673a\u53f7\u683c\u5f0f\u9519\u8bef\uff01",
	//------------------------------------------------------//
	user_tips_fill_name:"\u59d3\u540d\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	user_tips_invalid_name:"\u59d3\u540d\u683c\u5f0f\u4e0d\u6b63\u786e\uff01",
	user_tips_phone_existed:"\u7535\u8bdd\u53f7\u7801\u5df2\u7ecf\u5b58\u5728\uff01",
	user_tips_please_choose_correct_file:"\u53ea\u652f\u6301\u5bfc\u5165.xls \u683c\u5f0f\u7684\u6587\u4ef6\uff01",
	user_tips_file:"\u6587\u4ef6\uff1a",
	user_tips_size:"\u5927\u5c0f\u4e3a",
	user_tips_all_file_size:"\u6587\u4ef6\u603b\u5927\u5c0f\u9650\u5236\uff1a",
	user_tips_phone_only_four_bit:"\u53f7\u7801\u53ea\u80fd\u4e3a4\u4f4d\uff01",
	user_account_operation_selected_all : "\u52fe\u9009\u6240\u6709\u7528\u6237",
	user_account_operation_cancle_selected_all: "\u53d6\u6d88\u52fe\u9009\u6240\u6709\u7528\u6237",
	user_failed_mail_select_recored:"\u8bf7\u9009\u62e9\u9700\u8981\u64cd\u4f5c\u7684\u6570\u636e\uff01",

	//======================License start==========================//
	license_purpose_none:"\u7528\u9014\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	license_no_none:"\u8bf7\u8f93\u5165license\uff01",
	license_no_invalid:"license\u683c\u5f0f\u4e0d\u6b63\u786e\uff01",
	license_revoke_reason:"\u64a4\u9500\u539f\u56e0\u4e0d\u80fd\u4e3a\u7a7a\uff01",
	//======================License end==========================//

	mail_tips_fil_password:"\u53d1\u4ef6\u4eba\u8ba4\u8bc1\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a\uff01"

}