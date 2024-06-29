package com.example.projectse;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class ReportLostItemActivity extends AppCompatActivity {
    private Button btnSubmit;
    private LinearLayout navHome;
    private LinearLayout navHistory;
    private LinearLayout scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_lost_item);

        btnSubmit = findViewById(R.id.btnSubmit);
        navHome = findViewById(R.id.nav_home);
        navHistory = findViewById(R.id.nav_history);
        scanner = findViewById(R.id.scanner);

        btnSubmit.setOnClickListener(v -> showCustomDialog());

        navHome.setOnClickListener(v -> {
            Intent home = new Intent(ReportLostItemActivity.this, HomeActivity.class);
            startActivity(home);
        });

        navHistory.setOnClickListener(v -> {
            Intent history = new Intent(ReportLostItemActivity.this, HistoryActivity.class);
            startActivity(history);
        });

        scanner.setOnClickListener(v -> {
            scanCode();
        });
    }

    private void showCustomDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert2);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setDimAmount(0.7f);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);

        TextView message = dialog.findViewById(R.id.dialog_message);

        message.setText(getString(R.string.thank_you_text));

        dialog.setOnDismissListener(dialog1 -> {
            startActivity(new Intent(ReportLostItemActivity.this, LostnFoundActivity.class));
        });

        dialog.show();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(ReportLostItemActivity.this);
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
