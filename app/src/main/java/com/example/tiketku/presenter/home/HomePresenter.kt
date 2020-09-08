package com.example.tiketku.presenter.home

import android.util.Log
import com.example.pendaftaransekolahmvp.network.kotlin.ConfigNetwork
import com.example.tiketku.model.kotlin.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(var homeView: HomeView) {

    fun getProfile(idProfile:String)
    {
        ConfigNetwork.getRetrofit().getProfile(idProfile).enqueue(object : Callback<ResponseUser>{
            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Log.d("Response Gagal total",t.localizedMessage)
                homeView.onError(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseUser>,
                response: Response<ResponseUser>
            ) {
                if (response.isSuccessful)
                {
                    val data =response.body()?.results
                    if (data?.size?:0>0)
                    {
                        Log.d("Response Berhasil",response.message())
                        homeView.onSuccess(response.message(),data)
                    }
                    else
                    {
                        Log.d("Response Gagal",response.message())
                        homeView.onError(response.message())
                    }
                }
            }

        })
    }
}