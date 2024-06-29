package com.example.projectse;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private TextView login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btnRegister);
        login = findViewById(R.id.login);

        btnRegister.setOnClickListener(v -> {

            Intent login = new Intent(RegisterActivity.this, MainActivity.class);

            startActivity(login);
        });

        String text = getString(R.string.already_hv_acc);
        SpannableString spannableString = new SpannableString(text);

        int startIndex = text.indexOf("Login Now!");
        int endIndex = startIndex + "Login Now!".length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#0F0AFF")), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        login.setText(spannableString);
        login.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
