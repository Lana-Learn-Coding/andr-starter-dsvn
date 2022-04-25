package io.lana.andrlayoutstarter.booking;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import io.lana.andrlayoutstarter.FormUtils;
import io.lana.andrlayoutstarter.MainApplication;
import io.lana.andrlayoutstarter.NavigableActivity;
import io.lana.andrlayoutstarter.R;

public class FormActivity extends NavigableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        MainApplication application = (MainApplication) getApplicationContext();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, application.getCities());
        ((AutoCompleteTextView) findViewById(R.id.autocomplete_from)).setAdapter(adapter);
        ((AutoCompleteTextView) findViewById(R.id.autocomplete_to)).setAdapter(adapter);

        bindDatePicker(findViewById(R.id.edittext_start_date));
        bindDatePicker(findViewById(R.id.edittext_end_date));

        findViewById(R.id.btn_save).setOnClickListener(this::saveForm);
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
                textInputEditText.setText(localDate.format(FormUtils.SIMPLE_DATE_FORMAT));
            });

            datePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        });
    }

    private void saveForm(View view) {
        BookingTicket ticket = new BookingTicket();
        ticket.setName(FormUtils.getTextValue(findViewById(R.id.txt_name)));
        ticket.setPhone(FormUtils.getTextValue(findViewById(R.id.txt_phone)));

        ticket.setFrom(FormUtils.getTextValue(findViewById(R.id.autocomplete_from)));
        ticket.setTo(FormUtils.getTextValue(findViewById(R.id.autocomplete_to)));
        ticket.setOneWay(((RadioButton) findViewById(R.id.chk_one_way)).isChecked());

        ticket.setStartDate(FormUtils.getTextDate(findViewById(R.id.edittext_start_date)));
        ticket.setEndDate(FormUtils.getTextDate(findViewById(R.id.edittext_end_date)));

        try {
            ticket.setChildrenCount(Integer.parseInt(FormUtils.getTextValue(findViewById(R.id.txt_children_count))));
            ticket.setPeopleCount(Integer.parseInt(FormUtils.getTextValue(findViewById(R.id.txt_people_count))));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Format số không hợp lệ, xin hãy kiểm tra lại trường Người Lớn và Trẻ Em ", Toast.LENGTH_SHORT).show();
            return;
        }

        MainApplication app = (MainApplication) getApplicationContext();
        app.getTickets().add(ticket);
        Toast.makeText(this, "Đặt vé thành công", Toast.LENGTH_SHORT).show();
    }
}