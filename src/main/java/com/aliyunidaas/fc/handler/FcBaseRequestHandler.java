package com.aliyunidaas.fc.handler;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.HttpRequestHandler;
import com.aliyunidaas.fc.util.ServletUtil;
import com.aliyunidaas.sync.event.objects.ErrorResponseObject;
import com.aliyunidaas.sync.event.objects.RequestObject;
import com.aliyunidaas.sync.event.objects.ResponseObject;
import com.aliyunidaas.sync.event.objects.SuccessResponseObject;
import com.aliyunidaas.sync.util.ExceptionUtil;
import com.aliyunidaas.sync.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基础函数计算处理Handler
 *
 * @author hatterjiang
 * @see com.aliyunidaas.sync.event.runner.EventDataRunner
 */
public abstract class FcBaseRequestHandler implements HttpRequestHandler {

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, Context context)
            throws IOException, ServletException {
        final FunctionComputeLogger logger = context.getLogger();
        String requestBody = null;
        try {
            requestBody = ServletUtil.readRequestBody(request);
            if ((requestBody == null) || requestBody.trim().isEmpty()) {
                final ErrorResponseObject errorResponseObject = new ErrorResponseObject();
                errorResponseObject.setError("bad_request");
                errorResponseObject.setErrorDescription("Empty request body");
                ServletUtil.writeErrorResponse(response, logger, 400, errorResponseObject);
                return;
            }
            final RequestObject requestObject = JsonUtil.fromJson(requestBody, RequestObject.class);
            logger.debug("Receive event object: " + JsonUtil.toJson(requestObject));

            final ResponseObject responseObject = innerHandleRequest(context, requestObject);
            if (responseObject instanceof SuccessResponseObject) {
                ServletUtil.writeSuccessResponse(response, logger, (SuccessResponseObject)responseObject);
            } else {
                ServletUtil.writeErrorResponse(response, logger, 500, (ErrorResponseObject)responseObject);
            }
        } catch (Throwable t) {
            logger.error("Unknown error: " + t.getMessage()
                    + ", requestURI: " + request.getRequestURI()
                    + ", requestBody: " + requestBody
                    + " :: " + ExceptionUtil.printStacktrace(t));

            final ErrorResponseObject errorResponseObject = new ErrorResponseObject();
            errorResponseObject.setError("internal_error");
            errorResponseObject.setErrorDescription("Error: " + t.getMessage());
            ServletUtil.writeErrorResponse(response, logger, 500, errorResponseObject);
        }
    }

    protected abstract ResponseObject innerHandleRequest(Context context, RequestObject requestObject) throws Exception;
}