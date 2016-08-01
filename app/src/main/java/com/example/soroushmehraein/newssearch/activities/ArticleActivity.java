package com.example.soroushmehraein.newssearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.soroushmehraein.newssearch.R;
import com.example.soroushmehraein.newssearch.models.Article;

public class ArticleActivity extends AppCompatActivity {

    WebView wvArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Article article = getIntent().getParcelableExtra(SearchActivity.INTENT_ARTICLE);

        wvArticle = (WebView) findViewById(R.id.wvArticle);

        wvArticle.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (wvArticle != null) {
            wvArticle.loadUrl(article.getWebUrl());
        }
    }

}
