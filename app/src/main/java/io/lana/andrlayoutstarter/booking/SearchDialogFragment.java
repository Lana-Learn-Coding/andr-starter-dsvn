package io.lana.andrlayoutstarter.booking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

import io.lana.andrlayoutstarter.FormUtils;
import io.lana.andrlayoutstarter.R;


public class SearchDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_booking_history_search, null);
        EditText text = view.findViewById(R.id.txt_from_date);
        text.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            datePicker.addOnPositiveButtonClickListener(millis -> {
                Instant selected = Instant.ofEpochMilli(millis);
                LocalDate localDate = selected.atZone(ZoneId.systemDefault()).toLocalDate();
                text.setText(localDate.format(FormUtils.SIMPLE_DATE_FORMAT));
            });

            datePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
        });


        return new AlertDialog.Builder(getActivity())
                .setTitle("Search in ticket")
                .setView(view)
                .setNegativeButton("Cancel", (dialog, which) -> Objects.requireNonNull(getDialog()).cancel())
                .create();
    }
}