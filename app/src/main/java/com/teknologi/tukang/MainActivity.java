package com.teknologi.tukang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView mTvemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvemail = findViewById(R.id.tv_email);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        if (mFirebaseUser!=null){
            mTvemail.setText(mFirebaseUser.getEmail());
        }else {
            Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
            startActivity(intent);
        }
    }

    public void goalatberat(View view) {
        Intent intent = new Intent(MainActivity.this, AlatBerat.class);
        startActivity(intent);
    }

    public void gobahanbaku(View view) {
        Intent intent = new Intent(MainActivity.this, BahanBaku.class);
        startActivity(intent);
    }

    public void gotukang(View view) {
        Intent intent = new Intent(MainActivity.this, SewaTukang.class);
        startActivity(intent);
    }

    public void gomaps(View view) {
    }

    public void gologout(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this, "Log Out Sukses", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
        startActivity(intent);
    }

    public void godata(View view) {
        Intent intent = new Intent(MainActivity.this, LoginAdmin.class);
        startActivity(intent);
    }
}