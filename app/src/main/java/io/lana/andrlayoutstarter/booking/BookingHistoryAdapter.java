package io.lana.andrlayoutstarter.booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.format.DateTimeFormatter;
import java.util.List;

import io.lana.andrlayoutstarter.R;

public class BookingHistoryAdapter extends ArrayAdapter<BookingTicket> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BookingHistoryAdapter(@NonNull Context context, List<BookingTicket> histories) {
        super(context, 0, histories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BookingTicket item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_booking_history, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.code)).setText(item.getId());
        ((TextView) convertView.findViewById(R.id.from)).setText(item.getFrom());
        ((TextView) convertView.findViewById(R.id.to)).setText(item.getTo());
        ((TextView) convertView.findViewById(R.id.name)).setText(item.getName());

        String startDate = item.getStartDate().format(DATE_FORMATTER) + " 18:55:00";
        ((TextView) convertView.findViewById(R.id.start_date)).setText(startDate);

        return convertView;
    }
}
