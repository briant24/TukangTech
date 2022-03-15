package com.teknologi.tukang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityReset extends AppCompatActivity {
    Button btnreset;
    TextInputLayout txt_email;
    String email;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        btnreset = findViewById(R.id.btn_reset);
        txt_email = findViewById(R.id.txt_username);
        mAuth = FirebaseAuth.getInstance();

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();
            }
        });
    }

    private void resetpassword() {
        email = txt_email.getEditText().getText().toString();
        if (email.isEmpty()){
            Toast.makeText(this, "Email perlu diinputkan!", Toast.LENGTH_SHORT).show();
            txt_email.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ActivityReset.this, "Berhasi! Silahkan cek email anda", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityReset.this, ActivityLogin.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(ActivityReset.this, "Gagal! Silahkan coba lagi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void gologin(View view) {
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
    }

    public void goregister(View view) {
        Intent intent = new Intent(this, ActivityRegister.class);
        startActivity(intent);
    }
}