package com.aliyunidaas.sample.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyunidaas.sample.constants.EnvConstants;

/**
 * 阿里云客户端工具类
 *
 * @author hatterjiang
 */
public class AliyunClientUtil {

    private static final String ACCESS_KEY_ID_VALUE = System.getenv(EnvConstants.ACCESS_KEY_ID);
    private static final String ACCESS_KEY_SECRET_VALUE = System.getenv(EnvConstants.ACCESS_KEY_SECRET);

    private static IAcsClient client;

    synchronized public static IAcsClient createAcsClient() {
        if (client == null) {
            final DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                    ACCESS_KEY_ID_VALUE, ACCESS_KEY_SECRET_VALUE);
            client = new DefaultAcsClient(profile);
        }
        return client;
    }
}
