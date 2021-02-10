package com.rngesus.smartwallet;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    EditText etLogin;
    EditText etPass;
    CheckBox cbKeepsigned;
    Button btnLogin;
    TextView tvSignup;
    TextView tvForgotten;
    SharedPref shared;
    public static final String MY_PREFS_FILENAME = "com.rngesus.smartwallet";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        init();


        btnLogin.setOnClickListener(v -> {
            if (cbKeepsigned.isChecked()) {
                shared=new SharedPref();
                shared.SaveData(MainActivity.this,"true",etLogin.getText().toString(),etPass.getText().toString());
                signin(etLogin.getText().toString(), etPass.getText().toString());

            }
            signin(etLogin.getText().toString(), etPass.getText().toString());
        });

        tvSignup.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();

        });

        tvForgotten.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, ForgottenPassword.class);
            startActivity(intent);
            finish();
        });

    }

    public void init() {
        etLogin = findViewById(R.id.etLogin);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignup = findViewById(R.id.tvSignup);
        tvSignup.setPaintFlags(tvSignup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tvForgotten = findViewById(R.id.tvFrogotten);
        tvForgotten.setPaintFlags(tvForgotten.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        cbKeepsigned = findViewById(R.id.cbSignedin);

        shared=new SharedPref();
        shared.LoadData(MainActivity.this);
        String message = shared.LoadData(MainActivity.this);
        String[] str = new String[3];

        for(int i=0; i<message.length(); i++)
        {
            str=message.split(",");
        }

        if (str[0].equals("true")) {
            String login = str[1];
            String pass = str[2];
            signin(login,pass);

        }


    }

    public void signin(String login, String pass) {
        if(login.isEmpty() && pass.isEmpty())
        {
            Toast.makeText(this, "Login or password is Empty", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(login, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                        startActivity(intent);
                        etLogin.setText("");
                        etPass.setText("");
                        // use this to get user details in main program
                        // FirebaseUser user = mAuth.getCurrentUser();

                    } else {
                        // If sign in fails, display a message to the user.
                        String error = task.getException().getMessage();
                        Toast.makeText(MainActivity.this, "ERROR=" + error, Toast.LENGTH_SHORT).show();
                    }


                }


            });
        }


    }
}