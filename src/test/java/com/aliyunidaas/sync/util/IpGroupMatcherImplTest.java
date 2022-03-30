package com.aliyunidaas.sync.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class IpGroupMatcherImplTest {

    @Test
    public void test01() {
        IpMatcher ipMatcher = new IpGroupMatcherImpl(new ArrayList<>());

        Assertions.assertTrue(ipMatcher.matches("127.0.0.1"));
        Assertions.assertTrue(ipMatcher.matches("fe80::8c1:5da3:d4da:15f"));
    }

    @Test
    public void test02() {
        IpMatcher ipMatcher = new IpGroupMatcherImpl(Collections.singletonList("127.0.0.0/8"));

        Assertions.assertTrue(ipMatcher.matches("127.0.0.1"));
        Assertions.assertFalse(ipMatcher.matches("fe80::8c1:5da3:d4da:15f"));
    }

    @Test
    public void test03() {
        IpMatcher ipMatcher = new IpGroupMatcherImpl(
                Arrays.asList("127.0.0.0/8", "fe80::8c1:5da3:d4da:15f"));

        Assertions.assertTrue(ipMatcher.matches("127.0.0.1"));
        Assertions.assertTrue(ipMatcher.matches("fe80::8c1:5da3:d4da:15f"));
    }

    @Test
    public void test04() {
        IpMatcher ipMatcher = new IpGroupMatcherImpl(
                Arrays.asList("127.0.0.0/8", "fe80::8c1:5da3:d4da:15f/128"));

        Assertions.assertTrue(ipMatcher.matches("127.0.0.1"));
        Assertions.assertTrue(ipMatcher.matches("fe80::8c1:5da3:d4da:15f"));
    }
}
