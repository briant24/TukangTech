package com.teknologi.tukang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InputData extends AppCompatActivity {

    FloatingActionButton mAddFab, mAddBahan, mAddAlat, mAddTukang;
    TextView addBahanText, addAlatText, addTukangText, mTxt;
    boolean isAllFabsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        mAddFab = findViewById(R.id.fab_add);
        mAddBahan = findViewById(R.id.addbahan);
        mAddAlat = findViewById(R.id.addalat);
        mAddTukang = findViewById(R.id.addtukang);
        addBahanText = findViewById(R.id.add_bahan_action_text);
        addAlatText = findViewById(R.id.add_alat_action_text);
        addTukangText = findViewById(R.id.add_tukang_action_text);
        mTxt = findViewById(R.id.txt);

        mAddAlat.setVisibility(View.GONE);
        mAddTukang.setVisibility(View.GONE);
        mAddBahan.setVisibility(View.GONE);
        addBahanText.setVisibility(View.GONE);
        addAlatText.setVisibility(View.GONE);
        addTukangText.setVisibility(View.GONE);

        isAllFabsVisible = false;

        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAllFabsVisible){
                    mAddBahan.show();
                    mAddAlat.show();
                    mAddTukang.show();
                    addBahanText.setVisibility(View.VISIBLE);
                    addAlatText.setVisibility(View.VISIBLE);
                    addTukangText.setVisibility(View.VISIBLE);
                    mTxt.setVisibility(View.GONE);

                    isAllFabsVisible = true;
                }else {
                    mAddBahan.hide();
                    mAddAlat.hide();
                    mAddTukang.hide();
                    addBahanText.setVisibility(View.GONE);
                    addAlatText.setVisibility(View.GONE);
                    addTukangText.setVisibility(View.GONE);
                    mTxt.setVisibility(View.VISIBLE);
                    isAllFabsVisible = false;
                }
            }
        });
        mAddBahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputData.this, InputBahanBaku.class);
                startActivity(intent);
            }
        });
        mAddAlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mAddTukang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}