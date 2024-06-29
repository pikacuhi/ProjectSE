package com.example.projectse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import androidx.annotation.NonNull;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        register = findViewById(R.id.register);

        btnLogin.setOnClickListener(v -> {

            Intent home = new Intent(MainActivity.this, HomeActivity.class);

            startActivity(home);
        });

        String text = getString(R.string.sign_up);
        SpannableString spannableString = new SpannableString(text);

        int startIndex = text.indexOf("Sign Up!");
        int endIndex = startIndex + "Sign Up!".length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#0F0AFF")), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        }, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        register.setText(spannableString);
        register.setMovementMethod(LinkMovementMethod.getInstance());

    }
}