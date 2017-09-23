package com.example.doctoragenda;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.joda.time.LocalTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView list_item;
    private Intent dateIntent;
    private Button createAppointment;

    private HashMap<CalendarDay, LocalTime> appointmentMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            appointmentMap = new HashMap<CalendarDay, LocalTime>();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");

            list_item = (ListView) findViewById(R.id.list_item);
            createAppointment = (Button) findViewById(R.id.createAppointment);

            dateIntent = new Intent(this, CalendarActivity.class);
            if( getIntent() != null) {
                if( getIntent().getExtras() != null) {
                    if( getIntent().getExtras().get("appointments") != null) {
                        appointmentMap = (HashMap<CalendarDay, LocalTime>) getIntent().getExtras().get("appointments");
                    }
                }
            }
            List<String> appointmentList = new ArrayList<String>();
            String appointmentText;
            for (CalendarDay key : appointmentMap.keySet()) {
                appointmentText = simpleDateFormat.format(key.getDate());
                appointmentText += String.format(" a las %d:%d", appointmentMap.get(key).getHourOfDay(), appointmentMap.get(key).getMinuteOfHour());
                appointmentList.add(appointmentText);
            }

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, appointmentList);

            list_item.setAdapter(arrayAdapter);

            createAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dateIntent.putExtra("appointments", appointmentMap);
                    Bundle bundleAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                            R.anim.left_right, R.anim.right_left).toBundle();
                    startActivity(dateIntent, bundleAnimation);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
