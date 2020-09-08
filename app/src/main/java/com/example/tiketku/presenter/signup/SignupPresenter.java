package com.example.tiketku.presenter.signup;

import android.util.Log;

import com.example.tiketku.model.java.ResponseUserJava;
import com.example.tiketku.network.java.ApiService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupPresenter {

    // todo 6(Signup) Konstruct interface
    SignupView signupView;
    public SignupPresenter(SignupView signupView) {
        this.signupView = signupView;
    }

    //todo 4 (Signup) membuat fungsi signup
    public void signup(RequestBody idProfile, RequestBody username, RequestBody password, RequestBody emailAddress, RequestBody nama, RequestBody bio,RequestBody money, MultipartBody.Part photo)
    {
        //todo 5 (Signup) seleksi jika data kosong
        if (username!= null && password!=null && emailAddress!=null && nama!=null && bio!=null)
        {
            //todo 6 (Signup) Retrofit
            Call<ResponseUserJava> call = ApiService.Factory.create().postAddUser(idProfile, username, password, emailAddress, nama, bio,money, photo);
            call.enqueue(new Callback<ResponseUserJava>() {
                @Override
                public void onResponse(Call<ResponseUserJava> call, Response<ResponseUserJava> response) {

                    if (response.body().getMessage().equals("Email address sudah ada"))
                    {
                        signupView.emailAda();
                        signupView.hideProgress();
                        signupView.hideLoading();
                        Log.d("Response gagal",response.message());
                    }
                    else
                    {
                        signupView.onSuccess(response.message());
                        signupView.hideProgress();
                        signupView.hideLoading();
                        Log.d("Response Berhasil",response.message());
                    }
                }

                @Override
                public void onFailure(Call<ResponseUserJava> call, Throwable t) {
                    signupView.onError(t.getLocalizedMessage());
                    signupView.hideProgress();
                    signupView.hideLoading();
                    Log.d("Response Gagal",t.getLocalizedMessage());
                }
            });
        }
        else
        {
            signupView.inputKosong();
            signupView.hideLoading();
            signupView.hideProgress();
        }
    }
}
