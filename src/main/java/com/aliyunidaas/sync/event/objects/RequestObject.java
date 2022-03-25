package com.aliyunidaas.sync.event.objects;

/**
 * 事件推送请求
 *
 * @author hatterjiang
 */
public class RequestObject {
    /**
     * @see SyncEvent
     */
    private String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
