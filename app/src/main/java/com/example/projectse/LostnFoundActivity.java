package com.example.projectse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class LostnFoundActivity extends AppCompatActivity {
    private LinearLayout navHome;
    private LinearLayout navHistory;
    private LinearLayout scanner;
    private TextView reportItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostnfound);

        navHome = findViewById(R.id.nav_home);
        navHistory = findViewById(R.id.nav_history);
        reportItems = findViewById(R.id.report_items);
        scanner = findViewById(R.id.scanner);

        navHome.setOnClickListener(v -> {
            Intent home = new Intent(LostnFoundActivity.this, HomeActivity.class);
            startActivity(home);
        });

        navHistory.setOnClickListener(v -> {
            Intent history = new Intent(LostnFoundActivity.this, HistoryActivity.class);
            startActivity(history);
        });

        reportItems.setOnClickListener(v -> {
            Intent report = new Intent(LostnFoundActivity.this, ReportLostItemActivity.class);
            startActivity(report);
        });

        scanner.setOnClickListener(v -> {
            scanCode();
        });
    }

    private void scanCode(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(LostnFoundActivity.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });
}
