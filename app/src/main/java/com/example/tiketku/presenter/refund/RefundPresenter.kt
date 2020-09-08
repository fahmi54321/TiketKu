package com.example.tiketku.presenter.refund

import com.example.pendaftaransekolahmvp.network.kotlin.ConfigNetwork
import com.example.tiketku.model.kotlin.ResponsePurchaser
import com.example.tiketku.model.kotlin.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// todo 6(Purchaser) : construct interface
class RefundPresenter(var refundView: RefundView) {
    // todo 4(Purchaser) : membuat fugsi tambahPurchaser
    fun updatePurchaser1(idProfile:String,namaWisata:String,lokasiWisata:String,jmlTiket:String)
    {
        // todo 5(Purchaser) : konfigurasi retrofit
        ConfigNetwork.getRetrofit().tambahPurchaser(idProfile,namaWisata,lokasiWisata,jmlTiket).enqueue(object : Callback<ResponsePurchaser>{
            override fun onFailure(call: Call<ResponsePurchaser>, t: Throwable) {
                refundView.onError(t.localizedMessage)
                refundView.hideLoading()
                refundView.hideProgress()
            }

            override fun onResponse(
                call: Call<ResponsePurchaser>,
                response: Response<ResponsePurchaser>
            ) {
                refundView.onSuccess(response.message())
                refundView.hideLoading()
                refundView.hideProgress()
            }

        })
    }
}