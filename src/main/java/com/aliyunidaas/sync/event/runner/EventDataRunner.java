package com.aliyunidaas.sync.event.runner;

import com.aliyunidaas.sync.event.callback.EventDataCallback;
import com.aliyunidaas.sync.event.callback.objects.EventDataResponse;
import com.aliyunidaas.sync.event.context.EventContext;
import com.aliyunidaas.sync.event.objects.*;
import com.aliyunidaas.sync.log.DefaultSimpleLogger;
import com.aliyunidaas.sync.log.SimpleLogger;
import com.aliyunidaas.sync.util.*;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;

/**
 * 事件核心处理类，完成了解密、验证、日志、回调处理等工作
 *
 * 正常使用需要提供以下参数：
 * <ul>
 *     <li>appId</li>
 *     <li>jwkUrl</li>
 *     <li>encryptKey</li>
 *     <li>simpleLogger [可选]</li>
 *     <li>eventDataCallback</li>
 * </ul>
 *
 * 具体使用方法参看：com.aliyunidaas.sample.ram.RamFc#innerHandleRequest
 *
 * @author hatterjiang
 * @see "com.aliyunidaas.sample.ram.RamFc#innerHandleRequest"
 */
public class EventDataRunner {
    private String appId;
    private String jwkUrl;
    private String jwkJson;
    private String encryptKey;
    private SimpleLogger simpleLogger;
    private EventDataCallback eventDataCallback;

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setJwkUrl(String jwkUrl) {
        this.jwkUrl = jwkUrl;
    }

    public void setJwkJson(String jwkJson) {
        this.jwkJson = jwkJson;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public void setSimpleLogger(SimpleLogger simpleLogger) {
        this.simpleLogger = simpleLogger;
    }

    public void setEventDataCallback(EventDataCallback eventDataCallback) {
        this.eventDataCallback = eventDataCallback;
    }

    public ResponseObject dispatchEventData(RequestObject requestObject) {
        if (simpleLogger == null) {
            simpleLogger = new DefaultSimpleLogger();
        }
        try {
            return innerDispatchEventData(requestObject);
        } catch (Exception e) {
            simpleLogger.error(
                    "Exception, message: " + e.getMessage() + ", exception: " + ExceptionUtil.printStacktrace(e));
            return new ErrorResponseObject("internal_error", e.getMessage());
        }
    }

    protected ResponseObject innerDispatchEventData(RequestObject requestObject) throws Exception {
        final JwtConsumer jwtConsumer;
        if (StringUtil.isNotEmpty(jwkJson)) {
            jwtConsumer = JwtUtil.createJwtConsumerFromJwkJson(jwkJson, appId);
        } else if (StringUtil.isNotEmpty(jwkUrl)) {
            jwtConsumer = JwtUtil.createJwtConsumerFromUrl(jwkUrl, appId);
        } else {
            throw new IllegalArgumentException("No valid JWK JSON or JWK URL provided");
        }
        final JwtClaims jwtClaims = jwtConsumer.processToClaims(requestObject.getEvent());

        // 注意：这里允许未加密数据，为了安全在部署时需要考虑禁止数据未加密的场景
        final boolean isEncrypted = jwtClaims.getClaimValue("dataEncrypted", Boolean.class);

        final String plainDataJson;
        if (isEncrypted) {
            final String cipherData = jwtClaims.getStringClaimValue("cipherData");
            plainDataJson = JweUtil.decrypt(cipherData, encryptKey);
        } else {
            plainDataJson = JsonUtil.toJson(jwtClaims.getClaimValue("plainData"));
        }

        final SuccessResponseObject successResponseObject = new SuccessResponseObject();

        simpleLogger.debug("Receive sync event: " + plainDataJson);
        final SyncEvent syncEvent = JsonUtil.fromJson(plainDataJson, SyncEvent.class);

        final EventData[] eventDataList = JsonUtil.fromJson(syncEvent.getEventData(), EventData[].class);
        for (EventData eventData : eventDataList) {
            simpleLogger.debug("Process event data: " + JsonUtil.toJson(eventData));
            try {
                final EventContext eventContext = new EventContext();
                eventContext.setLogger(simpleLogger);
                eventContext.setSyncEvent(syncEvent);
                eventContext.setEventData(eventData);
                final EventDataResponse eventDataResponse = eventDataCallback.onEventData(eventContext);
                switch (eventDataResponse.getType()) {
                    case SUCCESS:
                        successResponseObject.getSuccessEvents().add(
                                new ResponseEventObject(eventData.getEventId(), "SUCCESS", "SUCCESS"));
                        break;
                    case FAILED:
                        successResponseObject.getFailedEvents().add(
                                new ResponseEventObject(eventData.getEventId(),
                                        eventDataResponse.getEventCode(), eventDataResponse.getEventMessage()));
                        break;
                    case SKIPPED:
                        successResponseObject.getSkippedEvents().add(
                                new ResponseEventObject(eventData.getEventId(),
                                        eventDataResponse.getEventCode(), eventDataResponse.getEventMessage()));
                        break;
                    case NEED_RETRY:
                        successResponseObject.getRetriedEvents().add(
                                new ResponseEventObject(eventData.getEventId(),
                                        eventDataResponse.getEventCode(), eventDataResponse.getEventMessage()));
                        break;
                    default:
                        simpleLogger.error("Unknown event data response type: " + eventDataResponse.getType());
                        throw new RuntimeException("Unknown event data response type: " + eventDataResponse.getType());
                }
            } catch (Exception e) {
                simpleLogger.error(
                        "Exception, event data: " + JsonUtil.toJson(eventData) + ", exception: " + ExceptionUtil.printStacktrace(e));
                successResponseObject.getRetriedEvents().add(
                        new ResponseEventObject(eventData.getEventId(), "exception", e.getMessage()));
            }
        }
        return successResponseObject;
    }
}
