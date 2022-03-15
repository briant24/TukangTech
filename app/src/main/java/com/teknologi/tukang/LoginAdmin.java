package com.teknologi.tukang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginAdmin extends AppCompatActivity {
    TextInputLayout inputUsername, inputPassword;
    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        inputUsername = findViewById(R.id.txt_username);
        inputPassword = findViewById(R.id.txt_password);

        Button btnlogin = (Button) findViewById(R.id.btn_login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = inputUsername.getEditText().getText().toString();
                password = inputPassword.getEditText().getText().toString();
                if (username.isEmpty()){
                    Toast.makeText(LoginAdmin.this, "Username perlu diinputkan!", Toast.LENGTH_SHORT).show();
                    inputUsername.requestFocus();
                }
                if (password.isEmpty()){
                    Toast.makeText(LoginAdmin.this, "Password perlu diinputkan!", Toast.LENGTH_SHORT).show();
                    inputPassword.requestFocus();
                }
                else {
                    ceklogin();
                }
            }
        });
    }

    private void ceklogin() {
        username = inputUsername.getEditText().getText().toString();
        password = inputPassword.getEditText().getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (username.equals("admin") && password.equals("admin")){
            Toast.makeText(LoginAdmin.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginAdmin.this, InputData.class);
            startActivity(intent);
        }
        else {
            //Toast.makeText(LoginAdmin.this, "Gagal! Username atau Password Salah!", Toast.LENGTH_SHORT).show();
            builder.setMessage("Gagal! Username atau Password Salah!")
                    .setNegativeButton("Coba Lagi",null).create().show();
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(LoginAdmin.this, MainActivity.class);
        startActivity(intent);
    }
}