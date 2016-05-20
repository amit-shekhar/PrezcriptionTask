package com.prezcription.underscore.prezcriptiontask;

import android.graphics.Color;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerMessageAdapter extends RecyclerView.Adapter<RecyclerMessageAdapter.MyViewHolder> {

    private ArrayList<RecyclerMessage> messages;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView msg,stat;
        public LinearLayout llrow;
        public CardView card;

        public MyViewHolder(View itemView) {
            super(itemView);
            msg = (TextView) itemView.findViewById(R.id.tvChatMsg);
            stat = (TextView) itemView.findViewById(R.id.tvMsgStat);
            llrow = (LinearLayout)itemView.findViewById(R.id.llRow);
            card = (CardView) itemView.findViewById(R.id.card);
        }
    }

    public RecyclerMessageAdapter(ArrayList<RecyclerMessage> messages){
        this.messages = messages;
    }

    @Override
    public RecyclerMessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_msg_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerMessageAdapter.MyViewHolder holder, int position) {
        RecyclerMessage m = messages.get(position);
        StringBuilder sb = new StringBuilder();
        if(m.getType() == 0){
            holder.card.setBackgroundColor(Color.rgb(190, 250, 190));
            holder.llrow.setGravity(Gravity.RIGHT);
            sb.append(" (" + m.getStatus() + ")");
        }else{
            holder.card.setBackgroundColor(Color.rgb(175,175,250));
            holder.llrow.setGravity(Gravity.LEFT);

        }

        holder.msg.setText(m.getMsg());
        holder.stat.setText(m.getTime() + sb.toString());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
