package com.aliyunidaas.sync.event.callback;

import com.aliyunidaas.sync.event.bizdata.OrganizationalUnitInfo;
import com.aliyunidaas.sync.event.callback.objects.EventDataResponse;
import com.aliyunidaas.sync.event.constants.SyncEventConstants;
import com.aliyunidaas.sync.event.context.EventContext;

/**
 * 全量推送结构机构回调
 *
 * @author hatterjiang
 */
public interface OrganizationalUnitPushCallback {
    /**
     * @see SyncEventConstants#ORGANIZATIONAL_UNIT_PUSH
     */
    default EventDataResponse onOuPush(EventContext eventContext, OrganizationalUnitInfo ouInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }
}
