package com.example.tiketku.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.tiketku.R
import com.example.tiketku.SuccessBuyTicketActivity
import com.example.tiketku.presenter.refund.RefundPresenter
import com.example.tiketku.presenter.refund.RefundView
import kotlinx.android.synthetic.main.activity_refund_ticket.*

class RefundTicketActivity : AppCompatActivity(), RefundView {

    // todo 7(Purchaser) : deklarasi presenter
    var refundPresenter:RefundPresenter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refund_ticket)

        // todo 8(Purchaser) : inisialisasi presenter
        refundPresenter = RefundPresenter(this)

        val bundleNamaWisata = intent.extras
        namaWisata.text = bundleNamaWisata?.getString("namaWisata")

        val bundleIdUser = intent.extras
        idUser.text = bundleIdUser?.getString("idUser")

        val bundleLokasiWisata = intent.extras
        lokasiWisata.text = bundleLokasiWisata?.getString("lokasiWisata")

        val bundleAngka = intent.extras
        angka.text = bundleAngka?.getString("angka")

        val bundledate = intent.extras
        date.text = bundledate?.getString("dateWisata")

        val bundletime = intent.extras
        time.text = bundletime?.getString("timeWisata")

        val bundleinformation = intent.extras
        information.text = bundleinformation?.getString("informationWisata")

        bttnRefundNow.setOnClickListener {

            bttnRefundNow.text="LOADING..."
            bttnRefundNow.isEnabled = false
            progressBar.visibility = View.VISIBLE

            // todo 2(Purchaser) : Ambil view
            var idProfile = idUser.text.toString()
            var namaWisata = namaWisata.text.toString()
            var lokasiWisata = lokasiWisata.text.toString()
            var jmlTiket = angka.text.toString()

            // todo 10(Purchaser) : eksekusi presenter
            refundPresenter?.updatePurchaser1(idProfile, namaWisata, lokasiWisata, jmlTiket)
        }
    }

    // todo 9(Purchaser) : implementasi presenter

    override fun onSuccess(message: String) {
        toast("Berhasil")
        val intent = Intent(this,SuccessBuyTicketActivity::class.java)
        intent.putExtra("idProfile",idUser.text.toString())
        startActivity(intent)
    }

    override fun onError(message: String) {
        toast("Gagal")
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun hideLoading() {
        bttnRefundNow.text="REFUND NOW"
        bttnRefundNow.isEnabled = true
    }

    fun toast(message: String)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tidak bisa kembali", Toast.LENGTH_LONG).show()
    }
}