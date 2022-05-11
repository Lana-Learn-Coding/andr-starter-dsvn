package io.lana.andrlayoutstarter.booking;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Dao
public interface BookingDao {
    @Query("SELECT * FROM booking WHERE (name like :term or id like :term) and (:date is null or :date < startDate)")
    List<BookingTicket> getBooking(String term, LocalDate date);

    @Delete
    void delete(BookingTicket ticket);

    @Insert
    void insert(BookingTicket... bookingTickets);

    @Query("SELECT * FROM booking WHERE id = :id LIMIT 1")
    BookingTicket getBookingById(String id);

    @Update
    void update(BookingTicket bookingTicket);
}
