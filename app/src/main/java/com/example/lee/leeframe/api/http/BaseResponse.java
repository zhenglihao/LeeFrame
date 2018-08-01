package com.example.lee.leeframe.api.http;

import java.util.List;
/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 网络请求结果封装（根据具体网络请求 做调整）
 */

public class BaseResponse<T> {

    public boolean isSuccess() {
        return "000".equals(header.getCode());  // 此处"000"表示请求成功
    }

    private Header header;

    private T body;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public static class Header {

        private String version;
        private String error;
        private String code;
        private List<FieldError> fieldErrors;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<FieldError> getFieldErrors() {
            return fieldErrors;
        }

        public void setFieldErrors(List<FieldError> fieldErrors) {
            this.fieldErrors = fieldErrors;
        }
    }


    public static class FieldError {
        private String name;
        private String status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}
