package io.lana.andrlayoutstarter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FormActivity extends NavigableActivity {

    private static final String[] CITIES = new String[]{"Hà Nội", "TP Hồ Chí Minh", "Mỹ"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, CITIES);
        ((AutoCompleteTextView) findViewById(R.id.autocomplete_from)).setAdapter(adapter);
        ((AutoCompleteTextView) findViewById(R.id.autocomplete_to)).setAdapter(adapter);

        bindDatePicker(findViewById(R.id.edittext_start_date));
        bindDatePicker(findViewById(R.id.edittext_end_date));
    }

    protected void bindDatePicker(TextInputEditText textInputEditText) {
        textInputEditText.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            datePicker.addOnPositiveButtonClickListener(millis -> {
                Instant selected = Instant.ofEpochMilli(millis);
                LocalDate localDate = selected.atZone(ZoneId.systemDefault()).toLocalDate();
                textInputEditText.setText(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            });

            datePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        });
    }
}