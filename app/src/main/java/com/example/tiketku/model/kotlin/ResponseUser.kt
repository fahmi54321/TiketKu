package com.example.tiketku.model.kotlin

import com.google.gson.annotations.SerializedName


//todo 3.1 Membuat model
data class ResponseUser(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("result")
    val results: List<ResultsItemUser?>? = null
)

data class ResultsItemUser(

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("email_address")
    val email_address: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("bio")
    val bio: String? = null,

    @field:SerializedName("money")
    val money: String? = null,

    @field:SerializedName("photo")
    val photo: String? = null,

    @field:SerializedName("nama_wisata")
    val nama_wisata: String? = null,

    @field:SerializedName("lokasi_wisata")
    val lokasi_wisata: String? = null,

    @field:SerializedName("jumlah_tiket")
    val jumlah_tiket: String? = null,

    @field:SerializedName("id_profile")
    val id_profile: String? = null
)