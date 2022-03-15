package com.teknologi.tukang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class ActivityRegister extends AppCompatActivity {
    TextInputLayout inputNama, inputEmail, inputPassword;
    String email, nama, password;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        inputNama = findViewById(R.id.txt_nama);
        inputEmail = findViewById(R.id.txt_email);
        inputPassword = findViewById(R.id.txt_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button btnregister = (Button) findViewById(R.id.btn_register);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = inputNama.getEditText().getText().toString();
                password = inputPassword.getEditText().getText().toString();
                email = inputEmail.getEditText().getText().toString();
                if (nama.isEmpty()){
                    Toast.makeText(ActivityRegister.this, "Nama perlu diinputkan!", Toast.LENGTH_SHORT).show();
                    inputNama.requestFocus();
                    return;
                }
                if (email.isEmpty()){
                    Toast.makeText(ActivityRegister.this, "Email perlu diinputkan!", Toast.LENGTH_SHORT).show();
                    inputEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    Toast.makeText(ActivityRegister.this, "Password perlu diinputkan!", Toast.LENGTH_SHORT).show();
                    inputPassword.requestFocus();
                    return;
                }
                if (password.length() < 6){
                    Toast.makeText(ActivityRegister.this, "Minimal 6 karakter!", Toast.LENGTH_SHORT).show();
                    inputPassword.requestFocus();
                    return;
                }
                else {
                    registrasi();
                }
            }
        });
    }

    private void registrasi() {
        email = inputEmail.getEditText().getText().toString();
        password = inputPassword.getEditText().getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(nama,email,password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(ActivityRegister.this, "Registrasi Berhasil, Silahkan Login", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(ActivityRegister.this, "Registrasi Gagal, Coba Lagi", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(ActivityRegister.this, "Registrasi Gagal, Coba Lagi", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void gologin(View view) {
        Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
        startActivity(intent);
    }
}