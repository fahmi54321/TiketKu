package com.example.tiketku.presenter.details

import android.util.Log
import com.example.pendaftaransekolahmvp.network.kotlin.ConfigNetwork
import com.example.tiketku.model.kotlin.ResponseWisata
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// todo 6(details) contruct interface
class DetailsPresenter(var detailsView: DetailsView) {
    // todo 4(details) membuat fungsi getWisata
    fun getWisata(kodeWisata:String)
    {
        // todo 5(details) konfigurasi retrofit
        ConfigNetwork.getRetrofit().getWisata(kodeWisata).enqueue(object :Callback<ResponseWisata>{
            override fun onFailure(call: Call<ResponseWisata>, t: Throwable) {
                detailsView.onError(t.localizedMessage)
                Log.d("Response Gagal Total",t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseWisata>,
                response: Response<ResponseWisata>
            ) {
                if (response.isSuccessful)
                {
                    var data = response.body()?.result
                    if (data?.size?:0>0)
                    {
                        detailsView.onSuccess(response.message(),data)
                        Log.d("Response Bhs Details",response.message())
                    }
                    else
                    {
                        detailsView.onError(response.message())
                        Log.d("Response Data Kosong",response.message())
                    }
                }
                else
                {
                    detailsView.onError(response.message())
                    Log.d("Response Gagal",response.message())
                }
            }

        })
    }
}