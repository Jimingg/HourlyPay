package sg.edu.rp.c346.hourlypay;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {
    private static final String FORMAT = "%02d:%02d:%02d";
    private boolean running;
    private long RealPause;
    private String Starttime;
    private String endtime;
    int seconds , minutes;
    DatabaseHelper myDB;
    boolean fieldsOK =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);
        final EditText etDesc = findViewById(R.id.editTextDesc);
        final EditText etPay = findViewById(R.id.editTextPay);
        final EditText etBreak = findViewById(R.id.editTextBreak);
        final Chronometer simpleChronometer = (Chronometer) findViewById(R.id.ChronometerDisplay);
        final Button btnstart = findViewById(R.id.buttonStart);
        Button btnStop = findViewById(R.id.buttonStop);
        Button btnReset = findViewById(R.id.buttonReset);
        Button btnAdd = findViewById(R.id.buttonAdd);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldsOK = validate(new EditText[] {etBreak,etDesc,etPay});

                if(!running){
                    simpleChronometer.setBase(SystemClock.elapsedRealtime() - RealPause) ;
                    simpleChronometer.start();
                    running = true;
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm.ss aa");
                    Starttime = dateFormat.format(currentTime);
                }
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(running){
                    simpleChronometer.stop();
                    RealPause = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
                    running = false;
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm.ss aa");
                    endtime = dateFormat.format(currentTime);
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleChronometer.setBase(SystemClock.elapsedRealtime());
                RealPause = 0;
                etBreak.getText().clear();
                etDesc.getText().clear();
                etPay.getText().clear();

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fieldsOK == false) {
                    Toast.makeText(MainActivity.this, "Fill in the blanks", Toast.LENGTH_SHORT).show();

                } else {
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);

                    //set the dialog details
                    myBuilder.setTitle(etDesc.getText().toString());
                    myBuilder.setMessage("Add new Shift?");
                    myBuilder.setCancelable(false);
                    myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            Toast.makeText(getApplicationContext(), "New shift added", Toast.LENGTH_LONG).show();
                            System.out.println(RealPause);
                            Intent intent = new Intent(getBaseContext(), View_shift.class);
//                        intent.putExtra("time",RealPause);
//                        intent.putExtra("desc",etDesc.getText().toString());
//                        intent.putExtra("break",etBreak.getText().toString());
//                        intent.putExtra("pay",etPay.getText());
//                        intent.putExtra("start",Starttime);
//                        intent.putExtra("end",endtime);
                            Calendar calendar = Calendar.getInstance();
                            String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
//                        intent.putExtra("date",currentDate);
                            int hour = (int) ((RealPause / (1000 * 60 * 60)) % 24);

//                        intent.putExtra("hour",hour);
                            String value = etPay.getText().toString();
                            Double pay = Double.parseDouble(value);
                            int breakmoney = Integer.parseInt(etBreak.getText().toString());
                            Double TotalPay = (hour * pay) - ((breakmoney * 0.5) * pay);
//                        intent.putExtra("TotalPay",TotalPay);
                            simpleChronometer.stop();
                            RealPause = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
                            running = false;
                            Date currentTime = Calendar.getInstance().getTime();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm.ss aa");
                            endtime = dateFormat.format(currentTime);
                            boolean trt = myDB.addData(etDesc.getText().toString(), currentDate, Starttime, endtime, etBreak.getText().toString(), Integer.toString(hour), Double.toString(TotalPay));
                            startActivity(intent);
                            if (trt) {

                            }


                        }
                    });
                    myBuilder.setNegativeButton("CANCEL", null);
                    AlertDialog myDialog = myBuilder.create();

                    myDialog.show();
                }
            }
        });

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.Bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_Start_new:
                        Toast.makeText(MainActivity.this, "Start new Shift", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(),New_Shift_Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_home:
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();

                        Intent intent1 = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.action_view:
                        Toast.makeText(MainActivity.this, "View Statistics", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(getBaseContext(),View_shift.class);
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
