package com.yealink.uc.common.modules.staff.vo;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import com.yealink.uc.platform.utils.StringUtil;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author yewl
 */
public class ImportStaff {

    private static String[] GENDER_ARRAY_CN = {"女", "男"};
    private static String[] GENDER_ARRAY = {"female", "man"};

    private Long id;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String name;

    private String orgPaths;

    @Email
    private String email;

    private String genderStr;

    private Integer gender;

    private String mobilePhone;

    private List<String> mobilePhones;

    private String workPhone;

    private Integer extensionNumber;


    private int rowNum;

    private String errorMessage;

    public void constructGender(){
        gender = (GENDER_ARRAY_CN[0].equalsIgnoreCase(genderStr) || GENDER_ARRAY[0].equalsIgnoreCase(genderStr) )? 0 : 1;
    }

    public void constructMobilePhones(){
        mobilePhones = Arrays.asList(mobilePhone.split("\n"));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void validate() {
        StringBuilder buffer = new StringBuilder();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ImportStaff>> constraintViolations = validator.validate(this);
        for(ConstraintViolation constraintViolation : constraintViolations){
            buffer.append(constraintViolation.getMessage());
            buffer.append(";");
        }
        if(buffer.length() != 0){
            buffer.deleteCharAt(buffer.length() - 1);
        }
        errorMessage = buffer.toString();
    }

    public void appendErrorMessage(String errorMessage){
        if(!StringUtil.isStrEmpty(this.errorMessage)){
            this.errorMessage += ";";
        }
        this.errorMessage += errorMessage;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean hasError(){
        return !StringUtil.isStrEmpty(errorMessage);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgPaths() {
        return orgPaths;
    }

    public void setOrgPaths(String orgPaths) {
        this.orgPaths = orgPaths;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenderStr() {
        return genderStr;
    }

    public void setGenderStr(String genderStr) {
        this.genderStr = genderStr;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public List<String> getMobilePhones() {
        return mobilePhones;
    }

    public void setMobilePhones(List<String> mobilePhones) {
        this.mobilePhones = mobilePhones;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }
}
