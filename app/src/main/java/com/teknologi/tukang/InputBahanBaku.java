package com.teknologi.tukang;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.teknologi.tukang.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class InputBahanBaku extends AppCompatActivity {
    EditText inputNama, inputPcs, inputDesc, inputHarga;
    String nama, pcs, desc, harga;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Data Bahan Baku");
    ImageView image;
    Uri imageUri;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_bahan_baku);
        inputNama = findViewById(R.id.txt_nama);
        inputPcs = findViewById(R.id.txt_pcs);
        inputDesc = findViewById(R.id.txt_desc);
        inputHarga = findViewById(R.id.txt_harga);
        image = (ImageView) findViewById(R.id.uploadImage);
        progressBar = findViewById(R.id.progressBar1);
        Button btnbrowse = (Button) findViewById(R.id.btnBrowse);
        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihgambar();
            }
        });

        Button btnsave = (Button) findViewById(R.id.btnSave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri != null){
                    uploadGambar(imageUri);
                }else {
                    Toast.makeText(InputBahanBaku.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadGambar(Uri uri){
        nama = inputNama.getText().toString().trim();
        pcs = inputPcs.getText().toString().trim();
        desc = inputDesc.getText().toString().trim();
        harga = inputHarga.getText().toString().trim();

        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        DataBahan dataBahan = new DataBahan(nama, pcs, desc, harga, uri.toString());
                        String modelId = databaseReference.push().getKey();
                        databaseReference.child(modelId).setValue(dataBahan);
                        Toast.makeText(InputBahanBaku.this, "Berhasil!!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        bersih();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InputBahanBaku.this, "Gagal!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    private void pilihgambar() {
        Intent gallerryIntent = new Intent();
        gallerryIntent.setAction(Intent.ACTION_GET_CONTENT);
        gallerryIntent.setType("image/*");
        startActivityForResult(gallerryIntent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }

    private void bersih(){
        inputNama.setText("");
        inputPcs.setText("");
        inputDesc.setText("");
        inputHarga.setText("");
        image.setImageURI(null);
    }

}