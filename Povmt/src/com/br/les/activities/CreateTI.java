
package com.br.les.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.br.les.povmt.R;
import com.br.les.timeitup.ActivityTI;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class CreateTI extends Activity {

    private ActivityTI myActivityTI;
    private NumberPicker hours;
    // ficar
    // como 2.
    private NumberPicker minutes;
    private TextView tvDateStart;
    private String jsonUser;

    static final int DATE_DIALOG_ID = 999;

    private int year;
    private int month;
    private int day;

    private int hour;
    private int min;

    private Spinner category;
    private Spinner priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ti);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);

        final NumberPicker npHour = (NumberPicker) findViewById(R.id.nnPickHour);
        npHour.setMaxValue(100);
        npHour.setMinValue(0);
        npHour.setOnValueChangedListener(new OnValueChangeListener() {
            
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
               hour = newVal;                
            }
        });
        final NumberPicker npMin = (NumberPicker) findViewById(R.id.nPickMin);
        npMin.setMaxValue(59);
        npMin.setMinValue(0);
        npMin.setOnValueChangedListener(new OnValueChangeListener() {
            
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                min = newVal;
            }
        });
        min = npMin.getValue();

        tvDateStart = (TextView) findViewById(R.id.tvDateStart);
        tvDateStart.setText(day + "/" + month + "/"
                + year);

        setupSpinnerPriority();

        setupSpinnerCategory();

        setupBtCalendar();

        setuoBtCreate();

        setupBtCancel();
    }

    private void setupSpinnerPriority() {
        priority = (Spinner) findViewById(R.id.spinnerPriority);
        final List<String> list = new ArrayList<String>();
        list.add("Alta");
        list.add("Media");
        list.add("Baixa");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (CreateTI.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        list);
        priority.setAdapter(dataAdapter);
    }

    private void setupSpinnerCategory() {
        category = (Spinner) findViewById(R.id.spinnerCategory);
        final List<String> list = new ArrayList<String>();
        list.add("Sem Categoria");
        try {
            ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>(
                    "category");
            String faceId = ParseUser.getCurrentUser().getJSONObject("profile")
                    .getString("facebookId");
            Log.i("dfsdkf", faceId);
            query2.whereEqualTo(
                    "user", faceId);
            query2.findInBackground(new FindCallback<ParseObject>() {

                @Override
                public void done(List<ParseObject> arg0, ParseException arg1) {
                    if (arg1 == null) {
                        Log.i("dfsdkf", arg0.size() + "");
                        for (ParseObject categoryObj : arg0) {
                            list.add(categoryObj.getString("categoryName"));
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                (
                                        CreateTI.this,
                                        android.R.layout.simple_spinner_dropdown_item,
                                        list);
                        category.setAdapter(dataAdapter);
                    } else {
                        arg1.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void setupBtCancel() {
        Button cancelTI = (Button) findViewById(R.id.button_cancel);
        cancelTI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CreateTI.this, WeeklyMonitoring.class);
                finish();
                startActivity(i);
            }
        });
    }

    private void setuoBtCreate() {
        Button addTI = (Button) findViewById(R.id.button_create);
        addTI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.name_field);
                Toast toast = Toast.makeText(getApplicationContext(),
                        R.string.register_ok, Toast.LENGTH_SHORT);
                EditText activityName = (EditText) findViewById(R.id.name_field);
                toast.show();
                ParseObject newActivity = new ParseObject("Activity");
                Date date = new Date(day + "/" + month + "/"
                        + year);
                try {
                    String faceId = ParseUser.getCurrentUser()
                            .getJSONObject("profile")
                            .getString("facebookId");
                    newActivity.put("user", faceId);
                    newActivity.put("activityDay", date);
                    newActivity.put("priority",
                            (String) priority.getSelectedItem());
                    newActivity.put("name", activityName.getText().toString());
                    newActivity.put("category",
                            (String) category.getSelectedItem());
                    Calendar alarmCalendar = Calendar.getInstance();
                    newActivity.put("duration", TimeUnit.HOURS.toMillis(hour)
                            + TimeUnit.MINUTES.toMillis(min));
                    newActivity.saveInBackground();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setupBtCalendar() {
        Button startBt = (Button) findViewById(R.id.dateStartBt);
        startBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
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
