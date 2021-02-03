package com.rngesus.smartwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgottenPassword extends AppCompatActivity {
    private EditText email;
    private Button reset;
    private TextView Goback;
    private FrameLayout frame;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);
        email=findViewById(R.id.EmailAddress);
        reset=findViewById(R.id.Reset);
        Goback=findViewById(R.id.Goback);
        frame=findViewById(R.id.layout);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar2);


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                chagestate();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                reset.setEnabled(false);
                reset.setTextColor(getResources().getColor(R.color.white));
                firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {

                            Toast.makeText(ForgottenPassword.this, "Recovery email sent successfully ! check your inbox", Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            String error=task.getException().getMessage();
                            Toast.makeText(ForgottenPassword.this, error, Toast.LENGTH_SHORT).show();
                            reset.setEnabled(true);
                            reset.setTextColor(getResources().getColor(R.color.white));

                        }
                        progressBar.setVisibility(View.GONE);

                    }
                });

            }
        });
        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgottenPassword.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void chagestate() {
        if(!TextUtils.isEmpty(email.getText()))
        {
            reset.setEnabled(true);
            reset.setTextColor(getResources().getColor(R.color.white));
        }
        else
        {
            Toast.makeText(this, "Please Write your Email", Toast.LENGTH_SHORT).show();
            reset.setEnabled(false);
            reset.setTextColor(getResources().getColor(R.color.black));
        }
    }
}