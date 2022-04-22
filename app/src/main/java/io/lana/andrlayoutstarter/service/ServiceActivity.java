package io.lana.andrlayoutstarter.service;

import android.os.Bundle;
import android.widget.ListView;

import io.lana.andrlayoutstarter.NavigableActivity;
import io.lana.andrlayoutstarter.R;

public class ServiceActivity extends NavigableActivity {
    private final New loremNew = new New(getString(R.string.news_lorem_title), getString(R.string.news_lorem_content));

    private final New[] news = new New[]{loremNew, loremNew};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);


        ListView listNews = findViewById(R.id.list_news);
        listNews.setAdapter(new NewAdapter(this, news));
    }
}