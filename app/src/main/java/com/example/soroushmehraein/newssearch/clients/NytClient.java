package com.example.soroushmehraein.newssearch.clients;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Author: soroushmehraein
 * Project: NewsSearch
 * Date: 7/28/16
 */
public class NytClient {
    private static NytClient ourInstance = new NytClient();
    private static final String API_KEY = "d461aaa978be421bbe5990fda1d8cd53";
    private static final String BASE_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

    public static NytClient getInstance() {
        return ourInstance;
    }

    private NytClient() {
    }

    public void getArticlesAsync(RequestParams params, JsonHttpResponseHandler handler) {
        params.put("api_key", API_KEY);
        params.put("page", 0);
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(BASE_URL, params, handler);
    }
}
