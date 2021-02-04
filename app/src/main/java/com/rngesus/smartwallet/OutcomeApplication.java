package com.rngesus.smartwallet;

import android.app.Application;

import java.util.ArrayList;

public class OutcomeApplication extends MyActivity {
    public static ArrayList<Outcome_data> arrayList2;
    @Override
    public void onCreate() {
        super.onCreate();
      arrayList2=new ArrayList<Outcome_data>();
      arrayList2.add(new Outcome_data("5","5000","king","why i tell you","monday"));
      arrayList2.add(new Outcome_data("5","5000","king","why i tell you","monday"));
      arrayList2.add(new Outcome_data("5","5000","king","why i tell you","monday"));
      arrayList2.add(new Outcome_data("5","5000","king","why i tell you","monday"));

    }
}
