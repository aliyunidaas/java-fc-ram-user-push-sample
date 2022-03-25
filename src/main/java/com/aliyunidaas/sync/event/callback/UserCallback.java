package com.aliyunidaas.sync.event.callback;

import com.aliyunidaas.sync.event.bizdata.UserInfo;
import com.aliyunidaas.sync.event.callback.objects.EventDataResponse;
import com.aliyunidaas.sync.event.constants.SyncEventConstants;
import com.aliyunidaas.sync.event.context.EventContext;

/**
 * 增量用户回调
 *
 * @author hatterjiang
 */
public interface UserCallback {

    /**
     * @see SyncEventConstants#USER_CREATE
     */
    default EventDataResponse onUserCreate(EventContext eventContext, UserInfo userInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }

    /**
     * @see SyncEventConstants#USER_DELETE
     */
    default EventDataResponse onUserDelete(EventContext eventContext, UserInfo userInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }

    /**
     * @see SyncEventConstants#USER_UPDATE_INFO
     */
    default EventDataResponse onUserUpdateInfo(EventContext eventContext, UserInfo userInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }

    /**
     * @see SyncEventConstants#USER_UPDATE_PASSWORD
     */
    default EventDataResponse onUserUpdatePassword(EventContext eventContext, UserInfo userInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }

    /**
     * @see SyncEventConstants#USER_DISABLE
     */
    default EventDataResponse onUserUpdateDisabled(EventContext eventContext, UserInfo userInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }

    /**
     * @see SyncEventConstants#USER_ENABLE
     */
    default EventDataResponse onUserUpdateEnabled(EventContext eventContext, UserInfo userInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }

    /**
     * @see SyncEventConstants#USER_LOCK
     */
    default EventDataResponse onUserLock(EventContext eventContext, UserInfo userInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }

    /**
     * @see SyncEventConstants#USER_UNLOCK
     */
    default EventDataResponse onUserUnlock(EventContext eventContext, UserInfo userInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }

    /**
     * @see SyncEventConstants#USER_UPDATE_PRIMARY_OU
     */
    default EventDataResponse onUserUpdatePrimaryOu(EventContext eventContext, UserInfo userInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }
}
