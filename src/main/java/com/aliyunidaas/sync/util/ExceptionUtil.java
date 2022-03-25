package com.aliyunidaas.sync.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 *
 * @author hatterjiang
 */
public class ExceptionUtil {

    public static String printStacktrace(Throwable t) {
        final StringWriter sw = new StringWriter(1024);
        final PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
}
