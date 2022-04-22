package com.teknologi.tukang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.security.auth.login.LoginException;

public class ActivityRegister extends AppCompatActivity {
    private TextInputLayout inputNama, inputEmail, inputPassword, inputUsername;
    private String email, nama, password, username;
    private CheckBox level;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        inputNama = findViewById(R.id.txt_nama);
        inputEmail = findViewById(R.id.txt_email);
        inputPassword = findViewById(R.id.txt_password);
        inputUsername = findViewById(R.id.txt_username);
        level = findViewById(R.id.lvlUser);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button btnregister = (Button) findViewById(R.id.btn_register);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = inputNama.getEditText().getText().toString();
                password = inputPassword.getEditText().getText().toString();
                email = inputEmail.getEditText().getText().toString();
                username = inputUsername.getEditText().getText().toString();
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
                if (username.isEmpty()){
                    Toast.makeText(ActivityRegister.this, "Username perlu diinputkan!", Toast.LENGTH_SHORT).show();
                    inputUsername.requestFocus();
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
        nama = inputNama.getEditText().getText().toString();
        email = inputEmail.getEditText().getText().toString();
        password = inputPassword.getEditText().getText().toString();
        username = inputUsername.getEditText().getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        if (level.isChecked()){
            String UsrLvl = "1";
            HandlerUser handlerUser = new HandlerUser(nama,email,username,password,UsrLvl);
            String modelId = databaseReference.push().getKey();
            databaseReference.child("Users").child(modelId).setValue(handlerUser);
            Toast.makeText(getApplicationContext(), "Sukses Registrasi Admin", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
            startActivity(intent);
        }else  if (!level.isChecked()){
            String UsrLvl = "0";
            HandlerUser handlerUser = new HandlerUser(nama, email, username, password, UsrLvl);
            String modelId = databaseReference.push().getKey();
            databaseReference.child("Users").child(modelId).setValue(handlerUser);
            Toast.makeText(getApplicationContext(), "Sukses Registrasi User", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void gologin(View view) {
        Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
        startActivity(intent);
    }
}