package com.hta.loto;

import androidx.core.util.Consumer;

import com.google.gson.Gson;
import com.hta.loto.net.HttpNetwork;
import com.hta.loto.net.HttpResult;

public class Controller {

    private static final String base_url_shop = "http://108.61.223.132:8888/";
    private static final String param = "?q=";
    private static final String stattistic = "statistic";
    private static final String suggest = "suggest";


    public static void getCC(String type, Consumer<SuggestResponse> consumer) {
        StringBuilder url = new StringBuilder(base_url_shop);
        url.append(suggest);
        url.append(param);
        url.append(type);
        HttpResult result = HttpNetwork.HttpGetParams(url.toString(), null, null, requestBuilder -> {
        });
        try {
            if (result.isHttpOk()) {
                SuggestResponse responseProduct = new Gson().fromJson(result.response, SuggestResponse.class);
                consumer.accept(responseProduct);
            } else {
                consumer.accept(null);
            }
        } catch (Exception e) {
            consumer.accept(null);
        }
    }

    public static void getStaticstic(String type, Consumer<StattisticResponse> consumer) {
        StringBuilder url = new StringBuilder(base_url_shop);
        url.append(stattistic);
        url.append(param);
        url.append(type);
        HttpResult result = HttpNetwork.HttpGetParams(url.toString(), null, null, requestBuilder -> {
        });
        try {
            if (result.isHttpOk()) {
                StattisticResponse responseProduct = new Gson().fromJson(result.response, StattisticResponse.class);
                consumer.accept(responseProduct);
            } else {
                consumer.accept(null);
            }
        } catch (Exception e) {
            consumer.accept(null);
        }
    }
}
