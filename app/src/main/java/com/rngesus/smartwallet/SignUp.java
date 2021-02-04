package com.rngesus.smartwallet;

import android.app.AutomaticZenRule;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class SignUp extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SignUp() {
        // Required empty public constructor
    }
    private TextView Alreadyhaveaccount;
    private EditText iD;
    private EditText Fullname;
    private EditText Password;
    private EditText conpassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ImageButton Male;
    private ImageButton Female;
    private Button Sineup;
    private FrameLayout frame;
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        Alreadyhaveaccount = view.findViewById(R.id.textView4);
        iD = view.findViewById(R.id.email2);
        Fullname = view.findViewById(R.id.ename);
        Password = view.findViewById(R.id.Password);
        conpassword = view.findViewById(R.id.ConPassword);
        Sineup = view.findViewById(R.id.button);
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
        Fullname.addTextChangedListener(new TextWatcher() {
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
        conpassword.addTextChangedListener(new TextWatcher() {
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
        Sineup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkemail();
            }
        });
    }

    
     

    private void chagestate() {
        if (!TextUtils.isEmpty(iD.getText())) {
            if (!TextUtils.isEmpty(Fullname.getText())) {
                if (!TextUtils.isEmpty(Password.getText()) && conpassword.length() >= 8) {
                    if (!TextUtils.isEmpty(conpassword.getText())) {
                        {
                            Sineup.setEnabled(true);
                            Sineup.setTextColor(getResources().getColor(R.color.teal_200));
                        }

                        } else {
                            Sineup.setEnabled(false);
                            Sineup.setTextColor(getResources().getColor(R.color.black));
                        }
                    } else {
                        Sineup.setEnabled(false);
                        Sineup.setTextColor(getResources().getColor(R.color.black));

                    }
                } else {
                    Sineup.setEnabled(false);
                    Sineup.setTextColor(getResources().getColor(R.color.black));
                }

            } else {
                Sineup.setEnabled(false);
                Sineup.setTextColor(getResources().getColor(R.color.black));
            }




    }
    private void checkemail() {

      
        if (iD.getText().toString().matches(emailPattern))
        {
            if(Password.getText().toString().contentEquals(conpassword.getText()))
            {
                bar.setVisibility(View.VISIBLE);
                Sineup.setEnabled(false);
                mAuth.createUserWithEmailAndPassword(iD.getText().toString(),Password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    HashMap<String,Object> userdata=new HashMap<>();
                                    userdata.put("fullname",Fullname.getText().toString());
                                    userdata.put("email",iD.getText().toString());
                                    userdata.put("Profile","");
                                    firebaseFirestore.collection("USERS").add(userdata ).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if(task.isSuccessful())
                                            {
                                                bar.setVisibility(View.INVISIBLE);
                                                iD.setText(" ");
                                                Fullname.setText(" ");
                                                Password.setText(" ");
                                                conpassword.setText(" ");
                                                Intent intent=new Intent(getContext(),MainActivity.class);
                                                startActivity(intent);
                                                getActivity().finish();
                                            }
                                            else
                                            {
                                                bar.setVisibility(View.INVISIBLE);
                                                String error=task.getException().getMessage();
                                                Toast.makeText(getActivity(), "ERROR="+error, Toast.LENGTH_SHORT).show();
                                                Sineup.setEnabled(true);
                                            }

                                        }
                                    });


                                }
                                else
                                {
                                    bar.setVisibility(View.INVISIBLE);
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getActivity(), "ERROR="+error, Toast.LENGTH_SHORT).show();
                                    Sineup.setEnabled(true);

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