package com.teknologi.tukang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class InputBahanBaku extends AppCompatActivity {
    EditText inputNama, inputPcs, inputDesc, inputHarga;
    String nama, pcs, desc, harga;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference dbBahanBaku = FirebaseDatabase.getInstance().getReference().child("Data Bahan Baku");
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
                nama = inputNama.getText().toString().trim();
                pcs = inputPcs.getText().toString().trim();
                desc = inputDesc.getText().toString().trim();
                harga = inputHarga.getText().toString().trim();
                if(nama.isEmpty()){
                    inputNama.setError("Data belum dimasukkan");
                    inputNama.requestFocus();
                }else if (pcs.isEmpty()) {
                    inputPcs.setError("Data belum dimasukkan");
                    inputPcs.requestFocus();
                }else if (desc.isEmpty()){
                    inputDesc.setError("Data belum dimasukkan");
                    inputDesc.requestFocus();
                }else if (harga.isEmpty()){
                    inputHarga.setError("Data belum dimasukkan");
                    inputHarga.requestFocus();
                }else if (imageUri == null){
                    Toast.makeText(InputBahanBaku.this, "Gambar kosong", Toast.LENGTH_SHORT).show();
                }else{
                    uploadGambar(imageUri);
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

                        HandlerUpload handlerUpload = new HandlerUpload(nama, pcs, desc, harga, uri.toString());
                        String modelId = dbBahanBaku.push().getKey();
                        dbBahanBaku.child(modelId).setValue(handlerUpload);
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