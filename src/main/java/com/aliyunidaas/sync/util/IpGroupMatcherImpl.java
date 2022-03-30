package com.aliyunidaas.sync.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hatterjiang
 */
public class IpGroupMatcherImpl implements IpMatcher {
    private List<IpMatcher> ipMatcherList;

    public IpGroupMatcherImpl(List<String> ipList) {
        final List<IpMatcher> ipMatcherList = new ArrayList<>();
        for (String ip : ipList) {
            ipMatcherList.add(new IpMatcherImpl(ip));
        }
        this.ipMatcherList = ipMatcherList;
    }

    @Override
    public boolean matches(String address) {
        if ((ipMatcherList == null) || ipMatcherList.isEmpty()) {
            return true;
        }
        for (IpMatcher ipMatcher : ipMatcherList) {
            if (ipMatcher.matches(address)) {
                return true;
            }
        }
        return false;
    }
}
