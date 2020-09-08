package com.example.tiketku.presenter.myprofile

import android.util.Log
import com.example.pendaftaransekolahmvp.network.kotlin.ConfigNetwork
import com.example.tiketku.model.kotlin.ResponsePurchaser
import com.example.tiketku.model.kotlin.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// todo 4(getProfile) : construct interface
class MyProfilePresenter(var myProfileView: MyProfileView) {

    // todo 3(getProfile) : buat fungsi get Profile
    fun getPurchaser(idProfile:String)
    {
        // todo 4(getProfile) : konfigurasi retrofit
        ConfigNetwork.getRetrofit().getPurchaser(idProfile).enqueue(object : Callback<ResponsePurchaser>{
            override fun onFailure(call: Call<ResponsePurchaser>, t: Throwable) {
                myProfileView.onError(t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponsePurchaser>, response: Response<ResponsePurchaser>) {
                if (response.isSuccessful)
                {
                    val data =response.body()?.result
                    if (data?.size?:0>0)
                    {
                        Log.d("Response Berhasil",response.message())
                        myProfileView.onSuccess(response.message(),data)
                    }
                    else
                    {
                        Log.d("Response Gagal",response.message())
                        myProfileView.onError(response.message())
                    }
                }
            }

        })
    }
}