package io.lana.andrlayoutstarter.booking;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import io.lana.andrlayoutstarter.db.MainDatabase;
import io.lana.andrlayoutstarter.NavigableActivity;
import io.lana.andrlayoutstarter.R;

public class BookingHistoryActivity extends NavigableActivity {
    private BookingHistoryAdapter adapter;
    private BookingDao bookingDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        bookingDao = MainDatabase.getDbInstance(this).bookingDao();
        List<BookingTicket> bookingTickets = bookingDao.getAllBooking();
        adapter = new BookingHistoryAdapter(this, bookingTickets);
        ListView listHistory = findViewById(R.id.list_history);
        listHistory.setAdapter(adapter);
        registerForContextMenu(listHistory);
    }

    @Override
    protected void onResume() {
        adapter.clear();
        adapter.addAll(bookingDao.getAllBooking());
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}