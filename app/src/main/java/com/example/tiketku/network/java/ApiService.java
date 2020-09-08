package com.example.tiketku.network.java;

import com.example.tiketku.model.java.ResponseUserJava;

import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


//todo 2.2 konfigurasi API
public interface ApiService {
    @Multipart
    @POST("tambahUserNext.php")
    Call<ResponseUserJava> postTambahUser
            (
                    @Part("id_profile") RequestBody id_profile,
                    @Part("nama") RequestBody nama,
                    @Part("bio") RequestBody bio,
                    @Part MultipartBody.Part photo
            );

    //todo 1.4 konfigurasi API
    @GET("getProfile.php")
    Call<ResponseUserJava.ResponseUserJavaData> getProfile
            (
                    @Query("id_profile") String id_profile
            );

    //todo 1 (Signup) Konfigurasi API
    @Multipart
    @POST("tambahUser.php")
    Call<ResponseUserJava> postAddUser
            (
                    @Part("id_profile") RequestBody id_profile,
                    @Part("username") RequestBody username,
                    @Part("password") RequestBody password,
                    @Part("email_address") RequestBody email_address,
                    @Part("nama") RequestBody nama,
                    @Part("bio") RequestBody bio,
                    @Part("money") RequestBody money,
                    @Part MultipartBody.Part photo
            );

    //todo 1 (Edit Profile) Konfigurasi API
    @Multipart
    @POST("ubahProfile.php")
    Call<ResponseUserJava> updateProfile
    (
            @Part("id_profile") RequestBody id_profile,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("email_address") RequestBody email_address,
            @Part("nama") RequestBody nama,
            @Part("bio") RequestBody bio,
            @Part MultipartBody.Part photo
    );

    class Factory {
        public static ApiService create() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);

            OkHttpClient client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(ApiService.class);

        }
    }
}
