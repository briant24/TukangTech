package com.teknologi.tukang;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ImageView image;
    ProgressBar progressBar;
    int REQUEST_CODE_CAMERA = 0;
    int REQUEST_CODE_GALLERY = 1;

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
                uploadGambar();
            }
        });
    }

    private void uploadGambar(){
        nama = inputNama.getText().toString().trim();
        pcs = inputPcs.getText().toString().trim();
        desc = inputDesc.getText().toString().trim();
        harga = inputHarga.getText().toString().trim();
        if(nama.isEmpty() || harga.isEmpty() || pcs.isEmpty()){
            Toast.makeText(InputBahanBaku.this, "Nama/Pcs/Harga kosong", Toast.LENGTH_SHORT).show();
        }else {
            image.setDrawingCacheEnabled(true);
            image.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytes = stream.toByteArray();
            String namaFile = UUID.randomUUID() + ".jpg";
            String pathImage = "gambar/" + namaFile;
            UploadTask uploadTask = storageReference.child(pathImage).putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(InputBahanBaku.this, "Upload Berhasil!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(InputBahanBaku.this, "Upload Gagal!", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                    double progress = (100.0 *
                            taskSnapshot.getBytesTransferred()) /
                            taskSnapshot.getTotalByteCount();
                    progressBar.setProgress((int) progress);
                }
            });
            databaseReference = FirebaseDatabase.getInstance().getReference("BahanBaku");
            simpan(nama,pcs,desc,harga,namaFile);
        }
    }

    private void pilihgambar() {
        CharSequence[] menu = {"Kamera", "Galeri"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Upload Gambar")
                .setItems(menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent imageIntentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(imageIntentCamera, REQUEST_CODE_CAMERA);
                                break;
                            case 1:
                                Intent imageIntentGalery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(imageIntentGalery, REQUEST_CODE_GALLERY);
                                break;
                        }
                    }
                });
        dialog.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    image.setImageBitmap(bitmap);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    image.setImageURI(uri);
                }
                break;

        }
    }

    public void simpan(String nama, String pcs, String desc,
                       String harga, String namaFile) {
        String key = databaseReference.push().getKey();
        DataBahan dataBahan = new DataBahan(nama, pcs, desc, harga, namaFile);
        databaseReference.child(key).setValue(dataBahan);
        bersih();
    }
    private void bersih(){
        inputNama.setText("");
        inputPcs.setText("");
        inputDesc.setText("");
        inputHarga.setText("");
        image.setVisibility(View.GONE);
    }

}