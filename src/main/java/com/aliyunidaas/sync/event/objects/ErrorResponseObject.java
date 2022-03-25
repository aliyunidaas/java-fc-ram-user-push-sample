package com.aliyunidaas.sync.event.objects;

/**
 * 事件推送失败处理响应
 *
 * @author hatterjiang
 */
public class ErrorResponseObject implements ResponseObject {
    private String error;
    private String errorDescription;

    public ErrorResponseObject() {
    }

    public ErrorResponseObject(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
