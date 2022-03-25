package com.aliyunidaas.sync.event.constants;

/**
 * 事件类型常量
 *
 * @author hatterjiang
 */
public interface SyncEventConstants {
    /**
     * 用户事件前缀
     */
    String USER_EVENT_PREFIX = "urn:alibaba:idaas:app:event:ud:user:";
    /**
     * 用户事件前缀
     */
    String ORGANIZATIONAL_UNIT_EVENT_PREFIX = "urn:alibaba:idaas:app:event:ud:organizational_unit:";

    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * 账户创建
     */
    String USER_CREATE = "urn:alibaba:idaas:app:event:ud:user:create";
    /**
     * 账户删除
     */
    String USER_DELETE = "urn:alibaba:idaas:app:event:ud:user:delete";
    /**
     * 账户基础信息更新
     */
    String USER_UPDATE_INFO = "urn:alibaba:idaas:app:event:ud:user:update_info";
    /**
     * 账户密码更新
     */
    String USER_UPDATE_PASSWORD = "urn:alibaba:idaas:app:event:ud:user:update_password";
    /**
     * 账户禁用
     */
    String USER_DISABLE = "urn:alibaba:idaas:app:event:ud:user:disable";
    /**
     * 账户启用
     */
    String USER_ENABLE = "urn:alibaba:idaas:app:event:ud:user:enable";
    /**
     * 账户锁定
     */
    String USER_LOCK = "urn:alibaba:idaas:app:event:ud:user:lock";
    /**
     * 账户解锁
     */
    String USER_UNLOCK = "urn:alibaba:idaas:app:event:ud:user:unlock";
    /**
     * 账户变更组织机构
     */
    String USER_UPDATE_PRIMARY_OU = "urn:alibaba:idaas:app:event:ud:user:update_primary_ou";

    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * 组织机构创建
     */
    String ORGANIZATIONAL_UNIT_CREATE = "urn:alibaba:idaas:app:event:ud:organizational_unit:create";
    /**
     * 组织机构删除
     */
    String ORGANIZATIONAL_UNIT_DELETE = "urn:alibaba:idaas:app:event:ud:organizational_unit:delete";
    /**
     * 修改组织机构基础信息
     */
    String ORGANIZATIONAL_UNIT_UPDATE = "urn:alibaba:idaas:app:event:ud:organizational_unit:update";
    /**
     * 组织机构下移入账户
     */
    String ORGANIZATIONAL_UNIT_ADD_USER = "urn:alibaba:idaas:app:event:ud:organizational_unit:add_user";
    /**
     * 组织机构下移出账户
     */
    String ORGANIZATIONAL_UNIT_REMOVE_USER = "urn:alibaba:idaas:app:event:ud:organizational_unit:remove_user";
    /**
     * 组织机构更新父组织
     */
    String ORGANIZATIONAL_UNIT_UPDATE_ORGANIZATIONAL_UNIT = "urn:alibaba:idaas:app:event:ud:organizational_unit:update_parent_organizational_unit";

    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * 全量推送组织机构
     */
    String ORGANIZATIONAL_UNIT_PUSH = "urn:alibaba:idaas:app:event:ud:organizational_unit:push";
    /**
     * 全量推送账户
     */
    String USER_PUSH = "urn:alibaba:idaas:app:event:ud:user:push";
}
