package com.aliyunidaas.sync.event.bizdata;

/**
 * 用户组织机构对象
 *
 * @author hatterjiang
 */
public class UserInfoOrganizationalUnit {
    private String organizationalUnitId;
    private String name;
    private Boolean primary;

    public String getOrganizationalUnitId() {
        return organizationalUnitId;
    }

    public void setOrganizationalUnitId(String organizationalUnitId) {
        this.organizationalUnitId = organizationalUnitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }
}
