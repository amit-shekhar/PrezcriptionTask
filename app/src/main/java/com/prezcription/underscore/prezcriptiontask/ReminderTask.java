package com.prezcription.underscore.prezcriptiontask;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class ReminderTask extends Fragment implements View.OnClickListener {

    private Context context;

    private EditText etMsg;
    private TextView tvDate,tvTime,tvDateSet,tvTimeSet;
    private Button bSet;
    private RelativeLayout rlRemNo;
    private TextView tvRemYes;

    private int year, month, day,hour,min;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private boolean isAlarmSet;
    private Intent i;
    private PendingIntent pi;

    private static final String MyPREFERENCES = "Myprefs";

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;


    public void setContext(Context context){
        this.context = context;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reminder_main,container,false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etMsg = (EditText) view.findViewById(R.id.etMsg);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvDateSet = (TextView) view.findViewById(R.id.tvDateSet);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvTimeSet = (TextView) view.findViewById(R.id.tvTimeSet);
        bSet = (Button) view.findViewById(R.id.bSet);
        rlRemNo = (RelativeLayout) view.findViewById(R.id.rlRemNo);
        tvRemYes = (TextView) view.findViewById(R.id.tvRemYes);
        bSet.setOnClickListener(this);
        tvDateSet.setOnClickListener(this);
        tvTimeSet.setOnClickListener(this);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        Log.i("time", hour + " " + min);
        showDate(year, month + 1, day);
        showTime(hour, min);

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        isAlarmSet = checkForAlarm();
        if(isAlarmSet){
            rlRemNo.setVisibility(View.GONE);
            tvRemYes.setVisibility(View.VISIBLE);
            tvRemYes.setText("Reminder Set at \n\n" + sharedpreferences.getString("alarmTime", ""));
            bSet.setText("CANCEL");
        }else{
            rlRemNo.setVisibility(View.VISIBLE);
            tvRemYes.setVisibility(View.GONE);
            bSet.setText("SET REMINDER");
        }
        alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);

    }

    public boolean checkForAlarm(){
        return sharedpreferences.getString("alarmTime","") != "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvDateSet:
                new SelectDate().show(getFragmentManager(),"DatePicker");
                break;
            case R.id.tvTimeSet:
                new SelectTime().show(getFragmentManager(),"TimePicker");
                break;
            case R.id.bSet:
                if(isAlarmSet == false){

                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH,month);
                    calendar.set(Calendar.DAY_OF_MONTH,day);
                    calendar.set(Calendar.HOUR_OF_DAY,hour);
                    calendar.set(Calendar.MINUTE,min);
                    calendar.set(Calendar.SECOND, 0);
                    long time = calendar.getTimeInMillis() - System.currentTimeMillis();
                    Log.i("info",time + "");
                    if(time <= 0){
                        Toast.makeText(context,"Time Selected has already passed",Toast.LENGTH_LONG).show();
                        break;
                    }

                    isAlarmSet = true;
                    bSet.setText("CANCEL");
                    i = new Intent(context, ReminderService.class);
                    i.setAction("Reminder_Service_Action");
                    i.putExtra("msg", etMsg.getText().toString());
                    pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);



                    alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pi);
                    editor = sharedpreferences.edit();
                    editor.putString("alarmTime", getAlarmTime());
                    editor.commit();
                    tvRemYes.setVisibility(View.VISIBLE);
                    rlRemNo.setVisibility(View.GONE);
                    tvRemYes.setText("Reminder Set at \n\n" + sharedpreferences.getString("alarmTime", ""));
                }else{
                    isAlarmSet = false;
                    bSet.setText("SET REMINDER");
                    alarmManager.cancel(pi);
                    editor = sharedpreferences.edit();
                    editor.remove("alarmTime");
                    editor.commit();
                    rlRemNo.setVisibility(View.VISIBLE);
                    tvRemYes.setVisibility(View.GONE);
                }
                break;
        }
    }

    private String getAlarmTime() {
        StringBuilder sb = new StringBuilder();
        Date d = calendar.getTime();
        sb.append(d.getDate()).append("/").append(d.getMonth()).append("/").append(d.getYear()%100)
                .append("\n").append(d.getHours() < 10 ? "0"+d.getHours() : d.getHours()).append(" : ")
                .append(d.getMinutes() < 10 ? "0"+d.getMinutes() : d.getMinutes());

        return sb.toString();
    }


    private void showDate(int year, int month, int day) {
        tvDateSet.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private void showTime(int hourOfDay, int minute) {
        StringBuilder sb = new StringBuilder();
        if(hourOfDay%12 < 10){
            sb.append(0);
        }
        sb.append(hourOfDay % 12).append(" : ");

        if(minute < 10){
            sb.append("0");
        }
        sb.append(minute).append("  ");
        if(hourOfDay/12 == 0){
            sb.append("AM");
        }else{
            sb.append("PM");
        }
        tvTimeSet.setText(sb.toString());
    }

    public class SelectDate extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int y, int monthOfYear, int dayOfMonth) {
            year = y;
            month = monthOfYear ;
            day = dayOfMonth;
            showDate(year, month+1, day);
        }

    }
        public class SelectTime extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                return new TimePickerDialog(getActivity(),this,hour,min,false);
            }

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;
                showTime(hourOfDay, minute);
            }
        }

}
