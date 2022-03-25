package com.aliyunidaas.sync.event.objects;

/**
 * 同步日志
 *
 * @author hatterjiang
 */
public class SyncEvent {
    /**
     * 阿里云主账号UID
     */
    private Long aliUid;
    /**
     * IDaaS实例ID
     */
    private String instanceId;
    /**
     * 消息版本号
     */
    private String eventVersion;
    /**
     * Array of EventData in JSON String
     *
     * @see EventData
     */
    private String eventData;

    public Long getAliUid() {
        return aliUid;
    }

    public void setAliUid(Long aliUid) {
        this.aliUid = aliUid;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getEventVersion() {
        return eventVersion;
    }

    public void setEventVersion(String eventVersion) {
        this.eventVersion = eventVersion;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }
}
