package com.example.tiketku.presenter.editProfile;

import android.util.Log;

import com.example.tiketku.model.java.ResponseUserJava;
import com.example.tiketku.network.java.ApiService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfilePresenter {
    // todo 6 (Edit Profile) construct interface
    EditProfileView editProfileView;

    public EditProfilePresenter(EditProfileView editProfileView) {
        this.editProfileView = editProfileView;
    }

    // todo 4 (Edit Profile) membuat fungsi edit profile
    public void editProfile(RequestBody idProfile, RequestBody username, RequestBody password, RequestBody emailAddress, RequestBody nama, RequestBody bio, MultipartBody.Part photo)
    {
        // todo 5 (Edit Profile) konfigurasi retrofit
        if (username!=null && password!=null && emailAddress!=null && nama!=null && bio!=null)
        {
            Call<ResponseUserJava> call = ApiService.Factory.create().updateProfile(idProfile,username, password, emailAddress, nama, bio, photo);
            call.enqueue(new Callback<ResponseUserJava>() {
                @Override
                public void onResponse(Call<ResponseUserJava> call, Response<ResponseUserJava> response) {
                    editProfileView.onSuccess(response.message());
                    editProfileView.hideLoading();
                    editProfileView.hideProgress();
                    Log.d("Response Berhasil",response.message());
                }

                @Override
                public void onFailure(Call<ResponseUserJava> call, Throwable t) {
                    editProfileView.onError(t.getLocalizedMessage());
                    editProfileView.hideLoading();
                    editProfileView.hideProgress();
                    Log.d("Response Gagal",t.getLocalizedMessage());
                }
            });
        }
        else
        {
            editProfileView.inputKosong();
            editProfileView.hideLoading();
            editProfileView.hideProgress();
        }
    }
}
