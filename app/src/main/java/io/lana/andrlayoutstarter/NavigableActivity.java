package io.lana.andrlayoutstarter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NavigableActivity extends AppCompatActivity {
    protected View.OnClickListener navigate(Context context, Class<?> target) {
        return (v) -> {
            startActivity(new Intent(context, target));
            finish();
        };
    }

    protected Button findButtonById(int id) {
        return (Button) findViewById(id);
    }

    protected void setupNavigation() {
        findButtonById(R.id.button_home).setOnClickListener(navigate(this, MainActivity.class));
        findButtonById(R.id.button_service).setOnClickListener(navigate(this, ServiceActivity.class));
        findButtonById(R.id.button_form).setOnClickListener(navigate(this, FormActivity.class));
    }
}
