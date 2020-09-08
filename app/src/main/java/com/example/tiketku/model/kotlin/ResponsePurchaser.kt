package com.example.tiketku.model.kotlin
import com.google.gson.annotations.SerializedName

data class ResponsePurchaser(

    @field:SerializedName("result")
    val result: List<ResultItemPurchaser?>? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class ResultItemPurchaser(

    @field:SerializedName("id_profile")
    val id_profile: String? = null,

    @field:SerializedName("nama_wisata")
    val nama_wisata: String? = null,

    @field:SerializedName("lokasi_wisata")
    val lokasi_wisata: String? = null,

    @field:SerializedName("jumlah_tiket")
    val jumlah_tiket: String? = null
)
