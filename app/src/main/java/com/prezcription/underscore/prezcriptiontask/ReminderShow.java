package com.prezcription.underscore.prezcriptiontask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ReminderShow extends AppCompatActivity implements View.OnClickListener {

    private TextView tvMsgShow;
    Button bDismiss;

    private static final String MyPREFERENCES = "Myprefs";

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("info" , "reached");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_show);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED, WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        tvMsgShow = (TextView) findViewById(R.id.tvMsgShow);
        bDismiss = (Button) findViewById(R.id.bDismiss);

        String msg = (String)getIntent().getStringExtra("msg");
        if(msg == "" || msg == null){
            msg = "NONE";
        }
        tvMsgShow.setText(msg);

        bDismiss.setOnClickListener(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.remove("alarmTime");
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
