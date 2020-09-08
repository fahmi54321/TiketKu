package com.example.tiketku.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tiketku.R
import com.example.tiketku.model.kotlin.ResultsItemUser
import com.example.tiketku.presenter.home.HomePresenter
import com.example.tiketku.presenter.home.HomeView
import kotlinx.android.synthetic.main.activity_home.*
import java.text.NumberFormat
import java.util.*

class HomeActivity : AppCompatActivity(), HomeView {

    var homePresenter:HomePresenter?=null
    var locale:Locale?=null
    var numberFormat:NumberFormat?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        locale = Locale("in","ID")
        numberFormat = NumberFormat.getCurrencyInstance(locale)

        val bundle = intent.extras
        txtId.text = bundle?.getString("id")

        var text = txtId.text.toString()

        homePresenter = HomePresenter(this)

        homePresenter?.getProfile(text)

        lnMonas.setOnClickListener {
            val intent = Intent(this,TicketDetailsActivity::class.java)
            intent.putExtra("kodeWisata","mns")
            intent.putExtra("money",txtUang.text.toString())
            intent.putExtra("idUser",text)
            startActivity(intent)
        }

        lnSphinx.setOnClickListener {
            val intent = Intent(this,TicketDetailsActivity::class.java)
            intent.putExtra("kodeWisata","spx")
            intent.putExtra("money",txtUang.text.toString())
            intent.putExtra("idUser",text)
            startActivity(intent)
        }

        lnTorri.setOnClickListener {
            val intent = Intent(this,TicketDetailsActivity::class.java)
            intent.putExtra("kodeWisata","tri")
            intent.putExtra("money",txtUang.text.toString())
            intent.putExtra("idUser",text)
            startActivity(intent)
        }

        lnBorobudur.setOnClickListener {
            val intent = Intent(this,TicketDetailsActivity::class.java)
            intent.putExtra("kodeWisata","brd")
            intent.putExtra("money",txtUang.text.toString())
            intent.putExtra("idUser",text)
            startActivity(intent)
        }

        lnPagoda.setOnClickListener {
            val intent = Intent(this,TicketDetailsActivity::class.java)
            intent.putExtra("kodeWisata","pgd")
            intent.putExtra("money",txtUang.text.toString())
            intent.putExtra("idUser",text)
            startActivity(intent)
        }

        lnPisa.setOnClickListener {
            val intent = Intent(this,TicketDetailsActivity::class.java)
            intent.putExtra("kodeWisata","psa")
            intent.putExtra("money",txtUang.text.toString())
            intent.putExtra("idUser",text)
            startActivity(intent)
        }

        imgProfile.setOnClickListener {
            val intent = Intent(this,MyProfileActivity::class.java)
            intent.putExtra("idUser",text)
            intent.putExtra("nama",txtNama.text.toString())
            intent.putExtra("bio",txBio.text.toString())
            intent.putExtra("username",txtUsername.text.toString())
            intent.putExtra("emailAddress",txtEmailAddress.text.toString())
            startActivity(intent)
        }
    }

    override fun onSuccess(message: String, results: List<ResultsItemUser?>?) {
        Toast.makeText(this,"Berhasil",Toast.LENGTH_LONG).show()
        progressBar.visibility = View.GONE
        bgLoading.visibility = View.GONE
        for (i:Int in results?.indices?:0..1)
        {
            var Contants = "http://192.168.1.11/ticket/foto/"
            txtNama.text = results?.get(i)?.nama
            txBio.text = results?.get(i)?.bio
            txtUang.text = results?.get(i)?.money
            txtUsername.text = results?.get(i)?.username
            txtEmailAddress.text = results?.get(i)?.email_address
            Glide.with(this)
                .load(Contants+results?.get(i)?.photo)
                .apply(RequestOptions().error(R.drawable.icon_nopic))
                .into(imgProfile)
        }
    }

    override fun onError(message: String) {
        Toast.makeText(this,"Gagal",Toast.LENGTH_LONG).show()
    }
}