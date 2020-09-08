package com.example.tiketku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.tiketku.view.HomeActivity
import kotlinx.android.synthetic.main.activity_refund_ticket.*
import kotlinx.android.synthetic.main.activity_success_buy_ticket.*
import kotlinx.android.synthetic.main.activity_success_signup.*

class SuccessBuyTicketActivity : AppCompatActivity() {
    lateinit var top_to_bottom : Animation
    lateinit var bottom_to_top : Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_buy_ticket)

        top_to_bottom = AnimationUtils.loadAnimation(this,R.anim.top_to_bottom)
        bottom_to_top = AnimationUtils.loadAnimation(this,R.anim.bottom_to_top)

        icon_success_buy_ticket.startAnimation(top_to_bottom)
        txtSelamatMenikmati.startAnimation(top_to_bottom)
        txtAndaBerhasil.startAnimation(top_to_bottom)
        bttnMyDashboard.startAnimation(bottom_to_top)

        val bundleIdProfile = intent.extras
        txtIdProfile.text = bundleIdProfile?.getString("idProfile")

        bttnMyDashboard.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            intent.putExtra("id",txtIdProfile.text.toString())
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tidak bisa kembali", Toast.LENGTH_LONG).show()
    }
}