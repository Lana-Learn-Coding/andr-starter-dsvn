package io.lana.andrlayoutstarter.service;

import android.os.Bundle;
import android.widget.ListView;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

import io.lana.andrlayoutstarter.NavigableActivity;
import io.lana.andrlayoutstarter.R;

public class ServiceActivity extends NavigableActivity {
    private final List<New> news = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);


        news.add(new New(Faker.instance().book().title(), Faker.instance().chuckNorris().fact()));
        news.add(new New(Faker.instance().book().title(), Faker.instance().chuckNorris().fact()));
        news.add(new New(Faker.instance().book().title(), Faker.instance().chuckNorris().fact()));
        ListView listNews = findViewById(R.id.list_news);
        listNews.setAdapter(new NewAdapter(this, news));
    }
}