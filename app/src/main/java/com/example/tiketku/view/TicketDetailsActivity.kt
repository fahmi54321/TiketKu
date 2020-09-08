package com.example.tiketku.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tiketku.R
import com.example.tiketku.model.kotlin.ResultItem
import com.example.tiketku.presenter.details.DetailsPresenter
import com.example.tiketku.presenter.details.DetailsView
import kotlinx.android.synthetic.main.activity_ticket_details.*

class TicketDetailsActivity : AppCompatActivity(), DetailsView {

    var detailsPresenter:DetailsPresenter?=null
    var progressBar:ProgressBar?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_details)

        val bundleKodeWisata = intent.extras
        kodeWisata.text = bundleKodeWisata?.getString("kodeWisata")

        val bundleMoney = intent.extras
        money.text = bundleMoney?.getString("money")

        val bundleIdUser = intent.extras
        idUser.text = bundleIdUser?.getString("idUser")

        detailsPresenter = DetailsPresenter(this)

        progressBar = findViewById(R.id.progressBar)


        // todo 2(details) mengambil view

        val namaWisata = txtWisata.text.toString()
        val lokasiWisata = txtLokasiWisata.text.toString()
        val photoSpots = txtPhotoSpots.text.toString()
        val wifiAvailable = txtWifiAvailable.text.toString()
        val festivals = txtFestivals.text.toString()
        val deskripsi = txtDeskripsi.text.toString()

        // todo 8(details) eksekusi presenter
        detailsPresenter?.getWisata(kodeWisata.text.toString())

        bttnBuyTicket.setOnClickListener {
            val intent = Intent(this,
                TicketCheckoutActivity::class.java)
            intent.putExtra("moneyUser",money.text.toString())
            intent.putExtra("namaWisata",txtWisata.text.toString())
            intent.putExtra("lokasiWisata",txtLokasiWisata.text.toString())
            intent.putExtra("hargaTiket",hargaTiket.text.toString())
            intent.putExtra("userId",idUser.text.toString())
            intent.putExtra("date",date.text.toString())
            intent.putExtra("time",time.text.toString())
            intent.putExtra("information",information.text.toString())
            startActivity(intent)
        }
    }

    //todo 7(details) implementasi presenter

    override fun onSuccess(message: String, result: List<ResultItem?>?) {
        toast("Berhasil")
        progressBar?.visibility = View.GONE
        bttnBuyTicket.text="BUY TICKET"
        bttnBuyTicket.isEnabled = true
        for (i:Int in result?.indices?:0..1)
        {
            var Contants = "http://192.168.1.11/ticket/wisata/"
            txtWisata.text = result?.get(i)?.namaWisata
            txtLokasiWisata.text = result?.get(i)?.lokasiWisata
            txtPhotoSpots.text = result?.get(i)?.photoSpots
            txtWifiAvailable.text = result?.get(i)?.wifiAvailable
            txtFestivals.text = result?.get(i)?.festivals
            txtDeskripsi.text = result?.get(i)?.deskripsi
            date.text = result?.get(i)?.date
            time.text = result?.get(i)?.time
            information.text = result?.get(i)?.information
            hargaTiket.text = result?.get(i)?.hargaTiket
            Glide.with(this)
                .load(Contants+result?.get(i)?.image)
                .apply(RequestOptions().error(R.drawable.icon_nopic))
                .into(image)
        }
    }

    override fun onError(message: String) {
        toast("Gagal")
    }

    fun toast(message: String)
    {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }
}