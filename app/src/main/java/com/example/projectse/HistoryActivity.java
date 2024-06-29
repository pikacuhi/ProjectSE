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

public class HistoryActivity extends AppCompatActivity {
    private TextView ongoingStation;
    private TextView historyStation1;
    private TextView historyStation2;
    private TextView historyStation3;
    private TextView historyStation4;
    private LinearLayout navHome;
    private LinearLayout navHistory;
    private LinearLayout scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ongoingStation = findViewById(R.id.view_detail1);
        historyStation1 = findViewById(R.id.view_detail2);
        historyStation2 = findViewById(R.id.view_detail3);
        historyStation3 = findViewById(R.id.view_detail4);
        historyStation4 = findViewById(R.id.view_detail5);
        navHome = findViewById(R.id.nav_home);
        navHistory = findViewById(R.id.nav_history);
        scanner = findViewById(R.id.scanner);

        ongoingStation.setOnClickListener(v -> navigateToDetails(R.string.tanah_abang_station_text, R.string.tna_code, R.string.end_reservation_text));
        historyStation1.setOnClickListener(v -> navigateToDetails(R.string.sudirman_station_text, R.string.sdm_code, R.string.completed_res_text));
        historyStation2.setOnClickListener(v -> navigateToDetails(R.string.tanah_abang_station_text, R.string.tna_code, R.string.completed_res_text));
        historyStation3.setOnClickListener(v -> navigateToDetails(R.string.jakarta_kota_station_text, R.string.jkt_code, R.string.completed_res_text));
        historyStation4.setOnClickListener(v -> navigateToDetails(R.string.tanah_abang_station_text, R.string.tna_code, R.string.completed_res_text));

        navHome.setOnClickListener(v -> {
            Intent home = new Intent(HistoryActivity.this, HomeActivity.class);
            startActivity(home);
        });

        navHistory.setOnClickListener(v -> {
            Intent history = new Intent(HistoryActivity.this, HistoryActivity.class);
            startActivity(history);
        });

        scanner.setOnClickListener(v -> {
            scanCode();
        });
    }

    private void navigateToDetails(int name, int code, int status) {
        Intent intent = new Intent(HistoryActivity.this, LockResDetailsActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("code", code);
        intent.putExtra("status", status);
        startActivity(intent);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
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
