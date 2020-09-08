package com.example.tiketku.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tiketku.R
import com.example.tiketku.adapter.GetProfileAdapter
import com.example.tiketku.model.kotlin.ResultItemPurchaser
import com.example.tiketku.model.kotlin.ResultsItemUser
import com.example.tiketku.presenter.myprofile.MyProfilePresenter
import com.example.tiketku.presenter.myprofile.MyProfileView
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_my_profile.imgProfile
import kotlinx.android.synthetic.main.activity_my_profile.txBio
import kotlinx.android.synthetic.main.activity_my_profile.txtNama

class MyProfileActivity : AppCompatActivity(), MyProfileView {

    // todo 5(getProfile) : deklarasi presenter
    var myProfilePresenter:MyProfilePresenter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        // todo 6(getProfile) : inisialisasi presenter
        myProfilePresenter = MyProfilePresenter(this)

        val bundleIdUser = intent.extras
        idUserProfile.text = bundleIdUser?.getString("idUser")

        val bundleIdNama = intent.extras
        txtNama.text = bundleIdNama?.getString("nama")

        val bundleBio = intent.extras
        txBio.text = bundleBio?.getString("bio")

        val bundleUsername = intent.extras
        username.text = bundleUsername?.getString("username")

        val bundlePassword = intent.extras
        password.text = bundlePassword?.getString("password")

        val bundleEmail = intent.extras
        txtEmailAddress.text = bundleEmail?.getString("emailAddress")

        // todo 1(getProfile) : ambil view
        var idUser = idUserProfile.text.toString()

        // todo 8(getProfile) : eksekusi presenter
        myProfilePresenter?.getPurchaser(idUser)

        bttnEditProfile.setOnClickListener {
            val intent = Intent(this,EditProfileActivity::class.java)
            intent.putExtra("namaProfile",txtNama.text.toString())
            intent.putExtra("bioProfile",txBio.text.toString())
            intent.putExtra("usernameProfile",username.text.toString())
            intent.putExtra("passwordProfile",password.text.toString())
            intent.putExtra("emailAddressProfile",txtEmailAddress.text.toString())
            intent.putExtra("idProfile",idUser)
            startActivity(intent)
        }

    }

    // todo 7(getProfile) : implementasi presenter
    override fun onSuccess(message: String, results: List<ResultItemPurchaser?>?) {
        Toast.makeText(this,"Berhasil", Toast.LENGTH_LONG).show()
        rv_profile.adapter = GetProfileAdapter(results)
    }

    override fun onError(message: String) {
        Toast.makeText(this,"Gagal", Toast.LENGTH_LONG).show()
    }
}