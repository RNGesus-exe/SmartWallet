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
import com.google.zxing.Result;

import java.util.StringTokenizer;

public class scanActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    Dialog dialog;
    ImageButton BtnConfirm;
    ImageButton BtnCancel;
    EditText etTransferAmount;
    String TransferAmount;
    String REmail;
    String RName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        dialog = new Dialog(this);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                     //   Toast.makeText(scanActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                        String scannerOutput = result.getText();
                        StringTokenizer tokens = new StringTokenizer(scannerOutput, ",");
                        REmail = tokens.nextToken();
                        RName = tokens.nextToken();
                        TransferAmount = tokens.nextToken();
                     //   Toast.makeText(scanActivity.this, "Name"+RName, Toast.LENGTH_SHORT).show();
                        confirmation();



                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }
    public void confirmation(){
        dialog.setContentView(R.layout.confirm_view);
//        BtnConfirm = dialog.findViewById(R.id.btnConfirm);
        BtnCancel = dialog.findViewById(R.id.btnCancel);
        etTransferAmount = dialog.findViewById(R.id.etTransferAmount);
        etTransferAmount.setText(TransferAmount);
        loadData();

//        BtnConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadData();
//                executeTransaction();
//            }
//        });

        BtnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

    }
    public void executeTransaction()
    {

    }

    public void loadData()
    {

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
