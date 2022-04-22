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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityLogin extends AppCompatActivity {
    private TextInputLayout inputUsername, inputPassword;
    private String username,password;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputUsername = findViewById(R.id.txt_username);
        inputPassword = findViewById(R.id.txt_password);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        Button btnlogin = (Button) findViewById(R.id.btn_login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = inputUsername.getEditText().getText().toString();
                password = inputPassword.getEditText().getText().toString();
                if (username.isEmpty()){
                    Toast.makeText(ActivityLogin.this, "Username perlu diinputkan!", Toast.LENGTH_SHORT).show();
                    inputUsername.requestFocus();
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
        username = inputUsername.getEditText().getText().toString();
        password = inputPassword.getEditText().getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.child("Users")
                .orderByChild("username")
                .equalTo(username)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            String pass = data.child("password").getValue().toString();
                            if(pass.equals(password)){
                                String level = data.child("usrLvl").getValue().toString();
                                if(level.equals("1")) {
                                    String nama = data.child("nama").getValue().toString();
                                    Intent adminMenu = new Intent(ActivityLogin.this, MenuInputData.class);
                                    adminMenu.putExtra("nama", nama);
                                    startActivity(adminMenu);
                                }else {
                                    Intent userMenu = new Intent(ActivityLogin.this, MainActivity.class);
                                    startActivity(userMenu);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Username dan Atau Password Yang Anda Masukan Salah", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
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
    }
}