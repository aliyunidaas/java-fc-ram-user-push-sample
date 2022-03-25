package com.aliyunidaas.sync.log;

/**
 * 默认日志，记录到 stdout，在函数计算下使用类 com.aliyunidaas.fc.log.FcSimpleLogger
 *
 * @author hatterjiang
 */
public class DefaultSimpleLogger implements SimpleLogger {
    @Override
    public void trace(String message) {
        System.out.println("[TRACE] " + message);
    }

    @Override
    public void debug(String message) {
        System.out.println("[DEBUG] " + message);
    }

    @Override
    public void info(String message) {
        System.out.println("[INFO] " + message);
    }

    @Override
    public void warn(String message) {
        System.out.println("[WARN] " + message);
    }

    @Override
    public void error(String message) {
        System.out.println("[ERROR] " + message);
    }

    @Override
    public void fatal(String message) {
        System.out.println("[FATAL] " + message);
    }
}
