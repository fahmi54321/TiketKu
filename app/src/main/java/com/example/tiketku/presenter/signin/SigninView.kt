package com.example.tiketku.presenter.signin

import com.example.tiketku.model.kotlin.ResultsItemUser


// todo 3.3 kerangka response dari presenter
interface SigninView {
    fun onSuccess(message:String,results: List<ResultsItemUser?>?)
    fun onError(message:String)
    fun hiddeProgress()
    fun hiddeLoading()
    fun karakterKosong()
}