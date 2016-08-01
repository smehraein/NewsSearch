package com.example.soroushmehraein.newssearch.clients;

import android.content.Context;

import com.example.soroushmehraein.newssearch.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;

/**
 * Author: soroushmehraein
 * Project: NewsSearch
 * Date: 7/28/16
 */
public class NytClient {
    private static NytClient ourInstance = new NytClient();
    private static final String BASE_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

    public static NytClient getInstance() {
        return ourInstance;
    }

    private NytClient() {
    }

    public void getArticlesAsync(Context context, RequestParams params, int page, JsonHttpResponseHandler handler) {
        String api_key = context.getResources().getString(R.string.nyt_api_key);
        params.put("api_key", api_key);
        params.put("page", page);
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(BASE_URL, params, handler);
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) { e.printStackTrace(); }
        return false;
    }
}
