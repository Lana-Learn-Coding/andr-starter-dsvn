package io.lana.andrlayoutstarter.booking;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookingDao {
    @Query("SELECT * FROM booking")
    List<BookingTicket> getAllBooking();

    @Insert
    void insert(BookingTicket... bookingTickets);
}
