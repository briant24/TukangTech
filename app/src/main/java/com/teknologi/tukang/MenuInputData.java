package com.teknologi.tukang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.security.auth.login.LoginException;

public class MenuInputData extends AppCompatActivity {

    private TextView admin;
    private Button bahan,alat,tukang,logout;
    private String strnama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        admin = findViewById(R.id.txt_admin);
        bahan = findViewById(R.id.btnBahan);
        alat = findViewById(R.id.btnAlat);
        tukang = findViewById(R.id.btnTukang);
        logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(getApplicationContext(), ActivityLogin.class);
                startActivity(logout);
            }
        });
        bahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bahanbaku = new Intent(MenuInputData.this, InputBahanBaku.class);
                startActivity(bahanbaku);
            }
        });
        alat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alatbrt = new Intent(MenuInputData.this, InputAlatBerat.class);
                startActivity(alatbrt);
            }
        });
        tukang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tukangg = new Intent(MenuInputData.this, InputTenagaTukang.class);
                startActivity(tukangg);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        Intent ambildata=getIntent();
        strnama = ambildata.getStringExtra("nama");
        admin.setText("Admin, " + strnama);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
    }
}