package io.lana.andrlayoutstarter.booking;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import io.lana.andrlayoutstarter.NavigableActivity;
import io.lana.andrlayoutstarter.R;
import io.lana.andrlayoutstarter.db.MainDatabase;

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

    private void loadData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<BookingTicket> histories = bookingDao.getAllBooking();
            runOnUiThread(() -> {
                adapter.clear();
                adapter.addAll(histories);
                adapter.notifyDataSetChanged();
            });
        });
    }
}