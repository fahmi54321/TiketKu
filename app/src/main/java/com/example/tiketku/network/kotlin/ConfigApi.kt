package com.example.pendaftaransekolahmvp.network.kotlin

import com.example.tiketku.model.kotlin.ResponsePurchaser
import com.example.tiketku.model.kotlin.ResponseUser
import com.example.tiketku.model.kotlin.ResponseWisata
import retrofit2.Call
import retrofit2.http.*


//todo 2.1 Config API
interface ConfigApi {
    @FormUrlEncoded
    @POST("tambahUser.php")
    fun user(
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("email_address") email_address:String
    ): Call<ResponseUser>


    //todo 1.3 Config API Signin
    @FormUrlEncoded
    @POST("signin.php")
    fun signin(
        @Field("email_address") email_address:String,
        @Field("password") password:String
    ): Call<ResponseUser>

    @GET("getProfile.php")
    fun getProfile(
        @Query("id_profile") id_profile:String
    ): Call<ResponseUser>

    @GET("getProfile1.php")
    fun getProfile1(
        @Query("id_profile") id_profile:String,
        @Query("nama_wisata") nama_wisata:String
    ): Call<ResponseUser>

    @GET("getPurchaser.php")
    fun getPurchaser(
        @Query("id_profile") id_profile:String
    ): Call<ResponsePurchaser>


    // todo 1(Details) : API getWisata
    @GET("getWisata.php")
    fun getWisata(
        @Query("kode_wisata") kode_wisata:String
    ): Call<ResponseWisata>

    // todo 1(Update Uang) : API updateProfile
    @FormUrlEncoded
    @POST("updateProfile.php")
    fun updateProfile(
        @Field("id_profile") id_profile:String,
        @Field("money") money:String
    ): Call<ResponseUser>

    // todo 1(updatePurchaser) : API updatePurchaser
    @FormUrlEncoded
    @POST("updatePurchaser.php")
    fun updatePurchaser(
        @Field("id_profile") id_profile:String,
        @Field("nama_wisata") nama_wisata:String,
        @Field("lokasi_wisata") lokasi_wisata:String,
        @Field("jumlah_tiket") jumlah_tiket:String
    ): Call<ResponseUser>

    @FormUrlEncoded
    @POST("updatePurchaser1.php")
    fun updatePurchaser1(
        @Field("id_profile") id_profile:String,
        @Field("nama_wisata") nama_wisata:String,
        @Field("lokasi_wisata") lokasi_wisata:String,
        @Field("jumlah_tiket") jumlah_tiket:String
    ): Call<ResponseUser>


    // todo 1(Purchaser) : API tambahPurchaser
    @FormUrlEncoded
    @POST("tambahPurchaser.php")
    fun tambahPurchaser(
        @Field("id_profile") id_profile:String,
        @Field("nama_wisata") nama_wisata:String,
        @Field("lokasi_wisata") lokasi_wisata:String,
        @Field("jumlah_tiket") jumlah_tiket:String
    ):Call<ResponsePurchaser>
}