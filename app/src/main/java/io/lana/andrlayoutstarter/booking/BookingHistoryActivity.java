package io.lana.andrlayoutstarter.booking;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import io.lana.andrlayoutstarter.FormUtils;
import io.lana.andrlayoutstarter.NavigableActivity;
import io.lana.andrlayoutstarter.R;
import io.lana.andrlayoutstarter.db.MainDatabase;
import io.lana.andrlayoutstarter.service.NewActivity;

public class BookingHistoryActivity extends NavigableActivity {
    private BookingHistoryAdapter adapter;
    private BookingDao bookingDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        bookingDao = MainDatabase.getDbInstance(this).bookingDao();
        adapter = new BookingHistoryAdapter(this, new ArrayList<>());
        ListView listHistory = findViewById(R.id.list_history);
        listHistory.setAdapter(adapter);
        registerForContextMenu(listHistory);
    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.booking_history_menu, menu);

        int pos = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        BookingTicket newPost = adapter.getItem(pos);
        menu.setHeaderTitle(newPost.getId());
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.item_view_all) {
            navigate(this, NewActivity.class);
            return true;
        }

        int pos = ((AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo()).position;
        BookingTicket item = adapter.getItem(pos);
        if (id == R.id.item_delete) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure delete?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Executors.newSingleThreadExecutor().execute(() -> {
                            bookingDao.delete(item);
                            runOnUiThread(() -> {
                                adapter.remove(item);
                                adapter.notifyDataSetChanged();
                            });
                        });
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show())
                    .show();
            return true;
        }

        Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void loadData() {
        Bundle extras = ObjectUtils.defaultIfNull(getIntent().getExtras(), new Bundle());
        Executors.newSingleThreadExecutor().execute(() -> {
            String term = StringUtils.defaultIfBlank(extras.getString("term"), "");

            LocalDate date = StringUtils.isNotBlank(extras.getString("date"))
                    ? LocalDate.parse(extras.getString("date"), FormUtils.SIMPLE_DATE_FORMAT)
                    : null;

            List<BookingTicket> histories = bookingDao.getBooking("%" + term + "%", date);
            runOnUiThread(() -> {
                adapter.clear();
                adapter.addAll(histories);
                adapter.notifyDataSetChanged();
            });
        });
    }
}