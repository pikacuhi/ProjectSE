package com.example.projectse;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class LockResDetailsActivity extends AppCompatActivity {
    private TextView stationName;
    private TextView lockerCode;
    private TextView resStatus;
    private LinearLayout navHome;
    private LinearLayout navHistory;
    private LinearLayout scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockres_details);

        stationName = findViewById(R.id.station_name);
        lockerCode = findViewById(R.id.locker_code);
        resStatus = findViewById(R.id.res_status);
        navHome = findViewById(R.id.nav_home);
        navHistory = findViewById(R.id.nav_history);
        scanner = findViewById(R.id.scanner);

        Intent intent = getIntent();

        if (intent != null) {
            int name = intent.getIntExtra("name", -1);
            int code = intent.getIntExtra("code", -1);
            int status = intent.getIntExtra("status", -1);

            if (name != -1) {
                stationName.setText(name);
            }

            if (code != -1) {
                lockerCode.setText(code);
            }

            if (status != -1) {
                resStatus.setText(status);
            }
        }

        resStatus.setOnClickListener(v -> {
            String currentText = resStatus.getText().toString();
            String endReservationText = getString(R.string.end_reservation_text);

            if (currentText.equals(endReservationText)) {
                resStatus.setText(R.string.completed_res_text);
            }
            else{
                Intent locker = new Intent(LockResDetailsActivity.this, LockerReservationActivity.class);
                startActivity(locker);
            }
        });

        navHome.setOnClickListener(v -> {
            Intent home = new Intent(LockResDetailsActivity.this, HomeActivity.class);
            startActivity(home);
        });

        navHistory.setOnClickListener(v -> {
            Intent history = new Intent(LockResDetailsActivity.this, HistoryActivity.class);
            startActivity(history);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(LockResDetailsActivity.this);
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
