package com.example.tiketku

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiketku.view.HomeActivity
import kotlinx.android.synthetic.main.activity_success_signup.*

class SuccessSignupActivity : AppCompatActivity() {

    lateinit var top_to_bottom : Animation
    lateinit var bottom_to_top : Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_signup)

        top_to_bottom = AnimationUtils.loadAnimation(this,R.anim.top_to_bottom)
        bottom_to_top = AnimationUtils.loadAnimation(this,R.anim.bottom_to_top)

        icon_success_signup.startAnimation(top_to_bottom)
        txtKamuHebat.startAnimation(bottom_to_top)
        txtSelamatDatang.startAnimation(bottom_to_top)
        bttnExplore.startAnimation(bottom_to_top)

        bttnExplore.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tidak bisa kembali", Toast.LENGTH_LONG).show()
    }
}