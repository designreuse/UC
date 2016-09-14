var languageCode='en';

function Lang(key) {
	if (L[key]) {
		return L[key];
	} else {
		return key;
	}
}

var L = {
	system_common_prompt_please_select_a_recored:"Please select the records you want to delete!",
	system_common_confirm_delete_the_records:"Confirm to delete the record?",
	system_common_confirm_send_email_to_records:"Confirm to send the Email?",
	system_common_confirm_reset_records_certificate:"Reset the password?",
	system_common_confirm_reset_records_pin:"Reset the pin?",
	system_common_confirm_revoke_records:"Confirm to revoke the selected user?",
	system_common_please_select_a_recored:"Please select a record to operate",
	system_common_tips_captcha_error:"Captcha is error, please input again!",
	system_common_tips_fill_captcha:"Captcha can't be empty!",
	system_common_tips_capslock:"Caps lock key is open",
	system_common_please_wait:"Please wait...",
	system_common_check_file_format_size:"Upload failed, please check the format and size",
	system_common_file:"File:",
	system_common_size:"Size is",
	system_common_file_size_limit:"Total files size:",
	system_common_confirm_delete_file:"Whether to delete the file:",
	system_common_delete_success:"Delete successfully!",
	system_common_delete_failed:"Delete failed!",
	system_common_error:"Request failed, please try again!",
	system_common_tips_pwd_number:"At least one number!",
	system_common_tips_pwd_word:"At least one letter!",
	system_common_tips_pwd_symnol:"At least one special character!",
	system_common_confirm_send_email_to_all:"Confirm to send the Email to all?",
	system_common_confirm_reset_all_certificate:"Reset the password and pin code for all?",
	system_common_confirm_revoke_all:"Confirm to Revoke all user?",
	system_common_file_can_not_over_5:"File must be less than 5M!",
	system_common_no_results_match:"No results match!",
	system_common_add_success_and_confirm_send_email_to_records:"Add success, whether to send mail immediately?",
	login_tips_fill_username:"Username can't be empty!",
	login_tips_fill_password:"Password can't be empty!",
	login_tips_username_invalid:"Invalid username!",
	login_message_contain_captcha:"Captcha",
	login_recommend_mail_subject:"Introduction to Yealink Video Conferencing Cloud",
	login_recommend_mail_line1:"How to use Yealink cloud service?",
	login_recommend_mail_line3:"1.Directly click on http://www.yealinkvc.com or enter Yealink's official website http://www.yealink.com to apply for the enterprise number;",
	login_recommend_mail_line4:"2.Login management platform,and create extension numbers belong to your organization;",
	login_recommend_mail_line5:"3.Login VC Cloud on VCS, T49G and VC Desktop with account and password;",
	login_recommend_mail_line6:"Enjoy the perfect video conferencing immediately!",
	login_recommend_mail_yealink_group1:"Best Regards,",
	login_recommend_mail_yealink_group2:"The Yealink Team",
	mail_tips_fill_server:"Server address can't be empty!",
	mail_tips_fill_port:"Server port can't be empty!",
	mail_tips_port_number_only:"Please input the Numbers",
	mail_tips_port_invalid:"Invalid server port!",
	mail_tips_fill_username:"Sender's address can't be empty!",
	mail_tips_fil_password:"Authentication Password can't be empty!",
	mail_tips_username_invalid:"Invalid email address!",
	mail_tips_connection_success:"Connection succeed!",
	mail_tips_saving:"Saving...",
	mail_tips_test_connecting:"Connecting...",
	enterprise_tips_fill_fullname:"Full Name can't be empty!",
	enterprise_tips_invalid_fullname:"Full Name contains invalid characters!",
	enterprise_tips_fill_company:"Company can't be empty!",
	enterprise_tips_invalid_company:"Company name contains invalid characters!",
	enterprise_tips_invalid_email:"Email format is invalid!",
	enterprise_tips_fill_email:"Email address can't be empty!",
	enterprise_tips_password_fill_password:"Password can't be empty!",
	enterprise_tips_password_8_characters_min:"At least 8 characters!",
	enterprise_tips_email_exist:"Email Already in Use!",
	enterprise_tips_revoke_reason_not_null:"The cancellation reason can't be empty!",
	please_choose_correct_file:"Image format: jpg/jpeg/png/bmp/gif",
	freeswitch_list_confirm_delete_the_records:"Confirm to delete this register server data?",
	freeswitch_tips_fill_server:"Register server address can't be empty",
	freeswitch_tips_invalid_server:"Invalid registration server address",
	freeswitch_tips_fill_port:"ESL port can't be empty",
	freeswitch_tips_invalid_port:"Invalid ESl port",
	freeswitch_tips_fill_password:"Password can't be empty",
	freeswitch_tips_save_connecting:"Saving...",
	freeswitch_tips_test_connecting:"Connecting...",
	freeswitch_tips_test_connected_success:"Test connection is successful!",
	freeswitch_tips_save_connected_success:"Save succeed!",
	attachment_invalid_file_name_too_long:"File name cannot be more than 256 characters",
	account_tips_fill_email:"Email can't be empty!",
	account_tips_invalid_email:"Invalid email address!",
	account_tips_password_fill_repeatPwd:"Please input the password again!",
	account_tips_password_not_consistent:"Passwords don't match!",
	account_tips_password_fill_newPwd:"Password can't be empty!",
	account_tips_password_8_characters_min:"At least 8 characters!",
	account_tips_password_fill_currentPwd:"Password can't be empty!",
	account_tips_fill_username:"Username can't be empty!",
	account_tips_fmt_username:"Please input the Numbers, letters, _, @, -,.",
	account_tips_capslock:"Caps lock key is open",
	account_tips_fill_name:"Please enter your name",
	account_tips_invalid_full_name:"Invalid full name!",
	account_tips_fill_full_name:"Full Name can't be empty!",
	account_tips_invalid_company:"Invalid company name!",
	account_tips_fill_company:"Company can't be empty!",
	account_tips_invalid_name:"Name format is error!",
	account_tips_fill_extension:"Extension can't be empty!",
	account_tips_invalid_extension:"Extension format is error!",
	user_tips_fill_name:"Please enter your name",
	user_tips_invalid_name:"Name format is error!",
	user_tips_phone_existed:"Phone number already in use!",
	user_tips_please_choose_correct_file:"Only .XLS  format file is available!",
	user_tips_file:"File:",
	user_tips_size:"Size is",
	user_tips_all_file_size:"Total file size:",
	user_tips_phone_only_four_bit:"Number can only be 4!",
	user_account_operation_selected_all : "Select all users",
	user_account_operation_cancle_selected_all: "Cancel select all",
	user_failed_mail_select_recored:"Please select the records you want to operate!",
	license_purpose_none:"Purpose can't be empty!",
	license_no_none:"Please input the license!",
	license_no_invalid:"License format is error!",
	license_revoke_reason:"The cancellation reason can't be empty!"
}