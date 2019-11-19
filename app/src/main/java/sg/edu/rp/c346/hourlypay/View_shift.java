package sg.edu.rp.c346.hourlypay;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class View_shift extends AppCompatActivity {
    ListView lvrecord;
    ArrayList<Records> alrecord;
    CustomAdapter carecord;
    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shift);
        lvrecord = findViewById(R.id.Listviewshift);
        alrecord = new ArrayList<Records>();

        Intent intentReceived = getIntent();
//        String desc = intentReceived.getStringExtra("desc");
//        String date = intentReceived.getStringExtra("date");
//        String start = intentReceived.getStringExtra("start");
//        String end  = intentReceived.getStringExtra("end");
//        String breaks = intentReceived.getStringExtra("break");
//        Double hour = intentReceived.getDoubleExtra("hour",0.0);
//        Double pay = intentReceived.getDoubleExtra("TotalPay",0);

       myDB = new DatabaseHelper(this);
        final EditText tvpos = findViewById(R.id.editTextpos);

        Button btnpos = findViewById(R.id.buttonDelete);
        Button btndelete = findViewById(R.id.buttonDeleteAll);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        lvrecord,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {


                                    Records r = alrecord.get(position);
                                    String name = r.getDesc();
                                    myDB.deleteItem(name);
                                    alrecord.remove(position);
                                    carecord.notifyDataSetChanged();

                                }

                            }
                        });
        lvrecord.setOnTouchListener(touchListener);
        btnpos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos = tvpos.getText().toString();
                Log.d("posss", pos);
                myDB.deleteItem(pos);
                carecord.notifyDataSetChanged();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.deleteall();
                carecord.notifyDataSetChanged();
            }
        });


        carecord = new CustomAdapter(this, R.layout.viewcolumn_layout, alrecord);
        lvrecord.setAdapter(carecord);
        Cursor cursor = myDB.getListContents();

        while (cursor.moveToNext()){
            Records record1 = new Records(cursor.getString(1) , cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
            alrecord.add(record1);
            carecord.notifyDataSetChanged();
        }





        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.Bottom_nav_view);
        bottomNavigationView.setSelectedItemId(R.id.action_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_Start_new:
                        Toast.makeText(View_shift.this, "Start new Shift", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(),New_Shift_Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_home:
                        Toast.makeText(View_shift.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.action_view:
                        Toast.makeText(View_shift.this, "View Statistics", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(getBaseContext(),View_shift.class);
                        startActivity(intent2);
                        break;

                }
                return true;
            }
        });
    }
}
