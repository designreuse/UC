package com.yealink.uc.api.modules.account.response;

/**
 * @author ChNan
 */
public class EditAccountPasswordResponse {
    private boolean isEditSuccess;

    public EditAccountPasswordResponse(final boolean isEditSuccess) {
        this.isEditSuccess = isEditSuccess;
    }

    public boolean getIsEditSuccess() {
        return isEditSuccess;
    }

    public void setIsEditSuccess(final boolean isEditSuccess) {
        this.isEditSuccess = isEditSuccess;
    }
}
