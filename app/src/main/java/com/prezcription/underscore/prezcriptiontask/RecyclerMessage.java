package com.prezcription.underscore.prezcriptiontask;

public class RecyclerMessage {

    private int type;
    private String msg;
    private String time;
    private int status;

    public RecyclerMessage(int type,String msg,int status,String time){
        this.type = type;
        this.msg = msg;
        this.status = status;
        this.time = time;
    }

    public void setType(int type) { this.type = type; }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        if(status == 1){
            return "Sent";
        }else if (status == 2){
            return "Delivered";
        }else if(status == 3){
            return "Read";
        }
        return "";
    }

}
