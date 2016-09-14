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
	license_revoke_reason:"The cancellation reason can't be empty!"
}