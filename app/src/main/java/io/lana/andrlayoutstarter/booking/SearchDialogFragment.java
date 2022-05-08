package io.lana.andrlayoutstarter.booking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.datepicker.MaterialDatePicker;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

import io.lana.andrlayoutstarter.FormUtils;
import io.lana.andrlayoutstarter.NavigableActivity;
import io.lana.andrlayoutstarter.R;


public class SearchDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_booking_history_search, null);
        EditText textDate = view.findViewById(R.id.txt_from_date);
        EditText textCode = view.findViewById(R.id.txt_name);

        textDate.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            datePicker.addOnPositiveButtonClickListener(millis -> {
                Instant selected = Instant.ofEpochMilli(millis);
                LocalDate localDate = selected.atZone(ZoneId.systemDefault()).toLocalDate();
                textDate.setText(localDate.format(FormUtils.SIMPLE_DATE_FORMAT));
            });

            datePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
        });


        return new AlertDialog.Builder(getActivity())
                .setTitle("Search in ticket")
                .setView(view)
                .setNegativeButton("Cancel", (dialog, which) -> Objects.requireNonNull(getDialog()).cancel())
                .setPositiveButton("Search", (dialog, which) -> {
                    Intent intent = new Intent(getContext(), BookingHistoryActivity.class);
                    intent.putExtra("term", StringUtils.defaultString(textCode.getText().toString()));
                    intent.putExtra("date", StringUtils.defaultString(textDate.getText().toString(), LocalDate.now().format(FormUtils.SIMPLE_DATE_FORMAT)));
                    startActivity(intent);
                })
                .create();
    }
}