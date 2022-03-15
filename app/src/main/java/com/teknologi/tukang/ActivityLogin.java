package com.teknologi.tukang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityLogin extends AppCompatActivity {
    TextInputLayout inputEmail, inputPassword;
    String email,password;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = findViewById(R.id.txt_username);
        inputPassword = findViewById(R.id.txt_password);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        Button btnlogin = (Button) findViewById(R.id.btn_login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = inputEmail.getEditText().getText().toString();
                password = inputPassword.getEditText().getText().toString();
                if (email.isEmpty()){
                    Toast.makeText(ActivityLogin.this, "Email perlu diinputkan!", Toast.LENGTH_SHORT).show();
                    inputEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    Toast.makeText(ActivityLogin.this, "Password perlu diinputkan!", Toast.LENGTH_SHORT).show();
                    inputPassword.requestFocus();
                    return;
                }
                if (password.length() < 6){
                    Toast.makeText(ActivityLogin.this, "Minimal 6 karakter!", Toast.LENGTH_SHORT).show();
                    inputPassword.requestFocus();
                    return;
                }
                else {
                    ceklogin();
                }
            }
        });
    }

    private void ceklogin() {
        email = inputEmail.getEditText().getText().toString();
        password = inputPassword.getEditText().getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ActivityLogin.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ActivityLogin.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void goregister(View view) {
        Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
        startActivity(intent);
    }

    public void golupa(View view) {
        Intent intent = new Intent(ActivityLogin.this, ActivityReset.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        finish();
        System.exit(0);
    }
}