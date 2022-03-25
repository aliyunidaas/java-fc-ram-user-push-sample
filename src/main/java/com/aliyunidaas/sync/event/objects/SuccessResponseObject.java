package com.aliyunidaas.sync.event.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * 事件推送成功处理响应
 *
 * @author hatterjiang
 */
public class SuccessResponseObject implements ResponseObject {
    private List<ResponseEventObject> successEvents = new ArrayList<>();
    private List<ResponseEventObject> skippedEvents = new ArrayList<>();
    private List<ResponseEventObject> failedEvents = new ArrayList<>();
    private List<ResponseEventObject> retriedEvents = new ArrayList<>();

    public List<ResponseEventObject> getSuccessEvents() {
        return successEvents;
    }

    public void setSuccessEvents(List<ResponseEventObject> successEvents) {
        this.successEvents = successEvents;
    }

    public List<ResponseEventObject> getSkippedEvents() {
        return skippedEvents;
    }

    public void setSkippedEvents(List<ResponseEventObject> skippedEvents) {
        this.skippedEvents = skippedEvents;
    }

    public List<ResponseEventObject> getFailedEvents() {
        return failedEvents;
    }

    public void setFailedEvents(List<ResponseEventObject> failedEvents) {
        this.failedEvents = failedEvents;
    }

    public List<ResponseEventObject> getRetriedEvents() {
        return retriedEvents;
    }

    public void setRetriedEvents(List<ResponseEventObject> retriedEvents) {
        this.retriedEvents = retriedEvents;
    }
}
