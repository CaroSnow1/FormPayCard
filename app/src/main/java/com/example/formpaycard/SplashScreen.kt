package com.example.formpaycard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.concurrent.thread

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //Para que espere unos segundos
        thread{
            Thread.sleep(3000)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}