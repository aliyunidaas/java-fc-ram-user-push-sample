package com.aliyunidaas.sample.ram;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.ram.model.v20150501.*;
import com.aliyunidaas.sample.constants.EnvConstants;
import com.aliyunidaas.sample.util.AliyunClientUtil;
import com.aliyunidaas.sync.event.bizdata.UserInfo;
import com.aliyunidaas.sync.event.callback.UserCallback;
import com.aliyunidaas.sync.event.callback.UserPushCallback;
import com.aliyunidaas.sync.event.callback.impl.DefaultEventDataCallbackImpl;
import com.aliyunidaas.sync.event.callback.objects.EventDataResponse;
import com.aliyunidaas.sync.event.context.EventContext;
import com.aliyunidaas.sync.event.objects.RequestObject;
import com.aliyunidaas.sync.event.objects.ResponseObject;
import com.aliyunidaas.sync.event.runner.EventDataRunner;
import com.aliyunidaas.sync.fc.handler.FcBaseRequestHandler;
import com.aliyunidaas.sync.fc.log.FcSimpleLogger;
import com.aliyunidaas.sync.internal.util.*;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 详细说明请参看 README.md
 *
 * @see "https://help.aliyun.com/document_detail/408976.html"
 */
public class RamFc extends FcBaseRequestHandler {
    private static final String JWK_URL_VALUE = System.getenv(EnvConstants.JWK_URL);
    private static final String JWK_JSON_VALUE = System.getenv(EnvConstants.JWK_JSON);
    private static final String APP_ID_VALUE = System.getenv(EnvConstants.APP_ID);
    private static final String KEY_VALUE = System.getenv(EnvConstants.KEY);
    private static final String ALLOWED_IPS_VALUE = System.getenv(EnvConstants.ALLOWED_IPS);

    @Override
    protected IpMatcher getIpMatcher(FunctionComputeLogger logger) {
        final List<String> allowedIps = StringUtil.splitToList(ALLOWED_IPS_VALUE);
        if (allowedIps == null) {
            return null;
        }
        logger.debug("Allowed IP list: " + allowedIps);
        return new IpGroupMatcherImpl(allowedIps);
    }

    @Override
    protected ResponseObject innerHandleRequest(Context context, RequestObject requestObject) throws Exception {
        final FunctionComputeLogger logger = context.getLogger();
        final IAcsClient client = AliyunClientUtil.createAcsClient();

        final EventDataRunner eventDataRunner = new EventDataRunner();
        eventDataRunner.setEncryptKey(KEY_VALUE);
        eventDataRunner.setJwkUrl(JWK_URL_VALUE);
        eventDataRunner.setJwkJson(JWK_JSON_VALUE);
        eventDataRunner.setAppId(APP_ID_VALUE);
        eventDataRunner.setSimpleLogger(new FcSimpleLogger(context.getLogger()));

        final DefaultEventDataCallbackImpl defaultEventDataCallback = new DefaultEventDataCallbackImpl();
        eventDataRunner.setEventDataCallback(defaultEventDataCallback);
        // 注册用户推送回调
        defaultEventDataCallback.registerUserPushCallback(new UserPushCallback() {
            @Override
            public EventDataResponse onUserPush(EventContext eventContext, UserInfo userInfo) {
                syncUser(client, logger, userInfo);
                return EventDataResponse.newSuccessEventDataResponse();
            }
        });
        // 注册用户增量回调，注意这里只处理了用户创建和更新，根据实际需求可以 Override 需要的函数
        defaultEventDataCallback.registerUserCallback(new UserCallback() {
            @Override
            public EventDataResponse onUserCreate(EventContext eventContext, UserInfo userInfo) {
                syncUser(client, logger, userInfo);
                return EventDataResponse.newSuccessEventDataResponse();
            }

            @Override
            public EventDataResponse onUserUpdateInfo(EventContext eventContext, UserInfo userInfo) {
                syncUser(client, logger, userInfo);
                // 根据实际情况这里返回 SKIPPED, FAILED, NEED_RETRY 不同类型的响应结果
                return EventDataResponse.newSuccessEventDataResponse();
            }
        });

        return eventDataRunner.dispatchEventData(requestObject);
    }

    private static void syncUser(IAcsClient client, FunctionComputeLogger logger, UserInfo userInfo) {
        try {
            innerSyncUser(client, logger, userInfo);
        } catch (Exception e) {
            throw new RuntimeException("Sync user failed: " + e.getMessage() + ", user info: " + JsonUtil.toJson(userInfo), e);
        }
    }

