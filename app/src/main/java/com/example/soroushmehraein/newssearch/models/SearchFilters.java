package com.example.soroushmehraein.newssearch.models;

import android.text.TextUtils;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: soroushmehraein
 * Project: NewsSearch
 * Date: 7/28/16
 */
public class SearchFilters {
    public static final List<String> NEWS_DESKS = Arrays.asList("Arts & Leisure", "Business",
            "Culture", "Energy", "Movies", "Opinion", "Politics", "Science", "Technology");

    private String sort;
    private String startDate;
    private ArrayList<String> newsDesks = new ArrayList<>();
    private String query;


    private static SearchFilters ourInstance = new SearchFilters();

    public static SearchFilters getInstance() {
        return ourInstance;
    }

    private SearchFilters() {
    }

    public String getSort() {
        return sort;
    }

    public String getStartDate() {
        return startDate;
    }

    public ArrayList<String> getNewsDesks() {
        return newsDesks;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setNewsDesks(ArrayList<String> newsDesks) {
        this.newsDesks = newsDesks;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public RequestParams toRequestParams() {
        RequestParams params = new RequestParams();
        params.put("q", query);
        params.put("begin_date", startDate);
        params.put("sort", sort);
        params.put("fq", newsDesksQueryString());
        return params;
    }

    private String newsDesksQueryString() {
        if (newsDesks.isEmpty()) {
            return null;
        }
        String desks = TextUtils.join(" ", newsDesks);
        return String.format("news_desk:(%s)", desks);
    }
}
