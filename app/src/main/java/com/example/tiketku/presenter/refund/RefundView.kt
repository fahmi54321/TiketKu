package com.example.tiketku.presenter.refund

// todo 3(Purchaser) : Kerangka respon presenter
interface RefundView {
    fun onSuccess(message: String)
    fun onError(message: String)
    fun hideProgress()
    fun hideLoading()
}