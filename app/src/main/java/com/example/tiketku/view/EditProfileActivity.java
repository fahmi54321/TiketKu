package com.example.tiketku.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiketku.R;
import com.example.tiketku.presenter.editProfile.EditProfilePresenter;
import com.example.tiketku.presenter.editProfile.EditProfileView;
import com.example.tiketku.utils.ImageAttachmentListener;
import com.example.tiketku.utils.ImageUtils;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileActivity extends AppCompatActivity implements ImageAttachmentListener, EditProfileView {

    Bundle bundleUsername,bundleNama,bundleBio,bundlePassword,bundleEmailAddress,bundleIdProfile;
    Button buttonEditProfile,buttonPlus;
    CircleImageView imageProfile;
    File photo;
    EditText nama,bio,username,password,emailAddress;
    TextView idProfile;
    ProgressBar progressBar;
    View bgLoading;
    private ImageUtils imageutils;

    // todo 7 (Edit Profile) deklarasi presenter
    EditProfilePresenter editProfilePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // todo 8 (Edit Profile) inisialisasi presenter
        editProfilePresenter = new EditProfilePresenter(this);

        nama = findViewById(R.id.edtNama);
        bio = findViewById(R.id.edtBio);
        username = findViewById(R.id.edtUsername);
        password = findViewById(R.id.edtPassword);
        emailAddress = findViewById(R.id.edtEmailAddress);
        buttonEditProfile = findViewById(R.id.bttnEditProfile);
        buttonPlus = findViewById(R.id.buttonPlus);
        imageProfile = findViewById(R.id.imgProfile);
        idProfile = findViewById(R.id.idProfile);
        progressBar = findViewById(R.id.progressBar);
        bgLoading = findViewById(R.id.bgLoading);

        bundleUsername = getIntent().getExtras();
        username.setText(bundleUsername.getString("usernameProfile"));

        bundleNama = getIntent().getExtras();
        nama.setText(bundleNama.getString("namaProfile"));

        bundleBio = getIntent().getExtras();
        bio.setText(bundleBio.getString("bioProfile"));

        bundlePassword = getIntent().getExtras();
        password.setText(bundlePassword.getString("passwordProfile"));

        bundleEmailAddress = getIntent().getExtras();
        emailAddress.setText(bundleEmailAddress.getString("emailAddressProfile"));

        bundleIdProfile = getIntent().getExtras();
        idProfile.setText(bundleIdProfile.getString("idProfile"));

        imageutils = new ImageUtils(this);

        if(Build.VERSION.SDK_INT>=23)
        {
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }
        }

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageutils.imagepicker(1);
            }
        });

        // todo 2 (Edit Profile) Ambil View
        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonEditProfile.setText("Loading...");
                buttonEditProfile.setEnabled(false);
                bgLoading.setVisibility(View.VISIBLE);
                nama.setEnabled(false);
                bio.setEnabled(false);
                username.setEnabled(false);
                password.setEnabled(false);
                emailAddress.setEnabled(false);
                buttonPlus.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                if (photo!=null)
                {
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),photo);

                    //nama
                    RequestBody requestBodyNama = requestBody.create(MediaType.parse("text/plain"),nama.getText().toString());

                    //bio
                    RequestBody requestBodyBio = requestBody.create(MediaType.parse("text/plain"),bio.getText().toString());

                    //username
                    RequestBody requestBodyUsername = requestBody.create(MediaType.parse("text/plain"),username.getText().toString());

                    //password
                    RequestBody requestBodyPassword = requestBody.create(MediaType.parse("text/plain"),password.getText().toString());

                    //email address
                    RequestBody requestBodyEmailAddress = requestBody.create(MediaType.parse("text/plain"),emailAddress.getText().toString());

                    //id profile
                    RequestBody requestBodyIdProfile = requestBody.create(MediaType.parse("text/plain"),idProfile.getText().toString());

                    //photo
                    MultipartBody.Part multipartBodyPhoto =MultipartBody.Part.createFormData("foto",photo.getName(),requestBody);

                    // todo 10 (Edit Profile) eksekusi presenter
                    editProfilePresenter.editProfile(requestBodyIdProfile,requestBodyUsername,requestBodyPassword,requestBodyEmailAddress,requestBodyNama,requestBodyBio,multipartBodyPhoto);
                }
                else
                {
                    toast("Photo tidak boleh kosong");
                    buttonEditProfile.setText("EDIT PROFILE");
                    buttonEditProfile.setEnabled(true);
                    bgLoading.setVisibility(View.GONE);
                    nama.setEnabled(true);
                    bio.setEnabled(true);
                    username.setEnabled(true);
                    password.setEnabled(true);
                    emailAddress.setEnabled(true);
                    buttonPlus.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
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

    void toast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    // todo 9 (Edit Profile) implementasi presenter
    @Override
    public void onSuccess(String message) {
        toast("Berhasil Update");
        Intent intent = new Intent(EditProfileActivity.this,HomeActivity.class);
        intent.putExtra("id",idProfile.getText().toString());
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(String message) {
        toast("Berhasil Update");
        Intent intent = new Intent(EditProfileActivity.this,HomeActivity.class);
        intent.putExtra("id",idProfile.getText().toString());
        startActivity(intent);
        finish();
    }

    @Override
    public void inputKosong() {
        toast("Tidak boleh ada yang kosong");
        nama.setEnabled(true);
        bio.setEnabled(true);
        username.setEnabled(true);
        password.setEnabled(true);
        emailAddress.setEnabled(true);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void hideLoading() {
        buttonEditProfile.setText("EDIT PROFILE");
        buttonEditProfile.setEnabled(true);
        bgLoading.setVisibility(View.GONE);
        buttonPlus.setVisibility(View.VISIBLE);

    }
}