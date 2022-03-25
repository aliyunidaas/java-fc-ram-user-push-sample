package com.aliyunidaas.sync.event.context;

import com.aliyunidaas.sync.event.objects.EventData;
import com.aliyunidaas.sync.event.objects.SyncEvent;
import com.aliyunidaas.sync.log.SimpleLogger;

/**
 * 事件处理上下文
 *
 * @author hatterjiang
 */
public class EventContext {
    private SyncEvent syncEvent;
    private EventData eventData;
    private SimpleLogger logger;

    public SyncEvent getSyncEvent() {
        return syncEvent;
    }

    public void setSyncEvent(SyncEvent syncEvent) {
        this.syncEvent = syncEvent;
    }

    public EventData getEventData() {
        return eventData;
    }

    public void setEventData(EventData eventData) {
        this.eventData = eventData;
    }

    public SimpleLogger getLogger() {
        return logger;
    }

    public void setLogger(SimpleLogger logger) {
        this.logger = logger;
    }
}
