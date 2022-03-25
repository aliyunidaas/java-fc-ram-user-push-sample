package com.aliyunidaas.sync.event.callback;

import com.aliyunidaas.sync.event.bizdata.OrganizationalUnitInfo;
import com.aliyunidaas.sync.event.callback.objects.EventDataResponse;
import com.aliyunidaas.sync.event.constants.SyncEventConstants;
import com.aliyunidaas.sync.event.context.EventContext;

/**
 * 增量组织机构回调
 *
 * @author hatterjiang
 */
public interface OrganizationalUnitCallback {
    /**
     * @see SyncEventConstants#ORGANIZATIONAL_UNIT_CREATE
     */
    default EventDataResponse onOuCreate(EventContext eventContext, OrganizationalUnitInfo ouInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }

    /**
     * @see SyncEventConstants#ORGANIZATIONAL_UNIT_DELETE
     */
    default EventDataResponse onOuDelete(EventContext eventContext, OrganizationalUnitInfo ouInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }

    /**
     * @see SyncEventConstants#ORGANIZATIONAL_UNIT_UPDATE
     */
    default EventDataResponse onOuUpdate(EventContext eventContext, OrganizationalUnitInfo ouInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }

    /**
     * @see SyncEventConstants#ORGANIZATIONAL_UNIT_ADD_USER
     */
    default EventDataResponse onOuAddUser(EventContext eventContext, OrganizationalUnitInfo ouInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }

    /**
     * @see SyncEventConstants#ORGANIZATIONAL_UNIT_REMOVE_USER
     */
    default EventDataResponse onOuRemoveUser(EventContext eventContext, OrganizationalUnitInfo ouInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }

    /**
     * @see SyncEventConstants#ORGANIZATIONAL_UNIT_UPDATE_ORGANIZATIONAL_UNIT
     */
    default EventDataResponse onOuUpdateOrganizationalUnit(EventContext eventContext, OrganizationalUnitInfo ouInfo) {
        return EventDataResponse.newSkippedEventDataResponse("auto_skipped", "auto skipped by default implementation");
    }
}
