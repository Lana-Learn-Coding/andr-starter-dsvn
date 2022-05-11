package io.lana.andrlayoutstarter.booking;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.github.javafaker.Faker;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.Executors;

import io.lana.andrlayoutstarter.FormUtils;
import io.lana.andrlayoutstarter.MainApplication;
import io.lana.andrlayoutstarter.db.MainDatabase;
import io.lana.andrlayoutstarter.NavigableActivity;
import io.lana.andrlayoutstarter.R;

public class FormActivity extends NavigableActivity {
    private BookingDao bookingDao;
    private String updateId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        bookingDao = MainDatabase.getDbInstance(this).bookingDao();
        MainApplication application = (MainApplication) getApplicationContext();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, application.getCities());
        ((AutoCompleteTextView) findViewById(R.id.autocomplete_from)).setAdapter(adapter);
        ((AutoCompleteTextView) findViewById(R.id.autocomplete_to)).setAdapter(adapter);

        bindDatePicker(findViewById(R.id.edittext_start_date));
        bindDatePicker(findViewById(R.id.edittext_end_date));

        findViewById(R.id.btn_save).setOnClickListener(this::saveForm);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FormUtils.setTextValue(findViewById(R.id.btn_save), getResources().getText(R.string.book).toString());
        FormUtils.setTextValue(findViewById(R.id.txt_title), getResources().getText(R.string.booking).toString());

        Bundle extras = ObjectUtils.defaultIfNull(getIntent().getExtras(), new Bundle());
        String updateId = extras.getString("id");
        if (StringUtils.isBlank(updateId)) return;

        this.updateId = updateId;
        FormUtils.setTextValue(findViewById(R.id.btn_save), getResources().getText(R.string.update_book).toString());
        FormUtils.setTextValue(findViewById(R.id.txt_title), "Cập nhật " + updateId);
        Executors.newSingleThreadExecutor().execute(() -> {
            BookingTicket ticket = bookingDao.getBookingById(updateId);
            if (ticket == null) {
                runOnUiThread(() -> {
                    navigate(this, BookingHistoryActivity.class);
                    Toast.makeText(this, "Không tìm thấy vé " + updateId, Toast.LENGTH_SHORT).show();
                });
                return;
            }

            // set value to form on update
            runOnUiThread(() -> {
                FormUtils.setTextValue(findViewById(R.id.txt_name), ticket.getName());
                FormUtils.setTextValue(findViewById(R.id.txt_phone), ticket.getPhone());
                FormUtils.setTextValue(findViewById(R.id.edittext_start_date), ticket.getStartDate().format(FormUtils.SIMPLE_DATE_FORMAT));
                FormUtils.setTextValue(findViewById(R.id.edittext_end_date), ticket.getEndDate().format(FormUtils.SIMPLE_DATE_FORMAT));
                FormUtils.setTextValue(findViewById(R.id.txt_children_count), String.valueOf(ticket.getChildrenCount()));
                FormUtils.setTextValue(findViewById(R.id.txt_people_count), String.valueOf(ticket.getPeopleCount()));
                ((AutoCompleteTextView) findViewById(R.id.autocomplete_from)).setText(ticket.getFrom(), false);
                ((AutoCompleteTextView) findViewById(R.id.autocomplete_to)).setText(ticket.getTo(), false);
                ((RadioButton) findViewById(R.id.chk_one_way)).setChecked(ticket.isOneWay());
                ((RadioButton) findViewById(R.id.chk_two_way)).setChecked(!ticket.isOneWay());
            });
        });
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
        boolean isUpdate = StringUtils.isNotBlank(updateId);
        BookingTicket ticket = new BookingTicket(isUpdate ? updateId : "DSVN" + Faker.instance().number().digits(8));

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

        Executors.newSingleThreadExecutor().execute(() -> {
            if (isUpdate) {
                bookingDao.update(ticket);
            } else {
                bookingDao.insert(ticket);
            }

            runOnUiThread(() -> {
                String actionName = isUpdate ? "Cập nhật vé" : "Đặt vé";
                navigate(this, BookingHistoryActivity.class);
                Toast.makeText(this, actionName + " thành công", Toast.LENGTH_SHORT).show();
            });
        });
    }
}