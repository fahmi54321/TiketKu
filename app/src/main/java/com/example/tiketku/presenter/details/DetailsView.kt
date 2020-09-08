package com.example.tiketku.presenter.details

import com.example.tiketku.model.kotlin.ResultItem

//todo 3(details) kerangka respon details ticket

interface DetailsView {
    fun onSuccess(
        message:String,
        result: List<ResultItem?>?
    )
    fun onError(message:String)
}