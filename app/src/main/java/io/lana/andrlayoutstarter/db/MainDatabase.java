package io.lana.andrlayoutstarter.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import io.lana.andrlayoutstarter.booking.BookingDao;
import io.lana.andrlayoutstarter.booking.BookingTicket;

@Database(entities = {BookingTicket.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MainDatabase extends RoomDatabase {
    private static final String DBNAME = "booking";

    public abstract BookingDao bookingDao();

    public static MainDatabase getDbInstance(Context context) {
        return Room.databaseBuilder(context, MainDatabase.class, DBNAME).build();
    }
}
