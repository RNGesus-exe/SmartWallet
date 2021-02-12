package com.rngesus.smartwallet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    public CircleImageView profileimg;
    private TextView pname,pemail;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    String user;
    Intent intent;
    private static ImageView photos;
    int key=0;
    UploadPhoto uploadPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View view=navigationView.getHeaderView(0);
        pname=view.findViewById(R.id.fullname);
        pemail=view.findViewById(R.id.nav_email);
        profileimg=view.findViewById(R.id.profile_image);
        photos=view.findViewById(R.id.addimage);
        storageReference= FirebaseStorage.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        uploadPhoto=new UploadPhoto();
        uploadPhoto.onStart(NavigationActivity.this,profileimg);




        // Load data of  the User
        /*int amount=loadUserBalance();*/



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
                    case R.id.tv_price:
                        Toast.makeText(NavigationActivity.this, "income", Toast.LENGTH_SHORT).show();
                         intent = new Intent(NavigationActivity.this,IncomeRecyclerView.class);
                         startActivity(intent);
                        break;
                    case R.id.Outcome:
                        Toast.makeText(NavigationActivity.this, "Outcome", Toast.LENGTH_SHORT).show();
                        intent = new Intent(NavigationActivity.this,OutcomeRecyclerView.class);
                        startActivity(intent);
                        break;
                    case R.id.cart:
                        intent = new Intent(NavigationActivity.this,ShopActivity.class);
                        startActivity(intent);
                        Toast.makeText(NavigationActivity.this, "Cart", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Transfer:
                        Toast.makeText(NavigationActivity.this, "Transfer Cash", Toast.LENGTH_SHORT).show();
                        intent = new Intent(NavigationActivity.this,TransferActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.Recharge:
                        intent = new Intent(NavigationActivity.this, RechargeActivity.class);
                        startActivity(intent);
                        Toast.makeText(NavigationActivity.this, "Recharge Acc", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.QR:
                        Toast.makeText(NavigationActivity.this, "QR CODE", Toast.LENGTH_SHORT).show();
                        intent = new Intent(NavigationActivity.this,QrcodeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.Setting:
                        Toast.makeText(NavigationActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                        intent= new Intent(NavigationActivity.this,SettingActivity.class);
                        startActivity(intent);
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
        photos.setOnClickListener(v -> {
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,key);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                uploadPhoto=new UploadPhoto();
                uploadPhoto.Uploadimage(selectedImage,NavigationActivity.this,profileimg);
            }
        }
    }





    @Override
    protected void onStart() {
        super.onStart();
        user=firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("USERS").document(user).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                pname.setText(documentSnapshot.getString("fullname"));
                pemail.setText(documentSnapshot.getString("email"));


            }
        });


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