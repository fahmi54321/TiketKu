package com.example.tiketku.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiketku.R
import com.example.tiketku.presenter.checkout.CheckoutPresenter
import com.example.tiketku.presenter.checkout.CheckoutView
import kotlinx.android.synthetic.main.activity_ticket_checkout.*

class TicketCheckoutActivity : AppCompatActivity(), CheckoutView {

    // todo 6(Update Uang) : deklarasi presenter
    var checkoutPresenter:CheckoutPresenter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_checkout)

        // todo 7(Update Uang) : Inisialisasi Presenter
        checkoutPresenter = CheckoutPresenter(this)

        var angka = 1;

        val bundleMoney = intent.extras
        txtMoney.text = bundleMoney?.getString("moneyUser")

        val bundleNamaWisata = intent.extras
        txtWisataNama.text = bundleNamaWisata?.getString("namaWisata")

        val bundlelokasiWisata = intent.extras
        txtWisataLokasi.text = bundlelokasiWisata?.getString("lokasiWisata")

        val bundlehargaTiket = intent.extras
        totalHargaTiket.text = bundlehargaTiket?.getString("hargaTiket")

        val bundleidUser = intent.extras
        idUser.text = bundleidUser?.getString("userId")

        val bundledate = intent.extras
        date.text = bundledate?.getString("date")

        val bundletime = intent.extras
        time.text = bundletime?.getString("time")

        val bundleinformation = intent.extras
        information.text = bundleinformation?.getString("information")

        var harga=totalHargaTiket.text.toString()
        var hargaTiket = harga.toInt()

        var uang = txtMoney.text.toString()
        var uangUser = uang.toInt()


        bttnPlus.setOnClickListener {
            angka+=1
            txtAngka.text = angka.toString()
            if (angka>=1)
            {
                bttnMinus.visibility = View.VISIBLE
                bttnPayNow.visibility = View.VISIBLE
            }
            if (angka==0)
            {
                bttnPayNow.visibility = View.GONE
            }
            var total = angka*hargaTiket
            totalHargaTiket.text = total.toString()
            if(uangUser<total)
            {
                imgPeringatan.visibility = View.VISIBLE
                txtMoney.setTextColor(Color.RED)
                bttnPayNow.visibility = View.GONE
            }
            var totalUang = uangUser-total
            txtTotalUang.text = totalUang.toString()

        }
        bttnMinus.setOnClickListener {
            angka-=1
            txtAngka.text = angka.toString()
            if (angka<1)
            {
                bttnMinus.visibility = View.GONE
                bttnPayNow.visibility = View.GONE
            }
            var total = angka*hargaTiket
            totalHargaTiket.text = total.toString()

            if (uangUser>=total)
            {
                imgPeringatan.visibility = View.GONE
                txtMoney.setTextColor(Color.GREEN)
                bttnPayNow.visibility = View.VISIBLE
            }

            if (angka==0)
            {
                bttnPayNow.visibility = View.GONE
            }

            var totalUang = uangUser-total
            txtTotalUang.text = totalUang.toString()
        }

        bttnPayNow.setOnClickListener {

            bttnPayNow.text = "LOADING..."
            bttnPayNow.isEnabled = false
            progressBar.visibility = View.VISIBLE

            // todo 2(Update Uang) : Ambil View
            var idProfile = idUser.text.toString()
            var totalUang = txtTotalUang.text.toString()

            // todo 9(Update Uang) : Eksekusi Presenter
            checkoutPresenter?.updateProfile(idProfile,totalUang)
        }
    }

    // todo 8(Update Uang) : Implementasi Presenter

    override fun onSuccess(message: String) {
        toast("Berhasil")
        val intent = Intent(this, RefundTicketActivity::class.java)
        intent.putExtra("namaWisata",txtWisataNama.text.toString())
        intent.putExtra("lokasiWisata",txtWisataLokasi.text.toString())
        intent.putExtra("dateWisata",date.text.toString())
        intent.putExtra("timeWisata",time.text.toString())
        intent.putExtra("informationWisata",information.text.toString())
        intent.putExtra("angka",txtAngka.text.toString())
        intent.putExtra("idUser",idUser.text.toString())
        startActivity(intent)
    }

    override fun onError(message: String) {
        toast("Gagal")
    }

    override fun hiddeProgress() {
        progressBar.visibility = View.GONE
    }

    override fun hiddeLoading() {
        bttnPayNow.text = "PAY NOW"
        bttnPayNow.isEnabled = true
    }

    fun toast(message: String)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}