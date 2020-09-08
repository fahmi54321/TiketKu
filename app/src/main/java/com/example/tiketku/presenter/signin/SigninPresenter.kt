package com.example.tiketku.presenter.signin

import android.util.Log
import com.example.pendaftaransekolahmvp.network.kotlin.ConfigNetwork
import com.example.tiketku.model.kotlin.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// todo 6.3 inisialisasi interface
class SigninPresenter(var signinView: SigninView) {

    // todo 4.3 membuat fungsi signin
    fun signin(emailAddress:String,password:String)
    {
        if (emailAddress.isNotEmpty() && password.isNotEmpty())
        {
            // todo 5.3 membuat retrofit
            ConfigNetwork.getRetrofit().signin(emailAddress,password).enqueue(object : Callback<ResponseUser>{
                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    //todo 7.3 response jika gagal
                    signinView.onError(t.localizedMessage)
                    signinView.hiddeLoading()
                    signinView.hiddeProgress()
                }

                override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                    //todo 7.3 response jika berhasil

                    val data =response.body()?.results
                    if (data?.size?:0>0)
                    {
                        Log.d("Response Berhasil",response.message())
                        signinView.onSuccess(response.message(),data)
                        signinView.hiddeLoading()
                        signinView.hiddeProgress()
                    }
                    else
                    {
                        Log.d("Response Gagal",response.message())
                        signinView.onError(response.message())
                        signinView.hiddeLoading()
                        signinView.hiddeProgress()
                    }

                }

            })
        }
        else
        {
            signinView.karakterKosong()
            signinView.hiddeLoading()
            signinView.hiddeProgress()
        }
    }
}