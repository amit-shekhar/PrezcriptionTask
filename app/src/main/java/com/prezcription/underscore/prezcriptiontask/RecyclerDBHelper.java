package com.prezcription.underscore.prezcriptiontask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;

import java.util.ArrayList;

public class RecyclerDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "chatDB.db";
    private static final int SENT = 1;
    private static final int DELIVERED = 2;
    private static final int READ = 3;
    private static final int SEND = 0;
    private static final int RECEIVED = 1;
    private static final int NA = -1;

    public RecyclerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS chat" +
                        "(id integer primary key,type int,msg text,status integer,time string);"
        );

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS chat");
        onCreate(db);
    }

    public boolean insert(int id,int type,String msg,int status,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id",id);
        cv.put("type",type);
        cv.put("msg",msg);
        cv.put("status",status);
        cv.put("time", time.toString());
        db.insert("chat", null, cv);
        return true;
    }

    public ArrayList<RecyclerMessage> getAllContents(){
        ArrayList<RecyclerMessage> ar = new ArrayList<RecyclerMessage>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from chat",null);
        c.moveToFirst();

        while(c.isAfterLast() == false){
            ar.add(getMessage(c));
            c.moveToNext();
        }
        return ar;
    }

    private RecyclerMessage getMessage(Cursor c) {
        int type;
        int status;
        String msg;
        String time;

        type = c.getInt(c.getColumnIndex("type"));
        msg = c.getString(c.getColumnIndex("msg"));
        status = c.getInt(c.getColumnIndex("status"));
        time = c.getString(c.getColumnIndex("time"));

        return new RecyclerMessage(type,msg,status,time);

    }


    public void createSampleData() {
        insert(0,SEND,"one",READ,"11:23");
        insert(1,RECEIVED,"two",NA,"11:24");
        insert(2,SEND,"three",READ,"11:25");
        insert(3,RECEIVED,"four",NA,"11:26");
        insert(4,RECEIVED,"five",NA,"11:27");
        insert(5,SEND,"six seven eight nine ten",READ,"11:28");
        insert(6,RECEIVED,"eleven twelve",NA,"11:28");
        insert(7,SEND,"thirteen",READ,"11:29");
        insert(8,SEND,"fourteen",READ,"11:30");
        insert(9,RECEIVED,"fifteen",NA,"11:30");
        insert(10,SEND,"sixteen seventeen",READ,"1:31");
        insert(11,RECEIVED,"eighteen nineteen twenty",NA,"11:31");
        insert(12,SEND,"twenty one",READ,"11:32");
        insert(14,RECEIVED,"twenty two twenty three twenty four",NA,"11:32");
        insert(13,SEND,"twenty six",DELIVERED,"11:33");
        insert(15,SEND,"hundred",SENT,"11:54");
    }
}
