package io.lana.andrlayoutstarter.booking;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookingDao {
    @Query("SELECT * FROM booking")
    List<BookingTicket> getAllBooking();

    @Delete
    void delete(BookingTicket ticket);

    @Insert
    void insert(BookingTicket... bookingTickets);
}
