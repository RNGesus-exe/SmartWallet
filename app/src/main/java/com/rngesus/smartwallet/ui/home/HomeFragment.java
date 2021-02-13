package com.rngesus.smartwallet.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rngesus.smartwallet.Profile;
import com.rngesus.smartwallet.R;
import com.rngesus.smartwallet.SliderAdapter;
import com.rngesus.smartwallet.Slider;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    private ViewPager bannersliderviewpager;
    private ArrayList<Slider> Sliders;
    private int currentpage=2;
    private Timer timer;
    final private long DELAYTIME = 3000;
    final private long PeriodTIME = 3000;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String userEmail =  firebaseAuth.getCurrentUser().getEmail();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ProfileRef = db.collection("USERS");
    String email;
    int amount;
    public static TextView balance;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bannersliderviewpager=view.findViewById(R.id.bannerview_pager);
        balance=view.findViewById(R.id.balance);

        loadUserBalance(balance,getContext());



        Sliders = new ArrayList<>();

        Sliders.add(new Slider(R.drawable.macmobile));
        Sliders.add(new Slider(R.drawable.dunkin));

        Sliders.add(new Slider(R.drawable.mac));
        Sliders.add(new Slider(R.drawable.macmobile));
        Sliders.add(new Slider(R.drawable.startbucks));
        Sliders.add(new Slider(R.drawable.laptop));
        Sliders.add(new Slider(R.drawable.macmobile));
        Sliders.add(new Slider(R.drawable.dunkin));

        Sliders.add(new Slider(R.drawable.mac));
        Sliders.add(new Slider(R.drawable.macmobile));



        SliderAdapter sliderAdapter = new SliderAdapter(Sliders);
        bannersliderviewpager.setAdapter(sliderAdapter);
        bannersliderviewpager.setClipToPadding(false);
        bannersliderviewpager.setPageMargin(20);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentpage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    pagelooper();

                }
            }
        };
        bannersliderviewpager.addOnPageChangeListener(onPageChangeListener);
        startbannerslideshow();
        bannersliderviewpager.setOnTouchListener((v, event) -> {
            pagelooper();
            stopbanner();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                startbannerslideshow();
            }
            return false;
        });

        return view;
    }

    private void stopbanner() {
        timer.cancel();
    }

    private void startbannerslideshow()
    {
        Handler handler=new Handler();
        Runnable update=new Runnable()
        {
            @Override
            public void run()
            {
                if(currentpage>= Sliders.size())
                {
                    currentpage=1;
                }
                bannersliderviewpager.setCurrentItem(currentpage++,true);

            }
        };
        timer=new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(update);
            }
        },DELAYTIME,PeriodTIME);
    }

    private void loadUserBalance(TextView balance, Context context)
    {



        Query query;
        query = ProfileRef.orderBy("email");
        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Profile profile = documentSnapshot.toObject(Profile.class);


                            email = profile.getEmail();

                            amount = profile.getAmount();

                            if (userEmail.equalsIgnoreCase(email)) {
                                balance.setText("Rs. "+amount);
                            }


                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(context,"failed to get query results",Toast.LENGTH_SHORT).show());
    }



    private  void pagelooper()
    {
        if(currentpage== Sliders.size()-2)
        {
            currentpage=2;
            bannersliderviewpager.setCurrentItem(currentpage,false);

        }
        if(currentpage==1)
        {
            currentpage= Sliders.size()-3;
            bannersliderviewpager.setCurrentItem(currentpage,false);
        }
    }
}