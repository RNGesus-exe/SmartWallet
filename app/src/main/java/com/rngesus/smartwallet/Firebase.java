package com.rngesus.smartwallet;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

public class Firebase {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String userEmail =  firebaseAuth.getCurrentUser().getEmail();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ProfileRef = db.collection("USERS");
    private CollectionReference CardRef = db.collection("USERS");
    private DocumentReference userDocRef; // delete this.
    private DocumentReference receiverDocRef;
    private DocumentReference cardDocRef;
    String ReceiverEmail; // delete this.
    String email; // global var used in for each loop
    String CardID;
    int CardAmount;
    int amount;
    boolean EmailFlag = true; // delete this.
    boolean AmountFlag = false; // delete this.

    public void loadCardsData(String ID, Context context)
    {
        Query query;
        query = CardRef.orderBy("ID");
        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Cards card = documentSnapshot.toObject(Cards.class);
                            CardID = card.ID;
                            amount = card.recharge;
                            if (ID.equalsIgnoreCase(CardID)) {
                                cardDocRef = documentSnapshot.getReference();
                                CardAmount = amount;
                            }
                        }

                    }
                }).addOnFailureListener(e -> Toast.makeText(context,"failed to get query results",Toast.LENGTH_SHORT).show());
    }

    public DocumentReference loadReceiverDocRef(String REmail, Context context)
    {

        Query query;
        query = ProfileRef.orderBy("email");
        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Profile profile = documentSnapshot.toObject(Profile.class);

                        email = profile.getEmail();

                        amount = profile.getAmount();


                        if (REmail.equalsIgnoreCase(email))
                        {
                            receiverDocRef = documentSnapshot.getReference();

                        }

                    }

                }).addOnFailureListener(e -> Toast.makeText(context,"failed to get query results",Toast.LENGTH_SHORT).show());
        return receiverDocRef;
    }

    private void executeCardTransaction(Context context, DocumentReference receiverDocRef) {
        db.runTransaction((Transaction.Function<Integer>) transaction -> {
            DocumentSnapshot CardSnapshot = transaction.get(cardDocRef);
            DocumentSnapshot RSnapshot = transaction.get(receiverDocRef);
            Long newRAmount = RSnapshot.getLong("Amount") + CardSnapshot.getLong("recharge");
            transaction.update(cardDocRef, "recharge",0);
            transaction.update(receiverDocRef, "Amount", newRAmount);
            return null;
        }).addOnCompleteListener(task -> Toast.makeText(context,"Complete",Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(context,"failed transfer"+ e.getMessage(),Toast.LENGTH_SHORT).show());
    }
}
