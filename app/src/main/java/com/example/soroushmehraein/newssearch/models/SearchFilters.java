package com.example.soroushmehraein.newssearch.models;

import android.text.TextUtils;

import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Author: soroushmehraein
 * Project: NewsSearch
 * Date: 7/28/16
 */
public class SearchFilters {
    public enum NEWS {
        Business, Movies, Opinion, Politics, Technology
    }

    private String sort;
    private Calendar startDate;
    private HashMap<String, Boolean> newsDesksMap;

    private String query;
    private SimpleDateFormat dateFormatQuery = new SimpleDateFormat("yyyyMMdd", Locale.US);

    private SimpleDateFormat dateFormatHuman = new SimpleDateFormat("MM/dd/yy", Locale.US);
    private static final String SORT_NEWEST = "newest";
    private static final String SORT_OLDEST = "oldest";
    private static SearchFilters ourInstance = new SearchFilters();


    public static SearchFilters getInstance() {
        return ourInstance;
    }

    private SearchFilters() {
        sort = SORT_NEWEST;
        startDate = Calendar.getInstance();
        startDate.add(Calendar.YEAR, -1);
        newsDesksMap = new HashMap<>();
    }

    public String getSort() {
        return sort;
    }

    public Boolean checkNewsDesk(NEWS desk) {
        Boolean result = newsDesksMap.get(desk.name());
        if (result == null) {
            return Boolean.FALSE;
        } else {
            return result;
        }
    }

    public void setNewsDesk(NEWS desk, Boolean filtered) {
        newsDesksMap.put(desk.name(), filtered);
    }

    public Calendar getStartDate() {
        return startDate;
    }

    private String getStartDateQueryString() {
        return dateFormatQuery.format(startDate.getTime());
    }

    public String getStartDateHumanString() {
        return dateFormatHuman.format(startDate.getTime());
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public RequestParams toRequestParams() {
        RequestParams params = new RequestParams();
        params.put("q", query);
        params.put("begin_date", getStartDateQueryString());
        params.put("sort", sort);
        params.put("fq", newsDesksQueryString());
        return params;
    }

    private String newsDesksQueryString() {
        ArrayList<String> newsDesks = new ArrayList<>();
        for (String key : newsDesksMap.keySet()) {
            if (newsDesksMap.get(key)) {
                newsDesks.add(String.format("\"%s\"", key));
            }
        }
        if (newsDesks.isEmpty()) {
            return null;
        }
        String desks = TextUtils.join(" ", newsDesks);
        return String.format("news_desk:(%s)", desks);
    }
}
