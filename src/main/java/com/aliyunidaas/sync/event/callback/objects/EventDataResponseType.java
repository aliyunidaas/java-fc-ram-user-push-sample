package com.aliyunidaas.sync.event.callback.objects;

/**
 * 事件处理结果类型
 *
 * @author hatterjiang
 */
public enum EventDataResponseType {
    /**
     * 处理成功，消息不再推送
     */
    SUCCESS,
    /**
     * 跳过处理，消息不再推送
     */
    SKIPPED,
    /**
     * 处理失败，消息不再推送
     */
    FAILED,
    /**
     * 处理失败，消息还会推送，到达失败阈值后不再推送
     */
    NEED_RETRY,
}
