package com.hta.loto.net;

public class HttpResult {
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getResponse() {
        return response;
    }
    public void setResponse(String response) {
        this.response = response;
    }
    public String getHeaderLocaltion() {
        return headerLocaltion;
    }
    public void setHeaderLocaltion(String headerLocaltion) {
        this.headerLocaltion = headerLocaltion;
    }
    public String getHeaderCookie() {
        return headerCookie;
    }
    public void setHeaderCookie(String headerCookie) {
        this.headerCookie = headerCookie;
    }
    public String getReferer() {
        return referer;
    }
    public void setReferer(String referer) {
        this.referer = referer;
    }
    public int code;
    public String response;
    public String headerLocaltion;
    public String headerCookie;
    public String referer;
    public HttpResult() {
        code = -1;
        response = "";
    }

    public boolean isHttpOk() {
        return code == 200;
    }
}
