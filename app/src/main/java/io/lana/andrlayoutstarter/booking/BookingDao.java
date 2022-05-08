package io.lana.andrlayoutstarter.booking;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface BookingDao {
    @Query("SELECT * FROM booking WHERE (name like :term or id like :term) and (:date is null or :date < startDate)")
    List<BookingTicket> getBooking(String term, LocalDate date);

    @Delete
    void delete(BookingTicket ticket);

    @Insert
    void insert(BookingTicket... bookingTickets);
}
