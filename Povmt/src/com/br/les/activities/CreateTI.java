
package com.br.les.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.br.les.povmt.R;
import com.br.les.timeitup.ActivityTI;
import com.br.les.timeitup.User;
import com.br.les.util.HttpURLConnectionPOST;
import com.google.gson.Gson;

public class CreateTI extends Activity {

    private ActivityTI myActivityTI;
    private NumberPicker hours;
    private int priority = 2; // valor inicial, caso n�o seja mudado deve
                              // ficar
                              // como 2.
    private NumberPicker minutes;
    private User currentUser;
    private String jsonUser;
    private static final String JSONUSER = "JsonUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ti);

        Bundle bundle = getIntent().getExtras();
        jsonUser = bundle.getString(JSONUSER);
        final Gson gson = new Gson();
        currentUser = gson.fromJson(jsonUser, User.class);
        // System.out.println("####USUARIO: " + currentUser);

        hours = (NumberPicker) findViewById(R.id.hours);
        hours.setMaxValue(167);
        hours.setMinValue(0);
        hours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                    int newVal) {
                hours.setValue(newVal);
            }
        });

        minutes = (NumberPicker) findViewById(R.id.minutes);
        minutes.setMaxValue(59);
        minutes.setMinValue(0);
        minutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                    int newVal) {
                minutes.setValue(newVal);
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
                myActivityTI = new ActivityTI(name.getText().toString(),
                        hours.getValue(), minutes.getValue(), priority);
                currentUser.isActualWeek();
                currentUser.getFirstWeek().addTI(myActivityTI);
                String userJson = gson.toJson(currentUser);
                HttpURLConnectionPOST connPOST = new HttpURLConnectionPOST();
                connPOST.execute(userJson, currentUser.getEmail());
                Intent i = new Intent(CreateTI.this, WeeklyMonitoring.class);
                i.putExtra(JSONUSER, userJson);
                finish();
                startActivity(i);
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

    /**
     * If back button pressed, finalize Activity.
     */
    @Override
    public final void onBackPressed() {
        Intent i = new Intent(CreateTI.this, WeeklyMonitoring.class);
        i.putExtra(JSONUSER, jsonUser);
        finish();
        startActivity(i);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked) {
            switch (view.getId()) {
                case R.id.hiPriority:
                    priority = 2;
                    break;
                case R.id.mediumPriority:
                    priority = 1;
                    break;
                case R.id.lowPriority:
                    priority = 0;
                    break;
                default:
                    priority = 0;
                    break;
            }
        }
    }
}
