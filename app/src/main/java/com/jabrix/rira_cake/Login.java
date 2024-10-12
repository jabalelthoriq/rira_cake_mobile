package com.jabrix.rira_cake;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

public class Login extends AppCompatActivity {

    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERNAME = "username";

    private TextView registerTv;
    private Button loginBtn;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.login);
        initViews();
        setupListeners();
        handleIntentExtras();


    }



    private void initViews() {
        registerTv = findViewById(R.id.txt_register);
        loginBtn = findViewById(R.id.btn_login);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtUsername = findViewById(R.id.edt_username);
    }

    private void setupListeners() {
        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(Login.this, Register.class);
                startActivity(r);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void handleIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(KEY_EMAIL)) {
                String email = extras.getString(KEY_EMAIL);
                edtEmail.setText(email);
            }
            if (extras.containsKey(KEY_USERNAME)) {
                String username = extras.getString(KEY_USERNAME);
                edtUsername.setText(username);
            }
        }
    }

    public void login() {
        String enteredPassword = edtPassword.getText().toString();

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedPassword = prefs.getString("password", "");
        String email = edtEmail.getText().toString();
        String username = edtUsername.getText().toString();



        if (enteredPassword.equals(storedPassword)) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Login.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(KEY_EMAIL, email);
            bundle.putString(KEY_USERNAME, username);
            i.putExtras(bundle);
            startActivity(i);
        } else {
            Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
        }
    }
}

