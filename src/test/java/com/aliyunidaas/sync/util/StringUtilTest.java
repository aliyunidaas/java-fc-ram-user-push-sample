package com.aliyunidaas.sync.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author hatterjiang
 */
public class StringUtilTest {
    @Test
    public void testSplitToList() {
        Assertions.assertNull(StringUtil.splitToList(null));
        Assertions.assertNull(StringUtil.splitToList(""));
        Assertions.assertNull(StringUtil.splitToList(",,,"));
        Assertions.assertEquals(
                Arrays.asList("127.0.0.1/32", "10.0.0.0/8"),
                StringUtil.splitToList("127.0.0.1/32,  10.0.0.0/8"));
    }
}
