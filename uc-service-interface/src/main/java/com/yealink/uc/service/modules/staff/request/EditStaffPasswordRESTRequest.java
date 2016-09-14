package com.yealink.uc.service.modules.staff.request;

/**
 * @author ChNan
 */
public class EditStaffPasswordRESTRequest {
    private String staffId;

    private String oldPassword;

    private String newPassword;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(final String staffId) {
        this.staffId = staffId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(final String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }
}
