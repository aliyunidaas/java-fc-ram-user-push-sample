package com.aliyunidaas.sync.event.callback;

import com.aliyunidaas.sync.event.bizdata.UserInfo;
import com.aliyunidaas.sync.event.callback.objects.EventDataResponse;
import com.aliyunidaas.sync.event.constants.SyncEventConstants;
import com.aliyunidaas.sync.event.context.EventContext;

/**
 * 全量推送用户回调
 *
 * @author hatterjiang
 */
public interface UserPushCallback {
    /**
     * @see SyncEventConstants#USER_PUSH
     */
    default EventDataResponse onUserPush(EventContext eventContext, UserInfo userInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }
}
