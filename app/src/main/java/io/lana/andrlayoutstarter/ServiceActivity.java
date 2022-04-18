package io.lana.andrlayoutstarter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ServiceActivity extends NavigableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        setupNavigation();
    }
}