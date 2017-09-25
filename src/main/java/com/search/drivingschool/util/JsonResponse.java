package com.search.drivingschool.util;

/**
 * Created by abc on 9/25/2017.
 */


import java.util.Map;


public class JsonResponse {

    public static JsonResponse failureResponse(Error error) {
        JsonResponse response = new JsonResponse();
        response.setStatus(Status.FAIL);
        response.setError(error);
        return response;
    }

    public static JsonResponse failureResponse(Error error, String errorCode) {
        JsonResponse response = new JsonResponse();
        response.setStatus(Status.FAIL);
        response.setError(error);
        response.setErrorCode(errorCode);
        return response;
    }

    public static JsonResponse failureResponse(Error error, String errorCode, Map<String, String> params) {
        JsonResponse response = new JsonResponse();
        response.setStatus(Status.FAIL);
        response.setError(error);
        response.setErrorCode(errorCode);
        response.setResult(params);
        return response;
    }

    public static JsonResponse successResponse(Object... result) {
        JsonResponse response = new JsonResponse();
        response.setStatus(Status.SUCCESS);
        response.setResult(result);
        return response;
    }

    public enum Status {
        SUCCESS, FAIL
    }

    public enum Error {
        SYS_ERROR, NOT_AUTHORISED, BIZ_ERROR, VALIDATION_ERROR
    }

    private Status status;
    private Error error;
    private String errorCode;
    private Object[] result;
    private Map<String, String> cmsLabels;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorMessage) {
        this.errorCode = errorMessage;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Object getResult() {
        if(result==null) {
            return result;
        } else if(result.length==1) {
            return result[0];
        }
        return result;
    }

    public void setResult(Object... result) {
        this.result = result;
    }

    public Map<String, String> getCmsLabels() {
        return cmsLabels;
    }

    public void setCmsLabels(Map<String, String> cmsLabels) {
        this.cmsLabels = cmsLabels;
    }



    /**
     * It wrap status and error in string.
     * result and cmslabels are excluded, please implement it if required.
     */
    public String toString() {
        StringBuffer builder = new StringBuffer("{\"status\":\"" + (status != null ? status.name() : "") + "\"");
        if (status == Status.FAIL) {
            builder.append(",\"error\":\"" + error.name() + "\"");
        }
        builder.append("}");
        return builder.toString();
    }
}
