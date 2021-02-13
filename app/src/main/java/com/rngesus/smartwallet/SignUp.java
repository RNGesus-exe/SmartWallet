package com.rngesus.smartwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    private TextView Alreadyhaveaccount;
    private EditText iD;
    private EditText fullname;
    private EditText Password;
    private EditText confirmPassword;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Button signUp;
    private FirebaseAuth mAuth;
    private ProgressBar bar;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        init();
        onView();

    }
    void init()
    {
        Alreadyhaveaccount = findViewById(R.id.tvSignIn);
        Alreadyhaveaccount.setPaintFlags( Alreadyhaveaccount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        iD = findViewById(R.id.email2);
        fullname = findViewById(R.id.ename);
        Password = findViewById(R.id.Password);
        confirmPassword = findViewById(R.id.ConPassword);
        signUp = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        bar=findViewById(R.id.progressBar2);
        bar.setVisibility(View.GONE);
        firebaseFirestore=FirebaseFirestore.getInstance();

    }

    public void onView() {
        iD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeState();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeState();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeState();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeState();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        signUp.setOnClickListener(v -> checkEmail());
    }
    @Override
    public void onBackPressed(){
        return;
    }
    private void changeState() {
        if (!TextUtils.isEmpty(iD.getText())) {
            if (!TextUtils.isEmpty(fullname.getText())) {
                if (!TextUtils.isEmpty(Password.getText()) && confirmPassword.length() >= 8) {
                    if (!TextUtils.isEmpty(confirmPassword.getText())) {
                        {
                            signUp.setEnabled(true);
                            signUp.setTextColor(getResources().getColor(R.color.teal_200));
                        }

                    } else {
                        signUp.setEnabled(false);
                        signUp.setTextColor(getResources().getColor(R.color.black));
                    }
                } else {
                    signUp.setEnabled(false);
                    signUp.setTextColor(getResources().getColor(R.color.black));

                }
            } else {
                signUp.setEnabled(false);
                signUp.setTextColor(getResources().getColor(R.color.black));
            }

        } else {
            signUp.setEnabled(false);
            signUp.setTextColor(getResources().getColor(R.color.black));
        }




    }
    private void checkEmail() {


        if (iD.getText().toString().matches(emailPattern))
        {
            if(Password.getText().toString().contentEquals(confirmPassword.getText()))
            {
                bar.setVisibility(View.VISIBLE);
                signUp.setEnabled(false);
                mAuth.createUserWithEmailAndPassword(iD.getText().toString(),Password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    HashMap<String,Object> userdata=new HashMap<>();
                                    userdata.put("fullname", fullname.getText().toString());
                                    userdata.put("email",iD.getText().toString());
                                    userdata.put("Amount",0);
                                    firebaseFirestore.collection("USERS").document(mAuth.getUid()).set(userdata ).addOnSuccessListener(new OnSuccessListener<Void>()


                                    {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            if(task.isSuccessful())
                                            {
                                                bar.setVisibility(View.INVISIBLE);
                                                iD.setText(" ");
                                                fullname.setText(" ");
                                                Password.setText(" ");
                                                confirmPassword.setText(" ");
                                                Intent intent=new Intent(SignUp.this,MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else
                                            {
                                                bar.setVisibility(View.INVISIBLE);
                                                String error=task.getException().getMessage();
                                                Toast.makeText(SignUp.this, "ERROR="+error, Toast.LENGTH_SHORT).show();
                                                signUp.setEnabled(true);
                                            }

                                        }


                                    });


                                }
                                else
                                {
                                    bar.setVisibility(View.INVISIBLE);
                                    String error=task.getException().getMessage();
                                    Toast.makeText(SignUp.this, "ERROR="+error, Toast.LENGTH_SHORT).show();
                                    signUp.setEnabled(true);

                                }
                            }
                        });
            }
            else
            {
                Password.setError("Password Mismatch");
            }
        }
        else {
            iD.setError("Email Mismatch!");
        }
    }
}