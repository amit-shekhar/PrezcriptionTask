package com.prezcription.underscore.prezcriptiontask;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ImageTask extends Fragment implements View.OnClickListener {

    private Context context;
    private ImageView image;
    private ProgressBar pr;
    private Button bLoad;
    private ImageDownloader downloader;
    private Bitmap errorImage;

    private String[] imageURLs = {
            "http://www.hdwallpapers.in/download/lamborghini_huracan_lp_610_4-1920x1080.jpg",
            "http://www.hdwallpapers.in/download/sunny_fall_day-1920x1080.jpg",
            "http://www.hdwallpapers.in/download/hank_finding_dory-2560x1440.jpg",
            "http://www.hdwallpapers.in/download/assassins_creed_movie-1920x1200.jpg",
            "http://www.hdwallpapers.in/download/savanna_tiger_wildlife-1440x900.jpg"
    };
    private int count = 0;

    public ImageTask(Context context){
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_main,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image = (ImageView) view.findViewById(R.id.ivShow);
        pr = (ProgressBar) view.findViewById(R.id.progressBar);
        pr.setIndeterminate(false);
        bLoad = (Button) view.findViewById(R.id.bLoad);
        bLoad.setOnClickListener(this);
        errorImage = BitmapFactory.decodeResource(getResources(),R.mipmap.errorimage);

    }

    @Override
    public void onClick(View v) {
        downloader = new ImageDownloader(pr,image,errorImage);
        downloader.execute(imageURLs[count], context.getCacheDir().toString());
        count = ( count+1 )%5;
    }
}
