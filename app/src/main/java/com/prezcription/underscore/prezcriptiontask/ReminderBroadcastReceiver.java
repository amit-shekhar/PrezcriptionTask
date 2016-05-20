package com.prezcription.underscore.prezcriptiontask;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by _underscore on 20-05-2016.
 */
public class ReminderBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("info", "broadcast recieve");
        Intent i = new Intent(context,ReminderShow.class);
        i.putExtra("msg",(String)intent.getStringExtra("msg"));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setAction("Reminder_Show_Action");
        context.startActivity(i);
        Log.i("info", "broadcast passed");
    }
}
