package com.aliyunidaas.sync.event.objects;

/**
 * 事件数据体
 *
 * @author hatterjiang
 */
public class EventData {
    /**
     * 事件ID
     */
    private String eventId;
    /**
     * 事件类型
     *
     * @see com.aliyunidaas.sync.event.constants.SyncEventConstants
     */
    private String eventType;
    /**
     * 事件时间，Unix Epoch 毫秒
     */
    private Long eventTime;
    /**
     * 业务ID
     */
    private String bizId;
    /**
     * 业务数据，板书事件类型对应的数据结构分为用户和组织机构
     *
     * @see com.aliyunidaas.sync.event.bizdata.UserInfo
     * @see com.aliyunidaas.sync.event.bizdata.OrganizationalUnitInfo
     */
    private String bizData;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizData() {
        return bizData;
    }

    public void setBizData(String bizData) {
        this.bizData = bizData;
    }
}
