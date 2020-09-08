package com.example.tiketku.presenter.checkout


// todo 3(Update Uang) : Kerangka respon dari presenter
interface CheckoutView {
    fun onSuccess(message:String)
    fun onError(message:String)
    fun hiddeProgress()
    fun hiddeLoading()
}