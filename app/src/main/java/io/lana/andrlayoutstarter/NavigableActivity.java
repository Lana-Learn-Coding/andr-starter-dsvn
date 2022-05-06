package io.lana.andrlayoutstarter;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import io.lana.andrlayoutstarter.booking.BookingHistoryActivity;
import io.lana.andrlayoutstarter.booking.FormActivity;
import io.lana.andrlayoutstarter.booking.SearchDialogFragment;
import io.lana.andrlayoutstarter.service.NewActivity;
import io.lana.andrlayoutstarter.service.ServiceActivity;

public class NavigableActivity extends AppCompatActivity {
    protected void navigate(Context context, Class<?> target) {
        if (getClass().equals(target)) return;
        startActivity(new Intent(context, target));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuitem_booking) {
            navigate(this, FormActivity.class);
            return true;
        }

        if (id == R.id.menuitem_home) {
            navigate(this, MainActivity.class);
            return true;
        }

        if (id == R.id.menuitem_service) {
            navigate(this, ServiceActivity.class);
            return true;
        }

        if (id == R.id.menuitem_news) {
            navigate(this, NewActivity.class);
            return true;
        }

        if (id == R.id.menuitem_booking_history) {
            navigate(this, BookingHistoryActivity.class);
            return true;
        }

        if (id == R.id.menuitem_search) {
            DialogFragment dialogFragment = new SearchDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "history_search");
            return true;
        }

        return false;
    }
}
