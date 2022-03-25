package com.aliyunidaas.sync.event.bizdata;

import java.util.List;

/**
 * 用户对象
 *
 * @author hatterjiang
 */
public class UserInfo {
    private String sourceType;
    private String sourceId;

    private String userId;
    private String username;
    private String displayName;

    private String password;
    private Boolean passwordSet;

    private String phoneNumber;
    private String phoneRegion;
    private Boolean phoneVerified;

    private String email;
    private Boolean emailVerified;

    /**
     * 状态：enabled-启用，disabled-禁用
     */
    private String status;
    private Long accountExpireTime;
    private Long registerTime;
    private Long lockExpireTime;
    private Long createTime;
    private Long lastUpdatedTime;
    private String description;
    private List<UserInfoOrganizationalUnit> userOrganizationalUnits;

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getPasswordSet() {
        return passwordSet;
    }

    public void setPasswordSet(Boolean passwordSet) {
        this.passwordSet = passwordSet;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneRegion() {
        return phoneRegion;
    }

    public void setPhoneRegion(String phoneRegion) {
        this.phoneRegion = phoneRegion;
    }

    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAccountExpireTime() {
        return accountExpireTime;
    }

    public void setAccountExpireTime(Long accountExpireTime) {
        this.accountExpireTime = accountExpireTime;
    }

    public Long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }

    public Long getLockExpireTime() {
        return lockExpireTime;
    }

    public void setLockExpireTime(Long lockExpireTime) {
        this.lockExpireTime = lockExpireTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Long lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserInfoOrganizationalUnit> getUserOrganizationalUnits() {
        return userOrganizationalUnits;
    }

    public void setUserOrganizationalUnits(List<UserInfoOrganizationalUnit> userOrganizationalUnits) {
        this.userOrganizationalUnits = userOrganizationalUnits;
    }
}
