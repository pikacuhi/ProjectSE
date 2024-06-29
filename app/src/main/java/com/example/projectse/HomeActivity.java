package com.example.projectse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class HomeActivity extends AppCompatActivity {
    private ViewFlipper promoCarousel;
    private ConstraintLayout lost_found;
    private ConstraintLayout parking;
    private ConstraintLayout locker;
    private ConstraintLayout lost_item;
    private LinearLayout navHome;
    private LinearLayout navHistory;
    private LinearLayout scanner;
    private LinearLayout dotsLayout;
    private ImageView[] dots;
    private int currentPage = 0;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        promoCarousel = findViewById(R.id.promoCarousel);
        dotsLayout = findViewById(R.id.dotsLayout);
        lost_found = findViewById(R.id.lost_found);
        parking = findViewById(R.id.parking);
        locker = findViewById(R.id.locker);
        lost_item = findViewById(R.id.lost_item);
        navHome = findViewById(R.id.nav_home);
        navHistory = findViewById(R.id.nav_history);
        scanner = findViewById(R.id.scanner);

        int numberOfImages = promoCarousel.getChildCount();
        createDots(0, numberOfImages);

        promoCarousel.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right));
        promoCarousel.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_left));

        promoCarousel.getInAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                currentPage = (currentPage + 1) % numberOfImages;
                createDots(currentPage, numberOfImages);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        startAutoSlide(numberOfImages);

        lost_found.setOnClickListener(v -> {
            Intent lostfound = new Intent(HomeActivity.this, LostnFoundActivity.class);
            startActivity(lostfound);
        });

        parking.setOnClickListener(v -> {
            Intent parkres = new Intent(HomeActivity.this, ParkingReservationActivity.class);
            startActivity(parkres);
        });

        locker.setOnClickListener(v -> {
            Intent lockres = new Intent(HomeActivity.this, LockerReservationActivity.class);
            startActivity(lockres);
        });

        lost_item.setOnClickListener(v -> {
            Intent lostitem = new Intent(HomeActivity.this, ReportLostItemActivity.class);
            startActivity(lostitem);
        });

        navHome.setOnClickListener(v -> {
            Intent home = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(home);
        });

        navHistory.setOnClickListener(v -> {
            Intent history = new Intent(HomeActivity.this, HistoryActivity.class);
            startActivity(history);
        });

        scanner.setOnClickListener(v -> {
            scanCode();
        });
    }

    private void createDots(int currentPosition, int numberOfImages) {
        if (dotsLayout != null) {
            dotsLayout.removeAllViews();
        }

        dots = new ImageView[numberOfImages];

        for (int i = 0; i < numberOfImages; i++) {
            dots[i] = new ImageView(this);
            if (i == currentPosition) {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dot_selected));
            } else {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dot_not_selected));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);

            dotsLayout.addView(dots[i], params);
        }
    }

    private void startAutoSlide(final int numberOfImages) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                promoCarousel.showNext();
                handler.postDelayed(this, 3000); // Change interval here (3000ms = 3 seconds)
            }
        };

        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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