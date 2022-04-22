package io.lana.andrlayoutstarter.service;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import io.lana.andrlayoutstarter.NavigableActivity;
import io.lana.andrlayoutstarter.R;

public class NewActivity extends NavigableActivity {
    private NewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        adapter = new NewAdapter(this, new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            adapter.add(new New(getString(R.string.news_lorem_title), getString(R.string.news_lorem_content)));
        }
        ListView listNews = findViewById(R.id.list_news);
        listNews.setAdapter(adapter);
        registerForContextMenu(listNews);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.news_menu, menu);

        int pos = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        New newPost = adapter.getItem(pos);
        menu.setHeaderTitle(newPost.getTitle());
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.item_view_all) {
            navigate(this, NewActivity.class);
            return true;
        }

        int pos = ((AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo()).position;
        New item = adapter.getItem(pos);
        if (id == R.id.item_hide) {
            adapter.remove(item);
            adapter.notifyDataSetChanged();
        }


        Toast.makeText(this, item.getTitle() + ": " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
}