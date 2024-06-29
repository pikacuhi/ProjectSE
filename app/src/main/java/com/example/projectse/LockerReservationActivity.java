package com.example.projectse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class LockerReservationActivity extends AppCompatActivity {
    private FrameLayout station1;
    private FrameLayout station2;
    private FrameLayout station3;
    private FrameLayout station4;
    private LinearLayout navHome;
    private LinearLayout navHistory;
    private LinearLayout scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker_reservation);

        station1 = findViewById(R.id.station1);
        station2 = findViewById(R.id.station2);
        station3 = findViewById(R.id.station3);
        station4 = findViewById(R.id.station4);
        navHome = findViewById(R.id.nav_home);
        navHistory = findViewById(R.id.nav_history);
        scanner = findViewById(R.id.scanner);

        station1.setOnClickListener(v -> navigateToDetails(R.string.palmerah_station_text, R.string.pal_code, R.string.end_reservation_text));
        station2.setOnClickListener(v -> navigateToDetails(R.string.jakarta_kota_station_text, R.string.jkt_code, R.string.end_reservation_text));
        station3.setOnClickListener(v -> navigateToDetails(R.string.tanah_abang_station_text, R.string.tna_code, R.string.end_reservation_text));
        station4.setOnClickListener(v -> navigateToDetails(R.string.sudirman_station_text, R.string.sdm_code, R.string.end_reservation_text));

        navHome.setOnClickListener(v -> {
            Intent home = new Intent(LockerReservationActivity.this, HomeActivity.class);
            startActivity(home);
        });

        navHistory.setOnClickListener(v -> {
            Intent history = new Intent(LockerReservationActivity.this, HistoryActivity.class);
            startActivity(history);
        });

        scanner.setOnClickListener(v -> {
            scanCode();
        });
    }

    private void navigateToDetails(int name, int code, int status) {
        Intent intent = new Intent(LockerReservationActivity.this, LockResDetailsActivity.class);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(LockerReservationActivity.this);
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
