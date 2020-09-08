package com.example.tiketku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.tiketku.view.SignInActivity
import com.example.tiketku.view.SignupActivity
import kotlinx.android.synthetic.main.activity_get_started.*

class GetStartedActivity : AppCompatActivity() {

    lateinit var top_to_bottom : Animation
    lateinit var bottom_to_top : Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        top_to_bottom = AnimationUtils.loadAnimation(this,R.anim.top_to_bottom)
        bottom_to_top = AnimationUtils.loadAnimation(this,R.anim.bottom_to_top)

        logo.startAnimation(top_to_bottom)
        txt_get_started.startAnimation(top_to_bottom)
        bttnSignin.startAnimation(bottom_to_top)
        bttnSignup.startAnimation(bottom_to_top)

        bttnSignup.setOnClickListener {
            val intent = Intent(this,
                SignupActivity::class.java)
            startActivity(intent)
        }

        bttnSignin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}