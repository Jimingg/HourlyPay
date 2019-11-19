package sg.edu.rp.c346.hourlypay;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.time.Duration;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class New_Shift_Activity extends AppCompatActivity {
    long diffMinutes = 0;
    long diffHours = 0;
    long diffDays = 0;
    DatabaseHelper myDB;
    DateFormat formatter = null;
    Date convertedDate = null;
    String startdatef = null;
    String startTime = null;
    String endTime = null;
    boolean fieldsOK =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__shift_);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.Bottom_nav);
        final EditText etStartDate = findViewById(R.id.editTextSD);
        final EditText etEndDate = findViewById(R.id.editTextED);
        final EditText etStartTime = findViewById(R.id.editTextST);
        final EditText etEndTime = findViewById(R.id.editTextET);
        final Button Btnadd = findViewById(R.id.btnadd);
        final Button Btncalc = findViewById(R.id.btncalc);
        final EditText etDesc = findViewById(R.id.editTextdesc1);
        final EditText etbreak = findViewById(R.id.editTextbreak1);
        final EditText etrate = findViewById(R.id.editTextRate);
        final TextView tvTotalpay = findViewById(R.id.TextViewTotalPay);
        myDB = new DatabaseHelper(this);
        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fieldsOK = validate(new EditText[] {etStartDate,etEndDate,etStartTime,etEndTime,etDesc,etbreak,etrate});


                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthofyear, int dayOfMonth) {
                        etStartDate.setText(dayOfMonth + "/" + (monthofyear + 1) + "/" + year);

                    }
                };
                Calendar c = Calendar.getInstance();
                int thisyear = c.get(Calendar.YEAR);
                int thismonth = c.get(Calendar.MONTH);
                int thisday = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog myDateDialog = new DatePickerDialog(New_Shift_Activity.this,
                        myDateListener, thisyear, thismonth, thisday);
                myDateDialog.show();
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthofyear, int dayOfMonth) {
                        etEndDate.setText(dayOfMonth + "/" + (monthofyear + 1) + "/" + year);
                    }
                };
                Calendar c = Calendar.getInstance();
                int thisyear = c.get(Calendar.YEAR);
                int thismonth = c.get(Calendar.MONTH);
                int thisday = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog myDateDialog = new DatePickerDialog(New_Shift_Activity.this,
                        myDateListener, thisyear, thismonth, thisday);
                myDateDialog.show();
            }
        });

        etStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etStartTime.setText(hourOfDay + " : " + minute);

                    }
                };
                Calendar c = Calendar.getInstance();
                int thishour = c.get(Calendar.HOUR_OF_DAY);
                int thisminute = c.get(Calendar.MINUTE);
                TimePickerDialog myTimeDialog = new TimePickerDialog(New_Shift_Activity.this, myTimeListener, thishour, thisminute, true);
                myTimeDialog.show();

            }
        });

        etEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etEndTime.setText(hourOfDay + " : " + minute);

                    }
                };
                Calendar c = Calendar.getInstance();
                int thishour = c.get(Calendar.HOUR_OF_DAY);
                int thisminute = c.get(Calendar.MINUTE);
                TimePickerDialog myTimeDialog = new TimePickerDialog(New_Shift_Activity.this, myTimeListener, thishour, thisminute, true);
                myTimeDialog.show();

            }
        });
        Btncalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String startdate = etStartDate.getText().toString();
                    String Starttime = etStartTime.getText().toString();
                    String Startdatetime = startdate + " " + Starttime;
                    String endate = etEndDate.getText().toString();
                    String endtime = etEndTime.getText().toString();
                    String enddatetime = endate + " " + endtime;
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH : mm");

                    try {
                        Date d1 = format.parse(Startdatetime);
                        Date d2 = format.parse(enddatetime);

                        //in milliseconds
                        long diff = d2.getTime() - d1.getTime();


                        diffMinutes = diff / (60 * 1000) % 60;
                        diffHours = diff / (60 * 60 * 1000) % 24;
                        diffDays = diff / (24 * 60 * 60 * 1000);

                        Log.d("hour", diffMinutes + " minutes, ");
                        Log.d("min", diffHours + " hours, ");
                        Log.d("day", diffDays + " days, ");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                if(fieldsOK == true) {
                    Double rate = Double.parseDouble(etrate.getText().toString());
                    int breakmoney = Integer.parseInt(etbreak.getText().toString());


                    double totalpay = ((diffDays * 24 * rate) + (diffHours * rate) + (diffMinutes / 60 * rate)) - (breakmoney * 0.5 * rate);

                    String total = Double.toString(totalpay);

                    tvTotalpay.setText(total);
                }
                else {
                    Toast.makeText(New_Shift_Activity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();

                }

            }
        });
        Btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fieldsOK == true) {
//                intent.putExtra("Desc",etDesc.getText().toString());
//                intent.putExtra("ST",etDesc.getText().toString());
//                intent.putExtra("Desc",etDesc.getText().toString());
//                intent.putExtra("Desc",etDesc.getText().toString());
//                intent.putExtra("Desc",etDesc.getText().toString());
                    String startdate = etStartDate.getText().toString();
                    String Starttime = etStartTime.getText().toString();
                    String Startdatetime = startdate + " " + Starttime;
                    String endate = etEndDate.getText().toString();
                    String endtime = etEndTime.getText().toString();
                    String enddatetime = endate + " " + endtime;
                    Log.d("sad", enddatetime);
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH : mm");

                    try {
                        Date d1 = format.parse(Startdatetime);
                        Date d2 = format.parse(enddatetime);

                        //in milliseconds
                        long diff = d2.getTime() - d1.getTime();


                        diffMinutes = diff / (60 * 1000) % 60;
                        diffHours = diff / (60 * 60 * 1000) % 24;
                        diffDays = diff / (24 * 60 * 60 * 1000);

                        Log.d("hour", diffMinutes + " minutes, ");
                        Log.d("min", diffHours + " hours, ");
                        Log.d("day", diffDays + " days, ");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Double rate = Double.parseDouble(etrate.getText().toString());
                    int breakmoney = Integer.parseInt(etbreak.getText().toString());
                    double totalpay = ((diffDays * 24 * rate) + (diffHours * rate) + (diffMinutes / 60 * rate)) - (breakmoney * 0.5 * rate);

                    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat format2 = new SimpleDateFormat("dd MMM yyyy");
                    DateFormat formatold = new SimpleDateFormat("HH : mm");
                    try {
                        Date date = format1.parse(etStartDate.getText().toString());
                        startdatef = format2.format(date);

                        Date starttime1 = formatold.parse(Starttime);
                        Date endtime1 = formatold.parse(endtime);
                        SimpleDateFormat formatnew = new SimpleDateFormat("hh:mm a");
                        startTime = formatnew.format(starttime1);
                        endTime = formatnew.format(endtime1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Double totalhour = ((diffDays * 24) + (diffHours) + (diffMinutes / 60)) - (breakmoney * 0.5);

                    boolean trt = myDB.addData(etDesc.getText().toString(), startdatef, startTime, endTime, Integer.toString(breakmoney), Double.toString(totalhour), Double.toString(totalpay));
                    Intent intent = new Intent(getBaseContext(), View_shift.class);
                    startActivity(intent);
                }
            else {
                    Toast.makeText(New_Shift_Activity.this, "Please enter all fields ", Toast.LENGTH_SHORT).show();

                }}
        });


        bottomNavigationView.setSelectedItemId(R.id.action_Start_new);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_Start_new:
                        Toast.makeText(New_Shift_Activity.this, "Start new Shift", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), New_Shift_Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_home:
                        Toast.makeText(New_Shift_Activity.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.action_view:
                        Toast.makeText(New_Shift_Activity.this, "View Statistics", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(getBaseContext(), View_shift.class);
                        startActivity(intent2);
                        break;

                }
                return true;
            }
        });
    }
    private boolean validate(EditText[] fields){
        for(int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];
            if(currentField.getText().length() <0){
                return false;
            }
        }
        return true;
    }
}
