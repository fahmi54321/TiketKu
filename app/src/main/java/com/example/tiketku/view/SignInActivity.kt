package com.example.tiketku.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiketku.R
import com.example.tiketku.model.kotlin.ResultsItemUser
import com.example.tiketku.presenter.signin.SigninPresenter
import com.example.tiketku.presenter.signin.SigninView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), SigninView {

    var progressBar:ProgressBar?=null
    var view:View?=null
    // todo 8.3 deklarasi presenter
    var signinPresenter:SigninPresenter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        //todo 9.3 inisialisasi presenter
        signinPresenter = SigninPresenter(this)

        txtNewAccount.setOnClickListener {
            val intent = Intent(this,
                SignupActivity::class.java)
            startActivity(intent)
        }

        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        view = findViewById(R.id.bgLoading)

        bttnSignin.setOnClickListener {

            edtEmailAddress.isEnabled = false
            edtPassword.isEnabled = false
            bttnSignin.text = "LOADING..."
            progressBar?.visibility = View.VISIBLE
            view?.visibility = View.VISIBLE


            //todo 2.3 mengambil view
            val emailAddress = edtEmailAddress.text.toString()
            val password = edtPassword.text.toString()

            //todo 11.3 eksekusi presenter
            signinPresenter?.signin(emailAddress, password)
        }
    }


    //todo 10.3 implementasi presenter

    override fun onSuccess(message: String,results: List<ResultsItemUser?>?) {
        toast("Berhasil")
        val intent = Intent(this, HomeActivity::class.java)
        for (i:Int in results?.indices?:0..1)
        {
            var Contants = "http://192.168.1.11/ticket/foto/"
            intent.putExtra("id", results?.get(i)?.id_profile)
        }
        startActivity(intent)
        finish()
    }

    override fun onError(message: String) {
        toast("Gagal")
    }

    override fun hiddeProgress() {
        progressBar?.visibility = View.GONE
    }

    override fun hiddeLoading() {
        edtEmailAddress.isEnabled = true
        edtPassword.isEnabled = true
        bttnSignin.text = "SIGN IN"
        view?.visibility = View.GONE
    }

    override fun karakterKosong() {
        toast("Tidak boleh ada yang kosong")
    }

    fun toast(message: String)
    {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }
}