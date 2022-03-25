package com.aliyunidaas.sync.event.objects;

/**
 * 事件处理结果，分别用在处理成功、失败不重试、跳过，失败但重试场景
 *
 * @author hatterjiang
 */
public class ResponseEventObject {
    private String eventId;
    private String eventCode;
    private String eventMessage;

    public ResponseEventObject() {
    }

    public ResponseEventObject(String eventId, String eventCode, String eventMessage) {
        this.eventId = eventId;
        this.eventCode = eventCode;
        this.eventMessage = eventMessage;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }
}
