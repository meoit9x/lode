package com.hta.loto.net;


import android.os.Build;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;

public class HttpNetwork {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static String getUserAgent() {
        return String.format("Mozilla/5.0 (Linux; Android %s; %s Build/%s) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Mobile Safari/537.36",
                Build.VERSION.RELEASE, Build.MODEL, Build.HARDWARE);
    }

    public static HttpResult HttpGet(String url) {
//        Log.d("TEST", "URL: " + url);
        return HttpGet(url, null, null);
    }

    public static HttpResult HttpGet(String url, CookieStore cookieStore, String referer) {
        return HttpGet(url, cookieStore, referer, null);
    }

    public static HttpResult HttpGet(String url, CookieStore cookieStore, String referer, OnAddHeader onAddHeader) {
        OkHttpClient okHttpClient = getHttpDefault(cookieStore).build();
        Request.Builder builder = getRequestDefault(referer);
        if (onAddHeader != null) {
            onAddHeader.addHeader(builder);
        }
        builder.url(url);
        return execute(okHttpClient, builder.build());
    }

    public static HttpResult HttpGetParams(String url, CookieStore cookieStore, Map<String, String> params, OnAddHeader onAddHeader) {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }

        OkHttpClient okHttpClient = getHttpDefault(cookieStore).build();
        Request.Builder builder = getRequestDefault(null);
        if (onAddHeader != null) {
            onAddHeader.addHeader(builder);
        }
        builder.url(httpBuilder.build());
        return execute(okHttpClient, builder.build());
    }

    public static HttpResult HttpPostForm(String url, CookieStore cookieStore, Map<String, String> data, String referer, OnAddHeader onAddHeader) {
        OkHttpClient okHttpClient = getHttpDefault(cookieStore).build();
        Request.Builder b = getRequestDefault(referer);
        if (onAddHeader != null) {
            onAddHeader.addHeader(b);
        }
        FormBody.Builder body = new FormBody.Builder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(val)) continue;
            body.add(entry.getKey(), entry.getValue());
        }
        b.url(url);
        b.post(body.build());
        return execute(okHttpClient, b.build());
    }

    public static HttpResult HttpPostJson(String url, CookieStore cookieStore, String jsData, String referer, OnAddHeader onAddHeader) {
        OkHttpClient okHttpClient = getHttpDefault(cookieStore)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).build();
        Request.Builder b = getRequestDefault(referer);
        if (onAddHeader != null) {
            onAddHeader.addHeader(b);
        }
        b.url(url);
        if (jsData != null) {
            RequestBody js = RequestBody.create(JSON, jsData);
            b.post(js);
        }
        return execute(okHttpClient, b.build());
    }

    public static HttpResult HttpDownloadFile(String url, File fileName) {
        return HttpDownloadFile(url, fileName, null, null, null);
    }

    public static HttpResult HttpDownloadFile(String url, File fileSave, CookieStore cookieStore, String referer, OnAddHeader onAddHeader) {
        OkHttpClient okHttpClient = getHttpDefault(cookieStore).build();
        Request.Builder builder = new Request.Builder()
                .url(url);
        if (onAddHeader != null) {
            onAddHeader.addHeader(builder);
        }
        return download(okHttpClient, builder.build(), fileSave);
    }

    private static OkHttpClient.Builder getHttpDefault(CookieStore cookieStore) {
        OkHttpClient.Builder ok = new OkHttpClient.Builder();
        if (cookieStore != null)
            ok.cookieJar(cookieStore);
        return ok;
    }

    private static Request.Builder getRequestDefault(String referer) {
        Request.Builder b = new Request.Builder()
                .header("User-Agent", getUserAgent())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        if (referer != null)
            b.addHeader("Referer", referer);
        return b;
    }

    private static HttpResult execute(OkHttpClient httpClient, Request request) {
        Response res = null;
        HttpResult ret = new HttpResult();
        try {
            res = httpClient.newCall(request).execute();
            ret.code = res.code();
            ret.response = res.body().string();
            ret.headerLocaltion = res.request().url().toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (res != null) {
                bodyClose(res.body());
            }
        }
        return ret;
    }

    private static HttpResult download(OkHttpClient httpClient, Request request, File downloadedFile) {
        Response res = null;
        HttpResult ret = new HttpResult();
        try {
            res = httpClient.newCall(request).execute();
            BufferedSink sink = Okio.buffer(Okio.sink(downloadedFile));
            sink.writeAll(res.body().source());
            sink.close();
            ret.code = res.code();
            ret.headerLocaltion = res.request().url().toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (res != null) {
                bodyClose(res.body());
            }
        }
        return ret;
    }

    public static void bodyClose(ResponseBody body) {
        try {
            body.close();
        } catch (final Exception ex) {

        }
    }

    public interface OnAddHeader {
        void addHeader(Request.Builder requestBuilder);
    }

    interface OnRequest {
        HttpResult onRequest();
    }

    public static HttpResult retryLoad(OnRequest onRequest) {
        int retry = 3;
        do {
            HttpResult o = onRequest.onRequest();
            if (o.code != -1) return o;
        } while ((--retry) >= 0);
        return new HttpResult();
    }
}
