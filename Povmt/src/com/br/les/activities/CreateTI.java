
package com.br.les.activities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.br.les.povmt.R;
import com.br.les.timeitup.ActivityTI;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
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

    /** The Constant DP_WIDTH_IMAGE. */
    private static final int DP_WIDTH_IMAGE = 180;

    /** The Constant DP_HEIGHT_IMAGE. */
    private static final int DP_HEIGHT_IMAGE = 150;

    /** The Constant SCALE_DP_TO_PX. */
    private static final float SCALE_DP_TO_PX = 0.5f;

    /** The Constant SCALE_ROTATE_IMAGE. */
    private static final int SCALE_ROTATE_IMAGE = 90;

    /** The Constant ID_IMAGE_VERTICAL. */
    private static final int ID_IMAGE_VERTICAL = 8;

    /** The Constant ID_IMAGE_HORIZONTAL. */
    private static final int ID_IMAGE_HORIZONTAL = 6;

    private ImageView activityImage;

    private Bitmap newActivityImage;

    private Spinner category;
    private Spinner priority;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_LOAD_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ti);

        final Calendar c = Calendar.getInstance();
        activityImage = (ImageView) findViewById(R.id.imageActivity);
        newActivityImage = BitmapFactory.decodeResource(getResources(),
                R.drawable.defaultimaje);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);

        final NumberPicker npHour = (NumberPicker) findViewById(R.id.nnPickHour);
        npHour.setMaxValue(100);
        npHour.setMinValue(0);
        npHour.setOnValueChangedListener(new OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                    int newVal) {
                hour = newVal;
            }
        });
        final NumberPicker npMin = (NumberPicker) findViewById(R.id.nPickMin);
        npMin.setMaxValue(59);
        npMin.setMinValue(0);
        npMin.setOnValueChangedListener(new OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                    int newVal) {
                min = newVal;
            }
        });
        min = npMin.getValue();

        tvDateStart = (TextView) findViewById(R.id.tvDateStart);
        tvDateStart.setText(day + "/" + month + "/"
                + year);

        setupSpinnerPriority();

        setupSpinnerCategory();
        setupBtSelectImage();
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

    private void setupBtSelectImage() {
        Button selectImageTI = (Button) findViewById(R.id.selectImage);
        selectImageTI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogLoadImage(CreateTI.this).show();
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
                Date date = new Date();
                date.setMonth(month);
                date.setYear(year - 1900);
                date.setDate(day);
                try {
                    String faceId = ParseUser.getCurrentUser()
                            .getJSONObject("profile")
                            .getString("facebookId");
                    newActivity.put("user", faceId);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    newActivityImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bytearray = stream.toByteArray();
                    if (bytearray != null){
                        System.out.println("BYTESSS");  //test case
                        System.out.println(bytearray.toString());  //test case
                        ParseFile file = new ParseFile("myImage.png",bytearray);
                        file.saveInBackground();
                        newActivity.put("profilepic",file);
                    }
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
                    Intent i = new Intent(CreateTI.this, WeeklyMonitoring.class);
                    finish();
                    startActivity(i);
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
            month = selectedMonth;
            day = selectedDay;
            String date;
            // set selected date into textvie
            if (month > 9) {
                date = day + "/" + month + 1 + "/" + year;
            } else {
                date = day + "/0" + month + 1 + "/" + year;
            }
            tvDateStart.setText(date);
            // set selected date into datepicker also

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            final float scale = getResources().getDisplayMetrics().density;
            final int width = (int) (DP_WIDTH_IMAGE * scale + SCALE_DP_TO_PX); // dp
                                                                               // to
                                                                               // px
            final int height = (int) (DP_HEIGHT_IMAGE * scale + SCALE_DP_TO_PX); // dp
                                                                                 // to
            // px

            if (requestCode == REQUEST_IMAGE_CAPTURE) {

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                newActivityImage = imageBitmap;
                activityImage.setImageBitmap(imageBitmap);
            } else if (requestCode == REQUEST_LOAD_IMAGE) {

                final Uri selectedImage = data.getData();
                int orientation = 0;

                final String[] filePathColumn = {
                        MediaStore.Images.Media.DATA
                };
                final Cursor cursor = this.getContentResolver().query(
                        selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                final int columnIndex = cursor
                        .getColumnIndex(filePathColumn[0]);
                final String picturePath = cursor.getString(columnIndex);
                cursor.close();

                try {
                    final ExifInterface exif = new ExifInterface(picturePath);
                    orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION, 1);
                    // Utils.logPromotions("Oriantation image = " +
                    // orientation);

                } catch (final IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                final BitmapFactory.Options options = new BitmapFactory.Options();

                Bitmap myBitmap = BitmapFactory
                        .decodeFile(picturePath, options);
                options.inSampleSize = ID_IMAGE_VERTICAL;

                if (orientation == ID_IMAGE_HORIZONTAL) {
                    final Matrix matrix = new Matrix();
                    matrix.postRotate(SCALE_ROTATE_IMAGE);
                    myBitmap = Bitmap.createBitmap(myBitmap, 0, 0,
                            myBitmap.getWidth(), myBitmap.getHeight(), matrix,
                            true);
                    myBitmap = Bitmap.createScaledBitmap(myBitmap, height,
                            width, true);
                } else {
                    myBitmap = Bitmap.createScaledBitmap(myBitmap, width,
                            height, true);

                }

                newActivityImage = myBitmap;

                activityImage.setImageBitmap(myBitmap);

                // crop(picturePath);
                // mImage.setImageBitmap(myBitmap);

            }
        }
    }
}
