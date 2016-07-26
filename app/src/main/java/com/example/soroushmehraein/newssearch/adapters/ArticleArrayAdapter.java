package com.example.soroushmehraein.newssearch.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.soroushmehraein.newssearch.R;
import com.example.soroushmehraein.newssearch.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Author: soroushmehraein
 * Project: NewsSearch
 * Date: 7/26/16
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    public ArticleArrayAdapter(Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = this.getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivImage);
        imageView.setImageResource(0);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(article.getHeadline());

        String thumbnail = article.getThumbnail();

        if (!TextUtils.isEmpty(thumbnail)) {
            Picasso.with(getContext()).load(thumbnail).into(imageView);
        }

        return convertView;

    }
}
