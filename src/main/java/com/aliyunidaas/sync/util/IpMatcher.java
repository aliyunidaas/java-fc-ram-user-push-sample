package com.aliyunidaas.sync.util;

/**
 * IP地址（V4、V6）匹配
 *
 * @author hatterjiang
 */
public interface IpMatcher {

    boolean matches(String address);
}
