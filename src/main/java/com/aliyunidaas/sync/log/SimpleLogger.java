package com.aliyunidaas.sync.log;

/**
 * 简易日志接口，当在函数计算时适配到 `com.aliyun.fc.runtime.FunctionComputeLogger`
 *
 * @author hatterjiang
 */
public interface SimpleLogger {
    /**
     * 跟踪日志
     */
    void trace(String message);

    /**
     * 调试日志
     */
    void debug(String message);

    /**
     *
     * 信息日志
     */
    void info(String message);

    /**
     * 警告日志
     */
    void warn(String message);

    /**
     * 错误日志
     */
    void error(String message);

    /**
     * 严重错误日志
     */
    void fatal(String message);
}
