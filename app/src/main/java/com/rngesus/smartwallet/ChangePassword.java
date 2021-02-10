package com.rngesus.smartwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
    Button Updatepass;
    EditText oldpass,newpass,confirmPass;
    String emailfromAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Updatepass=findViewById(R.id.updatepass);
        oldpass=findViewById(R.id.Oldpass);
        newpass=findViewById(R.id.NewPass);
        confirmPass=findViewById(R.id.Conpass);

        Updatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass=newpass.getText().toString();
                String confirms=confirmPass.getText().toString();
                getAuth();
                Toast.makeText(ChangePassword.this, ""+emailfromAuth, Toast.LENGTH_SHORT).show();
                if(newPass.equals(confirms))
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential= EmailAuthProvider.getCredential(emailfromAuth,oldpass.getText().toString());
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            oldpass.setText(null);
                                            newpass.setText(null);
                                            confirmPass.setText(null);
                                            Toast.makeText(ChangePassword.this, "Password change", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            String error=task.getException().getMessage();
                                            Toast.makeText(ChangePassword.this, "ERROR="+error, Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                            }
                            else
                            {
                                String error=task.getException().getMessage();
                                Toast.makeText(ChangePassword.this, "ERROR="+error, Toast.LENGTH_SHORT).show();
                            }
                        }
                        });

                }
                else
                {
                    Toast.makeText(ChangePassword.this, "New Password and ConfirmPassword Must be same", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void getAuth()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.getUid();
        emailfromAuth=user.getEmail();

    }

}