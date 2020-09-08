package com.example.tiketku.presenter.home

import com.example.tiketku.model.kotlin.ResultsItemUser

interface HomeView {
    fun onSuccess(
        message:String,
        results: List<ResultsItemUser?>?
    )
    fun onError(message:String)
}