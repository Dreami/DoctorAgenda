package com.example.doctoragenda;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import org.joda.time.LocalTime;

import java.util.Calendar;
import java.util.HashMap;

public class CalendarActivity extends AppCompatActivity {
    private MaterialCalendarView calendar;
    private CalendarDay calInstance;
    private Button saveDate;
    private Intent dateIntent;
    private LocalTime timepicked;
    private HashMap<CalendarDay, LocalTime> appointments = new HashMap<CalendarDay, LocalTime>();

    private int hr, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.calendar);
            dateIntent = new Intent(this, MainActivity.class);

            if (getIntent() != null) {
                if (getIntent().getExtras() != null) {
                    if (getIntent().getExtras().get("appointments") != null) {
                        appointments = (HashMap<CalendarDay, LocalTime>) getIntent().getExtras().get("appointments");
                    }
                }
            }

            saveDate = (Button) findViewById(R.id.saveDate);
            saveDate.setVisibility(View.INVISIBLE);

            calendar = (MaterialCalendarView) findViewById(R.id.calendarView);
            calendar.setClickable(true);
            calendar.state().edit()
                    .setFirstDayOfWeek(Calendar.MONDAY)
                    .setMinimumDate(CalendarDay.from(1900, 1, 1))
                    .setMaximumDate(CalendarDay.from(2100, 12, 31))
                    .setCalendarDisplayMode(CalendarMode.MONTHS);

            calendar.setOnDateChangedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(@NonNull MaterialCalendarView calendar, @NonNull CalendarDay date, boolean selected) {
                    calInstance = calendar.getSelectedDate();
                    if (appointments.containsKey(calInstance)) {
                        String toastMsg = String.format("Fecha ocupada: Cita a las %d:%d", appointments.get(calInstance).getHourOfDay(),
                                appointments.get(calInstance).getMinuteOfHour());
                        Toast.makeText(CalendarActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
                        saveDate.setEnabled(false);
                    } else {
                        showDialog(0);
                    }
                }
            });

            saveDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(calInstance != null) {
                        if (!appointments.containsKey(calInstance)) {
                            appointments.put(calInstance, timepicked);
                            dateIntent.putExtra("appointments", appointments);
                            startActivity(dateIntent);
                        }
                    }
                }
            });

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new TimePickerDialog(CalendarActivity.this, timePickerListener, hr, min, false);
    }

    protected TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hr = hourOfDay;
            min = minute;

            timepicked = new LocalTime(hr, min);
            saveDate.setVisibility(View.VISIBLE);
            saveDate.setEnabled(true);
        }
    };
}
