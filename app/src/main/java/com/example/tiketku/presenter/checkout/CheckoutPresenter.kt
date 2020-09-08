package com.example.tiketku.presenter.checkout

import com.example.pendaftaransekolahmvp.network.kotlin.ConfigNetwork
import com.example.tiketku.model.kotlin.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// todo 4(Update Uang) : contruct interface
class CheckoutPresenter(var checkoutView: CheckoutView) {
    // todo 4(Update Uang) : Membuat fungsi update profile
    fun updateProfile(idProfile:String,money:String)
    {
        // todo 5(Update Uang) : Configurasi retrofit
        ConfigNetwork.getRetrofit().updateProfile(idProfile,money).enqueue(object :Callback<ResponseUser>{
            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                checkoutView.onError(t.localizedMessage)
                checkoutView.hiddeLoading()
                checkoutView.hiddeProgress()
            }

            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                checkoutView.onSuccess(response.message())
                checkoutView.hiddeLoading()
                checkoutView.hiddeProgress()
            }

        })
    }
}