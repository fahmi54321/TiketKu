package com.example.tiketku.presenter.myprofile

import com.example.tiketku.model.kotlin.ResultItemPurchaser

// todo 2(getProfile) : kerangka respon get Profile
interface MyProfileView {
    fun onSuccess(
        message:String,
        results: List<ResultItemPurchaser?>?
    )
    fun onError(message:String)
}