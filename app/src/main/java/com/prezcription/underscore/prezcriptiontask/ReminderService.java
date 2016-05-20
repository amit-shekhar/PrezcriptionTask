package com.prezcription.underscore.prezcriptiontask;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by _underscore on 20-05-2016.
 */
public class ReminderService extends IntentService {

    public ReminderService(String name) {
        super(name);
    }
    public ReminderService() {
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent i = new Intent();
        Log.i("info", "service recieve");
        i.setAction("Reminder_Broadcast_Action");
        i.putExtra("msg", (String) intent.getStringExtra("msg"));
        sendBroadcast(i);
        Log.i("info","service passsed");
    }
}
