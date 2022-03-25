package com.aliyunidaas.sync.event.callback.objects;

/**
 * 事件处理结果
 *
 * @author hatterjiang
 */
public class EventDataResponse {
    private EventDataResponseType type;
    private String eventCode;
    private String eventMessage;

    /**
     * 创建一个成功结果
     */
    public static EventDataResponse newSuccessEventDataResponse() {
        final EventDataResponse eventDataResponse = new EventDataResponse();
        eventDataResponse.setType(EventDataResponseType.SUCCESS);
        eventDataResponse.setEventCode("SUCCESS");
        eventDataResponse.setEventMessage("SUCCESS");
        return eventDataResponse;
    }

    /**
     * 创建一个跳过的结果
     */
    public static EventDataResponse newSkippedEventDataResponse(String eventCode, String eventMessage) {
        return newEventDataResponse(EventDataResponseType.SKIPPED, eventCode, eventMessage);
    }

    /**
     * 创建一个失败需要重试的结果
     */
    public static EventDataResponse newNeedRetryEventDataResponse(String eventCode, String eventMessage) {
        return newEventDataResponse(EventDataResponseType.NEED_RETRY, eventCode, eventMessage);
    }

    /**
     * 创建一个无需重试的结果
     */
    public static EventDataResponse newFailedEventDataResponse(String eventCode, String eventMessage) {
        return newEventDataResponse(EventDataResponseType.FAILED, eventCode, eventMessage);
    }

    private static EventDataResponse newEventDataResponse(EventDataResponseType type, String eventCode, String eventMessage) {
        final EventDataResponse eventDataResponse = new EventDataResponse();
        eventDataResponse.setType(type);
        eventDataResponse.setEventCode(eventCode);
        eventDataResponse.setEventMessage(eventMessage);
        return eventDataResponse;
    }

    public EventDataResponseType getType() {
        return type;
    }

    public void setType(EventDataResponseType type) {
        this.type = type;
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
