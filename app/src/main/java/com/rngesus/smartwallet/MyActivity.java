package com.rngesus.smartwallet;

import android.app.Application;

import java.util.ArrayList;

public class MyActivity extends Application {
   public static  ArrayList<Income_data> arrayList;
    @Override
    public void onCreate() {
        super.onCreate();
       arrayList = new ArrayList<>();
       arrayList.add(new Income_data("5","5000","king","why i tell you","monday"));
       arrayList.add(new Income_data("5","5000","king","why i tell you","monday"));
       arrayList.add(new Income_data("5","5000","king","why i tell you","monday"));
       arrayList.add(new Income_data("5","5000","king","why i tell you","monday"));

    }
}
