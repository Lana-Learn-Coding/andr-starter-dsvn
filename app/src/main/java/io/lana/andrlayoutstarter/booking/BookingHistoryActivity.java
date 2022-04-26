package io.lana.andrlayoutstarter.booking;

import android.os.Bundle;
import android.widget.ListView;

import com.github.javafaker.Faker;

import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import io.lana.andrlayoutstarter.MainApplication;
import io.lana.andrlayoutstarter.NavigableActivity;
import io.lana.andrlayoutstarter.R;

public class BookingHistoryActivity extends NavigableActivity {
    private BookingHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        MainApplication application = (MainApplication) getApplicationContext();

        adapter = new BookingHistoryAdapter(this, application.getTickets());
        if (application.getTickets().size() == 0) {
            for (int i = 0; i < 3; i++) {
                BookingTicket ticket = new BookingTicket();
                ticket.setId("DSVN" + Faker.instance().number().digits(8));
                ticket.setName(Faker.instance().name().fullName());
                ticket.setPhone(Faker.instance().phoneNumber().phoneNumber());
                ticket.setFrom(Faker.instance().options().nextElement(application.getCities()));
                ticket.setTo(Faker.instance().options().nextElement(application.getCities()));
                ticket.setOneWay(Faker.instance().bool().bool());
                ticket.setStartDate(Faker.instance().date().future(5, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                ticket.setEndDate(Faker.instance().date().future(20, 8, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                ticket.setChildrenCount(Faker.instance().number().numberBetween(1, 5));
                ticket.setPeopleCount(Faker.instance().number().numberBetween(1, 5));
                adapter.add(ticket);
            }
        }
        ListView listHistory = findViewById(R.id.list_history);
        listHistory.setAdapter(adapter);
        registerForContextMenu(listHistory);
    }

    @Override
    protected void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}