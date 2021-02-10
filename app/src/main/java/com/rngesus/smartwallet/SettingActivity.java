package com.rngesus.smartwallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    UploadPhoto upload=new UploadPhoto();
    public  static CircleImageView profileimgsetting;
    Button imageadd;
    int key=1;
    EditText email;
    String emailfromAuth,userpass;
    Dialog dialog;
    Button dialogebutton;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    EditText passkey;
    EditText name;
    TextView changenpass;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setTitle(" Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initi();

    }
    void initi()
    {
        profileimgsetting=findViewById(R.id.profile_setting);
        upload.onStart(SettingActivity.this,profileimgsetting);
        email=findViewById(R.id.NewEmail);
        name=findViewById(R.id.NameS);
        changenpass=findViewById(R.id.chagnepass);
        dialog=new Dialog(SettingActivity.this);
        dialog.setContentView(R.layout.passswordconfirmdialogue);
        dialogebutton=dialog.findViewById(R.id.dialogebtn);
        passkey=dialog.findViewById(R.id.passkey);

        changenpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(SettingActivity.this,ChangePassword.class);
                startActivity(intent);
            }
        });


    }
    public Context getContext()
    {
        return SettingActivity.this;
    }

    public void Setimage(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,key);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                upload.Uploadimage(selectedImage, SettingActivity.this, profileimgsetting);

            }
        }
    }
    @Override
    public void onBackPressed(){
        return;
    }
    public void Remove(View view) {
        upload.deleteimage(this);
    }
    public void getAuth()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.getUid();
        emailfromAuth=user.getEmail();

    }




    public void updateEmail(View view) {
        if(email.getText().toString().matches(emailPattern))
        { getAuth();
            if(email.getText().toString().trim().equals(emailfromAuth)) { /*don't do anything*/}
            else { dialog.show();
                dialogebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userpass=passkey.getText().toString();
                        if(!userpass.isEmpty())
                        {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            AuthCredential credential= EmailAuthProvider.getCredential(emailfromAuth,userpass);
                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {

                                        user.updateEmail(email.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            dialog.dismiss();
                                                            final DocumentReference docref=FirebaseFirestore.getInstance().collection("USERS").document(user.getUid());
                                                            Map<String,Object>map=new HashMap<>();
                                                            map.put("email",email.getText().toString());
                                                            docref.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Toast.makeText(SettingActivity.this, "Fire store is updated", Toast.LENGTH_SHORT).show();
                                                                    email.setText("");
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(SettingActivity.this, "Some thing is wrong", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                        }
                                                        else
                                                        {
                                                            String error=task.getException().getMessage();
                                                            Toast.makeText(SettingActivity.this, "ERROR="+error, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                    else
                                    {

                                        String error=task.getException().getMessage();
                                        Toast.makeText(SettingActivity.this, "ERROR="+error, Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });


                        }
                        else
                        {
                            Toast.makeText(SettingActivity.this, "Please enter Password to proceed", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                }
        }
        else
        {
            Toast.makeText(this, "Invalid Email or field is empty", Toast.LENGTH_SHORT).show();
        }
        updateuser();
    }

    private void updateuser() {
        String getname=name.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(!getname.isEmpty())
        {
            final DocumentReference docref=FirebaseFirestore.getInstance().collection("USERS").document(user.getUid());
            Map<String,Object>map=new HashMap<>();
            map.put("fullname",name.getText().toString());
            docref.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(SettingActivity.this, "Name is updated", Toast.LENGTH_SHORT).show();
                    name.setText("");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SettingActivity.this, "Some thing is wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}