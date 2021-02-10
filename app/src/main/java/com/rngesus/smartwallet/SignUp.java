package com.rngesus.smartwallet;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class SignUp extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View view;

    public SignUp() {
        // Required empty public constructor
    }
    private TextView Alreadyhaveaccount;
    private EditText iD;
    private EditText fullname;
    private EditText Password;
    private EditText confirmPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Button signUp;
    private FirebaseAuth mAuth;
    private ProgressBar bar;
    private FirebaseFirestore firebaseFirestore;





    public static SignUp newInstance(String param1, String param2) {
        SignUp fragment = new SignUp();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        Alreadyhaveaccount = view.findViewById(R.id.tvSignIn);
        Alreadyhaveaccount.setPaintFlags( Alreadyhaveaccount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        iD = view.findViewById(R.id.email2);
        fullname = view.findViewById(R.id.ename);
        Password = view.findViewById(R.id.Password);
        confirmPassword = view.findViewById(R.id.ConPassword);
        signUp = view.findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        bar=view.findViewById(R.id.progressBar2);
        bar.setVisibility(View.GONE);
        firebaseFirestore=FirebaseFirestore.getInstance();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iD.addTextChangedListener(new TextWatcher() {
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
        fullname.addTextChangedListener(new TextWatcher() {
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
        Password.addTextChangedListener(new TextWatcher() {
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
        confirmPassword.addTextChangedListener(new TextWatcher() {
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
        Alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkemail();
            }
        });
    }

    
     

    private void chagestate() {
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
    private void checkemail() {

      
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
                                                Intent intent=new Intent(getContext(),MainActivity.class);
                                                startActivity(intent);
                                                getActivity().finish();
                                            }
                                            else
                                            {
                                                bar.setVisibility(View.INVISIBLE);
                                                String error=task.getException().getMessage();
                                                Toast.makeText(getActivity(), "ERROR="+error, Toast.LENGTH_SHORT).show();
                                                signUp.setEnabled(true);
                                            }

                                        }


                                    });


                                }
                                else
                                {
                                    bar.setVisibility(View.INVISIBLE);
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getActivity(), "ERROR="+error, Toast.LENGTH_SHORT).show();
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