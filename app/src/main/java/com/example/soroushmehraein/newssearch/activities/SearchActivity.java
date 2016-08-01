package com.example.soroushmehraein.newssearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.soroushmehraein.newssearch.EndlessScrollListener;
import com.example.soroushmehraein.newssearch.R;
import com.example.soroushmehraein.newssearch.adapters.ArticleArrayAdapter;
import com.example.soroushmehraein.newssearch.clients.NytClient;
import com.example.soroushmehraein.newssearch.models.Article;
import com.example.soroushmehraein.newssearch.models.SearchFilters;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    public static String INTENT_ARTICLE = "article";
    public static String INTENT_FILTER = "filter";
    public static int FILTER_CODE = 100;


    EditText etQuery;
    GridView gvResults;
    Button btnSearch;
    SearchFilters filters;

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        filters = new SearchFilters();

        setupViews();
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);

                Article article = articles.get(position);
                intent.putExtra(INTENT_ARTICLE, article);

                startActivity(intent);
            }
        });

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                searchForArticles(page);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onArticleSearch(View view) {
        adapter.clear();
        searchForArticles(0);
    }

    private void searchForArticles(int page) {
        String query = etQuery.getText().toString();

        filters.setQuery(query);

        NytClient nytClient = NytClient.getInstance();

        if (!nytClient.isOnline()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }

        nytClient.getArticlesAsync(this, filters.toRequestParams(), page, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleJsonResults;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    adapter.addAll(Article.fromJsonArray(articleJsonResults));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showFilters(MenuItem item) {
        Intent intent = new Intent(SearchActivity.this, FilterActivity.class);
        intent.putExtra(INTENT_FILTER, filters);
        startActivityForResult(intent, FILTER_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILTER_CODE) {
            if (resultCode == RESULT_OK) {
                filters = data.getParcelableExtra(INTENT_FILTER);
            }
        }
    }
}
