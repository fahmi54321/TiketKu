package com.example.tiketku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splashscreen.*

class SplashSecreenActivity : AppCompatActivity() {

    lateinit var top_to_bottom : Animation
    lateinit var bottom_to_top : Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        top_to_bottom = AnimationUtils.loadAnimation(this,R.anim.top_to_bottom)
        bottom_to_top = AnimationUtils.loadAnimation(this,R.anim.bottom_to_top)

        logo.startAnimation(top_to_bottom)
        txtSplash.startAnimation(bottom_to_top)

        val thread = Thread(Runnable {
            try {
                Thread.sleep(3000)
            } catch (ex: InterruptedException) {
                ex.printStackTrace()
            } finally {
                startActivity(Intent(applicationContext, GetStartedActivity::class.java))
                finish()
            }
        })
        thread.start()
    }
}