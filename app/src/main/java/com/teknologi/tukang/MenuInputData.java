package com.teknologi.tukang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuInputData extends AppCompatActivity {

    Button bahan,alat,tukang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        bahan = findViewById(R.id.btnBahan);
        alat = findViewById(R.id.btnAlat);
        tukang = findViewById(R.id.btnTukang);
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
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}