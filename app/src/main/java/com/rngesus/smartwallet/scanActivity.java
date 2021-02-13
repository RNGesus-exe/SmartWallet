package com.rngesus.smartwallet;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.type.DateTime;
import com.google.zxing.Result;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.StringTokenizer;

public class scanActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String userEmail =  firebaseAuth.getCurrentUser().getEmail();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CodeScanner mCodeScanner;
    Dialog dialog;
    ImageButton BtnCancel;
    EditText etTransferAmount;
    String TransferAmount;
    String REmail;
    String RUid;

    ArrayList<Profile> profile_list = new ArrayList<>();

    public interface MyCallback {
        void onCallback(ArrayList<Profile> profile_list);
    }

    private void load_Profiles(MyCallback callback){
        db.collection("USERS")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Profile profile = new Profile(document.getId(), Integer.parseInt(document.get("Amount").toString()));
                            profile_list.add(profile);
                        }
                        callback.onCallback(profile_list);
                    } else {
                        Toast.makeText(scanActivity.this, "An error occurred while Loading!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private boolean check_amount(String Doc_id, int transferAmount){
        for(Profile profile : profile_list){
            if(profile.getDocumentId().equals(Doc_id) && profile.getAmount() >= transferAmount){
                return true;
            }
        }
        return false;
    }
    public void executeTransaction(DocumentReference SDocRef, DocumentReference RDocRef, int transferAmount)
    {
        db.runTransaction((Transaction.Function<Integer>) transaction -> {
            DocumentSnapshot senderSnapshot = transaction.get(SDocRef);
            Long newUserAmount = senderSnapshot.getLong("Amount") - transferAmount;
            DocumentSnapshot RSnapshot = transaction.get(RDocRef);
            Long newRAmount = RSnapshot.getLong("Amount") + transferAmount;
            transaction.update(SDocRef, "Amount",newUserAmount);
            transaction.update(RDocRef, "Amount", newRAmount);
            return null;
        }).addOnCompleteListener(task -> Toast.makeText(scanActivity.this,"QR Transfer Complete",Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(scanActivity.this,"failed transfer"+ e.getMessage(),Toast.LENGTH_SHORT).show());

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        dialog = new Dialog(this);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                scanActivity.this.runOnUiThread(() -> {
                    String scannerOutput = result.getText();
                    StringTokenizer tokens = new StringTokenizer(scannerOutput, ",");
                    REmail = tokens.nextToken();
                    TransferAmount = tokens.nextToken();
                    RUid = tokens.nextToken();
                    scanActivity.this.confirmation();
                });
            }
        });
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
    }

    public void confirmation(){
        dialog.setContentView(R.layout.confirm_view);
        BtnCancel = dialog.findViewById(R.id.btnCancel);
        etTransferAmount = dialog.findViewById(R.id.etTransferAmount);
        etTransferAmount.setText(TransferAmount);
        BtnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public void btnClicked(View view)
    {
        load_Profiles(profile_list -> {
            if(check_amount(firebaseAuth.getCurrentUser().getUid(),Integer.parseInt(TransferAmount))){
                DocumentReference RDocRef = db.collection("USERS").document(RUid);
                DocumentReference SDocRef =   db.collection("USERS").document(firebaseAuth.getCurrentUser().getUid());
                executeTransaction(SDocRef,RDocRef, Integer.parseInt(TransferAmount));
                //--------ADD RECEIPT FUNCTIONS HERE
                Date currentTime = Calendar.getInstance().getTime();
                String Str = currentTime.toString();
                String[] allParts = Str.split("\\s+");
                String date = allParts[0] + ", " + allParts[1] + ", " + allParts[2];
                String time = allParts[3];
                StringTokenizer tokens = new StringTokenizer(REmail, "@");
                String receiver = tokens.nextToken();
                DataManager dataManager = new DataManager();
                dataManager.addOutcomeReceipt(receiver, date, time, firebaseAuth.getCurrentUser().getUid(), " QR Transfer ", TransferAmount, view, false);
                dataManager = new DataManager();
                tokens = new StringTokenizer(firebaseAuth.getCurrentUser().getEmail(), "@");
                String sender = tokens.nextToken();
                dataManager.addIncomeReceipt(RUid, date, time, sender, "QR Transfer", TransferAmount, view, false);
                finish();
            }else
            {
                Toast.makeText(scanActivity.this," Insufficient Amount",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }
    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}
