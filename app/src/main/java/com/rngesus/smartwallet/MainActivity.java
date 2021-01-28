package com.rngesus.smartwallet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    EditText etLogin;
    EditText etPass;
    CheckBox cbKeepsigned;
    Button btnLogin;
    TextView tvSignup;
    TextView tvFrogotten;
    public static final String MY_PREFS_FILENAME = "com.example.loginfunctionality.Username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        init();


        btnLogin.setOnClickListener(v -> {
            if(cbKeepsigned.isChecked()){
                SharedPreferences.Editor sPrefEditor = getSharedPreferences(MY_PREFS_FILENAME, MODE_PRIVATE).edit();
                sPrefEditor.putString("msg","true");
                sPrefEditor.putString("email", etLogin.getText().toString());
                sPrefEditor.putString("pass", etPass.getText().toString());
                sPrefEditor.commit();

                signin(etLogin.toString().trim(),etPass.toString().trim());

            }
            signin(etLogin.toString().trim(),etPass.toString().trim());




        });

        tvSignup.setOnClickListener(v -> {

            // add your code here

        });

        tvFrogotten.setOnClickListener(v -> {

            // add your code here

        });

    }

    public void init(){
        etLogin = findViewById(R.id.etLogin);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignup = findViewById(R.id.tvSignup);
        tvFrogotten = findViewById(R.id.tvFrogotten);
        cbKeepsigned = findViewById(R.id.cbSignedin);

        SharedPreferences sPrefsReadData = getSharedPreferences(MY_PREFS_FILENAME, MODE_PRIVATE);
        String message = sPrefsReadData.getString("msg", "");
        if (message.equals("true"))
        {
            String login = sPrefsReadData.getString("email", "");
            String pass = sPrefsReadData.getString("pass", "");
            signin(login,pass);


        }




    }

    public void signin(String login, String pass){
        mAuth.signInWithEmailAndPassword(login, pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");

                       // use this to get user details in main program
                       // FirebaseUser user = mAuth.getCurrentUser();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();


                    }


                });

    }


    }
