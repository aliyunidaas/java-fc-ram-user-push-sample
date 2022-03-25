package com.aliyunidaas.sync.event.callback.impl;

import com.aliyunidaas.sync.event.bizdata.OrganizationalUnitInfo;
import com.aliyunidaas.sync.event.bizdata.UserInfo;
import com.aliyunidaas.sync.event.callback.*;
import com.aliyunidaas.sync.event.callback.objects.EventDataResponse;
import com.aliyunidaas.sync.event.constants.SyncEventConstants;
import com.aliyunidaas.sync.event.context.EventContext;
import com.aliyunidaas.sync.event.objects.EventData;
import com.aliyunidaas.sync.util.ExceptionUtil;
import com.aliyunidaas.sync.util.JsonUtil;

import java.util.Arrays;

/**
 * 默认事件处理回调
 *
 * @author hatterjiang
 * @see UserCallback
 * @see UserPushCallback
 * @see OrganizationalUnitCallback
 * @see OrganizationalUnitPushCallback
 */
public class DefaultEventDataCallbackImpl implements EventDataCallback {
    private UserCallback userCallback = null;
    private UserPushCallback userPushCallback = null;
    private OrganizationalUnitCallback organizationalUnitCallback = null;
    private OrganizationalUnitPushCallback organizationalUnitPushCallback = null;

    @Override
    public EventDataResponse onEventData(EventContext eventContext) {
        try {
            return innerOnEventData(eventContext);
        } catch (Throwable t) {
            final EventData eventData = eventContext.getEventData();
            eventContext.getLogger().error(
                    "Exception, event data: " + JsonUtil.toJson(eventData) + ", exception: " + ExceptionUtil.printStacktrace(t));
            return EventDataResponse.newNeedRetryEventDataResponse("exception", t.getMessage());
        }
    }

    protected EventDataResponse innerOnEventData(EventContext eventContext) {
        assertNotNull(eventContext, "Event context is null");
        final EventData eventData = eventContext.getEventData();
        final String eventType = eventData.getEventType();
        assertNotNull(eventType, "Event type is null");

        if (eventType.startsWith(SyncEventConstants.USER_EVENT_PREFIX)) {
            final UserInfo userInfo = JsonUtil.fromJson(eventData.getBizData(), UserInfo.class);
            eventContext.getLogger().debug("Processing event data: " +
                    Arrays.asList(eventData.getEventId(), userInfo.getUserId()));
            if (SyncEventConstants.USER_PUSH.equals(eventType)) {
                if (this.userPushCallback != null) {
                    return userPushCallback.onUserPush(eventContext, userInfo);
                }
            } else if (this.userCallback != null) {
                switch (eventType) {
                    case SyncEventConstants.USER_CREATE:
                        return this.userCallback.onUserCreate(eventContext, userInfo);
                    case SyncEventConstants.USER_DELETE:
                        return this.userCallback.onUserDelete(eventContext, userInfo);
                    case SyncEventConstants.USER_UPDATE_INFO:
                        return this.userCallback.onUserUpdateInfo(eventContext, userInfo);
                    case SyncEventConstants.USER_UPDATE_PASSWORD:
                        return this.userCallback.onUserUpdatePassword(eventContext, userInfo);
                    case SyncEventConstants.USER_DISABLE:
                        return this.userCallback.onUserUpdateDisabled(eventContext, userInfo);
                    case SyncEventConstants.USER_ENABLE:
                        return this.userCallback.onUserUpdateEnabled(eventContext, userInfo);
                    case SyncEventConstants.USER_LOCK:
                        return this.userCallback.onUserLock(eventContext, userInfo);
                    case SyncEventConstants.USER_UNLOCK:
                        return this.userCallback.onUserUnlock(eventContext, userInfo);
                    case SyncEventConstants.USER_UPDATE_PRIMARY_OU:
                        return this.userCallback.onUserUpdatePrimaryOu(eventContext, userInfo);
                    default:
                        eventContext.getLogger().warn("Unknown user event type: " + eventType);
                }
            }
        } else if (eventType.startsWith(SyncEventConstants.ORGANIZATIONAL_UNIT_EVENT_PREFIX)) {
            final OrganizationalUnitInfo organizationalUnitInfo = JsonUtil.fromJson(eventData.getBizData(), OrganizationalUnitInfo.class);
            eventContext.getLogger().debug("Processing event data: " +
                    Arrays.asList(eventData.getEventId(), organizationalUnitInfo.getOrganizationalUnitId()));
            if (SyncEventConstants.ORGANIZATIONAL_UNIT_PUSH.equals(eventType)) {
                if (this.organizationalUnitPushCallback != null) {
                    return this.organizationalUnitPushCallback.onOuPush(eventContext, organizationalUnitInfo);
                }
            } else if (this.organizationalUnitCallback != null) {
                switch (eventType) {
                    case SyncEventConstants.ORGANIZATIONAL_UNIT_CREATE:
                        return this.organizationalUnitCallback.onOuCreate(eventContext, organizationalUnitInfo);
                    case SyncEventConstants.ORGANIZATIONAL_UNIT_DELETE:
                        return this.organizationalUnitCallback.onOuDelete(eventContext, organizationalUnitInfo);
                    case SyncEventConstants.ORGANIZATIONAL_UNIT_UPDATE:
                        return this.organizationalUnitCallback.onOuUpdate(eventContext, organizationalUnitInfo);
                    case SyncEventConstants.ORGANIZATIONAL_UNIT_ADD_USER:
                        return this.organizationalUnitCallback.onOuAddUser(eventContext, organizationalUnitInfo);
                    case SyncEventConstants.ORGANIZATIONAL_UNIT_REMOVE_USER:
                        return this.organizationalUnitCallback.onOuRemoveUser(eventContext, organizationalUnitInfo);
                    case SyncEventConstants.ORGANIZATIONAL_UNIT_UPDATE_ORGANIZATIONAL_UNIT:
                        return this.organizationalUnitCallback.onOuUpdateOrganizationalUnit(eventContext, organizationalUnitInfo);
                    default:
                        eventContext.getLogger().warn("Unknown organizational unit event type: " + eventType);
                }
            }
        }
        // event data not handled, just skip
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by no implementation");
    }

    public void registerUserCallback(UserCallback userCallback) {
        assertNull(this.userCallback, "User callback already registered");
        this.userCallback = userCallback;
    }

    public void registerUserPushCallback(UserPushCallback userPushCallback) {
        assertNull(this.userPushCallback, "User push callback already registered");
        this.userPushCallback = userPushCallback;
    }

    public void registerOrganizationalUnitCallback(OrganizationalUnitCallback organizationalUnitCallback) {
        assertNull(this.organizationalUnitCallback, "Organizational unit callback already registered");
        this.organizationalUnitCallback = organizationalUnitCallback;
    }

    public void registerOrganizationalUnitPushCallback(OrganizationalUnitPushCallback organizationalUnitPushCallback) {
        assertNull(this.organizationalUnitPushCallback, "Organizational unit push callback already registered");
        this.organizationalUnitPushCallback = organizationalUnitPushCallback;
    }

    private void assertNotNull(Object object, String message) {
        if (object == null) {
            throw new RuntimeException(message);
        }
    }

    private void assertNull(Object object, String message) {
        if (object != null) {
            throw new RuntimeException(message);
        }
    }
}
