package com.prezcription.underscore.prezcriptiontask;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecyclerTask extends Fragment {

    private Context context;

    private RecyclerDBHelper helper;
    private ArrayList<RecyclerMessage> messages;
    private RecyclerView recyclerView;
    private RecyclerMessageAdapter adapter;

    public void setContext(Context context){
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_main,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

        helper = new RecyclerDBHelper(context);
        helper.createSampleData();
        messages = helper.getAllContents();
        adapter = new RecyclerMessageAdapter(messages);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

}