    private static void innerSyncUser(IAcsClient client, FunctionComputeLogger logger, UserInfo userInfo) {
        final String displayName = StringUtils.defaultString(StringUtil.filterNone1CodePlane(userInfo.getDisplayName()));
        final String userInfoPhoneNumber = StringUtils.isNotEmpty(userInfo.getPhoneNumber())
                ? (userInfo.getPhoneRegion() + "-" + userInfo.getPhoneNumber()) : StringUtils.EMPTY;
        String email = StringUtils.defaultString(userInfo.getEmail());
        userInfo.setDisplayName(displayName);

        final GetUserResponse.User user = findUserByUsername(client, logger, userInfo.getUsername());
        if (user == null) {
            // 用户不存在，则创建用户
            createUser(client, logger, userInfo);
            return;
        }
        // 如果用户已经存在，则判断用户是修改用户信息：显示名、邮箱、手机号
        final boolean isDisplayNameEquals = StringUtils.equals(user.getDisplayName(), displayName);
        final boolean isPhoneNumberEquals = StringUtils.equals(user.getMobilePhone(), userInfoPhoneNumber);
        final boolean isEmailEquals = StringUtils.equals(user.getEmail(), email);
        final boolean isUserMatches = isDisplayNameEquals && isPhoneNumberEquals && isEmailEquals;
        if (!isUserMatches) {
            updateUser(client, logger, userInfo);
        } else {
            logger.debug("User do not need to be updated: " + userInfo.getUsername());
        }
    }

    private static void updateUser(IAcsClient client, FunctionComputeLogger logger, UserInfo userInfo) {
        final UpdateUserRequest request;
        request = new UpdateUserRequest();
        request.setSysRegionId("cn-hangzhou");
        request.setUserName(userInfo.getUsername());
        request.setNewUserName(userInfo.getUsername());
        request.setNewDisplayName(userInfo.getDisplayName());
        if (StringUtils.isNotEmpty(userInfo.getPhoneNumber())) {
            request.setNewMobilePhone(userInfo.getPhoneRegion() + "-" + userInfo.getPhoneNumber());
        } else {
            request.setNewMobilePhone("");
        }
        request.setNewEmail(userInfo.getEmail());

        try {
            final UpdateUserResponse response = client.getAcsResponse(request);
            logger.debug("Update user response: " + new Gson().toJson(response));
        } catch (ServerException e) {
            throw new RuntimeException("Update user failed: " + userInfo.getUsername() + ", Request: " + JsonUtil.toJson(request), e);
        } catch (ClientException e) {
            logger.error("Username: " + userInfo.getUsername()
                    + ", ErrCode:" + e.getErrCode()
                    + ", ErrMsg:" + e.getErrMsg()
                    + ", RequestId:" + e.getRequestId()
                    + ", Request: " + JsonUtil.toJson(request)
                    + ", Exception: " + ExceptionUtil.printStacktrace(e));
            throw new RuntimeException("Update user failed[Client]: " + userInfo.getUsername(), e);
        }
    }

    private static void createUser(IAcsClient client, FunctionComputeLogger logger, UserInfo userInfo) {
        final CreateUserRequest request = new CreateUserRequest();
        request.setSysRegionId("cn-hangzhou");
        request.setUserName(userInfo.getUsername());
        request.setDisplayName(userInfo.getDisplayName());
        if (StringUtils.isNotEmpty(userInfo.getPhoneNumber())) {
            request.setMobilePhone(userInfo.getPhoneRegion() + "-" + userInfo.getPhoneNumber());
        }
        request.setEmail(userInfo.getEmail());
        request.setComments("Created by FC-RAM");
        try {
            final CreateUserResponse response = client.getAcsResponse(request);
            logger.debug("Create user response: " + new Gson().toJson(response));
        } catch (ServerException e) {
            throw new RuntimeException("Create user failed: " + userInfo.getUsername() + ", Request: " + JsonUtil.toJson(request), e);
        } catch (ClientException e) {
            logger.error("Username: " + userInfo.getUsername()
                    + ", ErrCode:" + e.getErrCode()
                    + ", ErrMsg:" + e.getErrMsg()
                    + ", RequestId:" + e.getRequestId()
                    + ", Request: " + JsonUtil.toJson(request)
                    + ", Exception: " + ExceptionUtil.printStacktrace(e));
            throw new RuntimeException("Create user failed[Client]: " + userInfo.getUsername(), e);
        }
    }

    private static GetUserResponse.User findUserByUsername(IAcsClient client, FunctionComputeLogger logger, String username) {
        final GetUserRequest request = new GetUserRequest();
        request.setSysRegionId("cn-hangzhou");
        request.setUserName(username);
        try {
            final GetUserResponse response = client.getAcsResponse(request);
            return response.getUser();
        } catch (ServerException e) {
            throw new RuntimeException("Get user failed: " + username + ", Request: " + JsonUtil.toJson(request), e);
        } catch (ClientException e) {
            if ("EntityNotExist.User".equals(e.getErrCode())) {
                return null;
            }
            logger.error("Username: " + username
                    + ", ErrCode:" + e.getErrCode()
                    + ", ErrMsg:" + e.getErrMsg()
                    + ", RequestId:" + e.getRequestId()
                    + ", Request: " + JsonUtil.toJson(request)
                    + ", Exception: " + ExceptionUtil.printStacktrace(e));
            throw new RuntimeException("Find user failed[Client]: " + username, e);
        }
    }
}