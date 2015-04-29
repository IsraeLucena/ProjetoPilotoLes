
package com.br.les.activities;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.br.les.povmt.R;
import com.br.les.timeitup.ActivityTI;
import com.parse.ParseObject;

public class CreateTI extends Activity {

    private ActivityTI myActivityTI;
    private NumberPicker hours;
    private int priority = 2; // valor inicial, caso n�o seja mudado deve
                              // ficar
                              // como 2.
    private NumberPicker minutes;
    private TextView tvDateStart;
    private String jsonUser;
    private static final String JSONUSER = "JsonUser";

    static final int DATE_DIALOG_ID = 999;

    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ti);

        Bundle bundle = getIntent().getExtras();
        jsonUser = bundle.getString(JSONUSER);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);

        tvDateStart = (TextView) findViewById(R.id.tvDateStart);
        Calendar calendar = Calendar.getInstance();
        tvDateStart.setText(day + "/" + month + "/"
                + year);

        Button startBt = (Button) findViewById(R.id.dateStartBt);
        startBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        Button addTI = (Button) findViewById(R.id.button_create);
        addTI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.name_field);
                Toast toast = Toast.makeText(getApplicationContext(),
                        R.string.register_ok, Toast.LENGTH_SHORT);
                toast.show();
                ParseObject newActivity = new ParseObject("Activity");
                // Intent i = new Intent(CreateTI.this, WeeklyMonitoring.class);
                // finish();
                // startActivity(i);
            }
        });

        Button cancelTI = (Button) findViewById(R.id.button_cancel);
        cancelTI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CreateTI.this, WeeklyMonitoring.class);
                i.putExtra(JSONUSER, jsonUser);
                finish();
                startActivity(i);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth + 1;
            day = selectedDay;
            String date;
            // set selected date into textvie
            if (month > 9) {
                date = day + "/" + month + "/" + year;
            } else {
                date = day + "/0" + month + "/" + year;
            }
            tvDateStart.setText(date);
            // set selected date into datepicker also

        }
    };
}
