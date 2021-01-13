package com.hta.loto.net;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieStore implements CookieJar {
    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private final HashMap<String, List<Cookie>> cookieMore = new HashMap<>();
    public CookieStore() {

    }

    public HashMap<String, List<Cookie>> getCookieStore() {
        return cookieStore;
    }
    public void addCookie(String host, Cookie cookie) {
        List<Cookie> ls = cookieStore.get(host);
        if(ls == null) {
            ls = new ArrayList<>();
            cookieStore.put(host, ls);
        }
        ls.add(cookie);
    }
    public void addMoreCookie(String host, Cookie cookie) {
        List<Cookie> ls = cookieMore.get(host);
        if(ls == null) {
            ls = new ArrayList<>();
            cookieMore.put(host, ls);
        }
        for (Cookie c : ls) {
            if(c.name().equals(cookie.name())) {
                ls.remove(c);
                break;
            }
        }
        ls.add(cookie);
    }
    private List<Cookie> getMoreCookie(String host) {
        return cookieMore.get(host);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        List<Cookie> list = cookieStore.get(url.host());
        if(list == null) {
            list = new ArrayList<>(cookies);
            cookieStore.put(url.host(), list);
        } else {
            for(int i = 0; i < cookies.size(); i++) {
                Cookie c = cookies.get(i);
                for (Cookie c2 : list) {
                    if(c2.name().equals(c.name())) {
                        list.remove(c2);
                        break;
                    }
                }
                list.add(c);
            }
        }
//        cookieStore.put(url.host(), cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url.host());
        List<Cookie> cMore = cookieMore.get(url.host());
        if(cMore != null) {
            if(cookies == null) cookies = cMore;
            else {
                cookies = new ArrayList<>(cookies);
                for (Cookie c : cMore) {
                    cookies.add(c);
                }
            }
        }
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }
}
