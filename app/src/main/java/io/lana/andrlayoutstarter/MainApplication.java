package io.lana.andrlayoutstarter;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import io.lana.andrlayoutstarter.booking.BookingTicket;

public class MainApplication extends Application {
    private final List<String> cities = new ArrayList<String>() {{
        add("Hà Nội");
        add("TP Hồ Chí Minh");
        add("Mỹ");
    }};

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public List<String> getCities() {
        return cities;
    }
}
