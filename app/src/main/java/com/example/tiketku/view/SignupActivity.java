package com.example.tiketku.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tiketku.R;
import com.example.tiketku.presenter.signup.SignupPresenter;
import com.example.tiketku.presenter.signup.SignupView;
import com.example.tiketku.utils.ImageAttachmentListener;
import com.example.tiketku.utils.ImageUtils;

import java.io.File;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SignupActivity extends AppCompatActivity implements ImageAttachmentListener, SignupView {

    private ImageUtils imageutils;
    File photo;
    ProgressBar progressBar;
    CircleImageView imageProfile;
    Button bttnPlus,bttnContinue;
    LinearLayout bgLoading;
    EditText nama,bio,idProfile,username,password,emailAddress,money;

    // todo 7 (Signup) deklarasi presenter
    private SignupPresenter signupPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // todo 8 (Signup) inisialisasi presenter
        signupPresenter = new SignupPresenter(this);

        imageProfile = findViewById(R.id.imgProfile);
        bttnPlus = findViewById(R.id.buttonPlus);
        bttnContinue = findViewById(R.id.bttnContinue);
        nama = findViewById(R.id.edtNama);
        bio = findViewById(R.id.edtBio);
        idProfile = findViewById(R.id.edtIdProfile);
        username = findViewById(R.id.edtUsername);
        password = findViewById(R.id.edtPassword);
        money = findViewById(R.id.edtMoney);
        emailAddress = findViewById(R.id.edtEmailAddress);
        progressBar = findViewById(R.id.progressBar);
        bgLoading = findViewById(R.id.bgLoading);

        idProfile.setText(UUID.randomUUID().toString());

        imageutils = new ImageUtils(this);

        if(Build.VERSION.SDK_INT>=23)
        {
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }
        }

        bttnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageutils.imagepicker(1);
            }
        });

        bttnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                bttnPlus.setVisibility(View.GONE);
                nama.setEnabled(false);
                bio.setEnabled(false);
                username.setEnabled(false);
                password.setEnabled(false);
                emailAddress.setEnabled(false);
                bttnContinue.setEnabled(false);
                bttnContinue.setText("LOADING...");
                bgLoading.setVisibility(View.VISIBLE);

                if (photo!=null)
                {
                    //todo 2 (Signup) inisialisasi variabel

                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),photo);

                    //nama
                    RequestBody requestBodyNama = requestBody.create(MediaType.parse("text/plain"),nama.getText().toString());

                    //bio
                    RequestBody requestBodyBio = requestBody.create(MediaType.parse("text/plain"),bio.getText().toString());

                    //money
                    RequestBody requestBodyMoney = requestBody.create(MediaType.parse("text/plain"),money.getText().toString());

                    //idProfile
                    RequestBody requestBodyIdProfile = requestBody.create(MediaType.parse("text/plain"),idProfile.getText().toString());

                    //username
                    RequestBody requestBodyUsername = requestBody.create(MediaType.parse("text/plain"),username.getText().toString());

                    //password
                    RequestBody requestBodyPassword = requestBody.create(MediaType.parse("text/plain"),password.getText().toString());

                    //email address
                    RequestBody requestBodyEmailAddress = requestBody.create(MediaType.parse("text/plain"),emailAddress.getText().toString());

                    //photo
                    MultipartBody.Part multipartBodyPhoto =MultipartBody.Part.createFormData("foto",photo.getName(),requestBody);

                    // todo 10 (Signup) eksekusi presenter
                    signupPresenter.signup(requestBodyIdProfile,requestBodyUsername,requestBodyPassword,requestBodyEmailAddress,requestBodyNama,requestBodyBio,requestBodyMoney,multipartBodyPhoto);

                }
                else
                {
                    toast("Foto tidak boleh kosong");
                    progressBar.setVisibility(View.GONE);
                    bttnPlus.setVisibility(View.VISIBLE);
                    nama.setEnabled(true);
                    bio.setEnabled(true);
                    username.setEnabled(true);
                    password.setEnabled(true);
                    emailAddress.setEnabled(true);
                    bttnContinue.setEnabled(true);
                    bttnContinue.setText("CONTINUE");
                    bgLoading.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        imageProfile.setImageBitmap(file);

        String path = imageutils.getPath(uri);
        photo = new File(path);
    }

    // todo 9 (Signup) implementasi presenter

    @Override
    public void onSuccess(String message) {
        toast("Berhasil");
        setResult(Activity.RESULT_OK);
        Intent intent = new Intent(SignupActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(String message) {

        toast("Gagal");
        bttnPlus.setVisibility(View.VISIBLE);
        nama.setEnabled(true);
        bio.setEnabled(true);
        username.setEnabled(true);
        password.setEnabled(true);
        emailAddress.setEnabled(true);
        bttnContinue.setEnabled(true);
        bttnContinue.setText("CONTINUE");
        bgLoading.setVisibility(View.GONE);
    }

    @Override
    public void inputKosong() {
        toast("Tidak Boleh Kosong");
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void hideLoading() {
        bttnPlus.setVisibility(View.VISIBLE);
        nama.setEnabled(true);
        bio.setEnabled(true);
        username.setEnabled(true);
        password.setEnabled(true);
        emailAddress.setEnabled(true);
        bttnContinue.setEnabled(true);
        bttnContinue.setText("CONTINUE");
        bgLoading.setVisibility(View.GONE);

    }

    @Override
    public void emailAda() {
        toast("Email Sudah Ada");
        bgLoading.setVisibility(View.GONE);
    }

    void toast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}