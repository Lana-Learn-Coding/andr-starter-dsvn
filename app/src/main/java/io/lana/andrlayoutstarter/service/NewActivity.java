package io.lana.andrlayoutstarter.service;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.lana.andrlayoutstarter.NavigableActivity;
import io.lana.andrlayoutstarter.R;

public class NewActivity extends NavigableActivity {
    private final List<New> news = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        for (int i = 0; i < 10; i++) {
            news.add(new New(getString(R.string.news_lorem_title), getString(R.string.news_lorem_content)));
        }
        ListView listNews = findViewById(R.id.list_news);
        listNews.setAdapter(new NewAdapter(this, news));
    }
}