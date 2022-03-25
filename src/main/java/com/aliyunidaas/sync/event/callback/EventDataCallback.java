package com.aliyunidaas.sync.event.callback;

import com.aliyunidaas.sync.event.callback.impl.DefaultEventDataCallbackImpl;
import com.aliyunidaas.sync.event.callback.objects.EventDataResponse;
import com.aliyunidaas.sync.event.context.EventContext;

/**
 * 事件回调，用户可以直接实现，或使用实现 com.aliyunidaas.sync.event.callback.impl.DefaultEventDataCallbackImpl
 *
 * @author hatterjiang
 * @see DefaultEventDataCallbackImpl
 */
public interface EventDataCallback {

    /**
     * 处理一条数据回调
     */
    EventDataResponse onEventData(EventContext eventContext);
}
