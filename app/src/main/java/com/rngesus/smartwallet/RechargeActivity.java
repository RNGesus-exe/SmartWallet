package com.rngesus.smartwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class RechargeActivity extends AppCompatActivity {

    private EditText et_recharge;
    private FirebaseFirestore db;
    private ArrayList<Cards> pin_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_account);
        init();
    }

    public interface RechargeCallBack{
        void on_load_callback(ArrayList<Cards> pin_list);
    }

    private void init() {
        getSupportActionBar().setTitle("Recharge");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_recharge = findViewById(R.id.et_recharge);
        db = FirebaseFirestore.getInstance();
    }

    public void Recharge(View view) {
        String pin = et_recharge.getText().toString().trim();
        if(pin.isEmpty()){
            Toast.makeText(this, "Please enter the pin code first!", Toast.LENGTH_SHORT).show();
        }
        else{
            load_cards(pin_loader -> {
                if(check_card_validation(pin)){
                    this.perform_transaction(pin);
                    et_recharge.setText("");
                    Date currentTime = Calendar.getInstance().getTime();
                    String Str = currentTime.toString();
                    String[] allParts = Str.split("\\s+");
                    String date = allParts[0] + ", " + allParts[1] + ", " + allParts[2];
                    String time = allParts[3];
                    DataManager dataManager = new DataManager();
                    dataManager.addIncomeReceipt(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            date, time, "Recharge Shop",
                            "QR Transfer",String.valueOf(get_amount(pin)), view, false);
                }
                else{
                    Toast.makeText(RechargeActivity.this, "The entered pin was incorrect!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private int get_amount(String pin){
        for(Cards card: pin_list){
            if(card.getID().equals(pin)){
                return card.getRecharge();
            }
        }
        return 0;
    }

    private void perform_transaction(String pin){
        db.runTransaction((Transaction.Function<Integer>) transaction -> {
            DocumentReference user_ref = db.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            DocumentSnapshot RSnapshot = transaction.get(user_ref);
            Long updated_user_amount = RSnapshot.getLong("Amount") + (this.get_amount(pin));
            transaction.update(user_ref, "Amount", updated_user_amount);
            return null;
        })
                .addOnCompleteListener(task -> Toast.makeText(RechargeActivity.this,
                        "Recharge Complete",Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(RechargeActivity.this,
                        "Failed Transfer"+ e.getMessage(),Toast.LENGTH_SHORT).show());
    }

    private boolean check_card_validation(String pin){
        for(Cards card : pin_list){
            if(card.getID().equals(pin)){
                return true;
            }
        }
        return false;
    }
    @Override
    public void onBackPressed(){
        return;
    }

    private void load_cards(RechargeCallBack rechargeCallBack){
        db.collection("Cards")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Cards card = new Cards(document.getId(),Integer.parseInt(document.get("recharge").toString()));
                            pin_list.add(card);
                        }
                        rechargeCallBack.on_load_callback(pin_list);
                    } else {
                        Toast.makeText(this, "An error occurred while Loading!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}