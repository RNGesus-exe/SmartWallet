package com.rngesus.smartwallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private CircleImageView profileimg;
    private TextView pname,pemail;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String user;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        pname=findViewById(R.id.fullname);
        pemail=findViewById(R.id.nav_email);
        profileimg=findViewById(R.id.profile_image);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.income:
                        Toast.makeText(NavigationActivity.this, "income", Toast.LENGTH_SHORT).show();
                         intent = new Intent(NavigationActivity.this,My_Income.class);
                         startActivity(intent);
                        break;
                    case R.id.Outcome:
                        Toast.makeText(NavigationActivity.this, "Outcome", Toast.LENGTH_SHORT).show();
                        intent = new Intent(NavigationActivity.this,out_come.class);
                        startActivity(intent);
                        break;
                    case R.id.cart:
                        Toast.makeText(NavigationActivity.this, "Cart", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Transfer:
                        Toast.makeText(NavigationActivity.this, "Tranfer Cash", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Recharge:
                        Toast.makeText(NavigationActivity.this, "Rechare Acc", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.QR:
                        Toast.makeText(NavigationActivity.this, "QR CODE", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Setting:
                        Toast.makeText(NavigationActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        SharedPref sharedPref= new SharedPref();
                        sharedPref.removeDataFromPref(NavigationActivity.this);
                        intent= new Intent(NavigationActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;

                }
                return true;

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
      /*  firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
        if(mFirebaseUser != null) {
            user = mFirebaseUser.getUid(); //Do what you need to do with the id
        }
        DocumentReference documentReference=firebaseFirestore.collection("USERS").document(user);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                Toast.makeText(NavigationActivity.this, ""+value.getString("email"), Toast.LENGTH_SHORT).show();
            }
        });*/



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}