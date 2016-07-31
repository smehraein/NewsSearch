package com.example.soroushmehraein.newssearch.models;

import android.text.TextUtils;

import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Author: soroushmehraein
 * Project: NewsSearch
 * Date: 7/28/16
 */
public class SearchFilters {
    public static final List<String> NEWS_DESKS = Arrays.asList("Arts & Leisure", "Business",
            "Culture", "Energy", "Movies", "Opinion", "Politics", "Science", "Technology");

    private String sort;
    private Calendar startDate;
    private ArrayList<String> newsDesks;
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
        newsDesks = new ArrayList<>();
        sort = SORT_NEWEST;
        startDate = Calendar.getInstance();
        startDate.add(Calendar.YEAR, -1);
    }

    public String getSort() {
        return sort;
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

    public ArrayList<String> getNewsDesks() {
        return newsDesks;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public void setNewsDesks(ArrayList<String> newsDesks) {
        ArrayList<String> formattedNewsDesks = new ArrayList<>();
        for (int i = 0; i < newsDesks.size(); i++) {
            formattedNewsDesks.add("\"" + newsDesks.get(i) + "\"");
        }
        this.newsDesks = formattedNewsDesks;
    }

    public void addNewsDesk(String newsDesk) {
        String formattedNewsDesk = "\"" + newsDesk + "\"";
        newsDesks.add(formattedNewsDesk);
    }

    public void removeNewsDesk(int position) {
        newsDesks.remove(position);
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
        if (newsDesks.isEmpty()) {
            return null;
        }
        String desks = TextUtils.join(" ", newsDesks);
        return String.format("news_desk:(%s)", desks);
    }
}
