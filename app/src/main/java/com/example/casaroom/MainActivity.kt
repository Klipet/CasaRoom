package com.example.casaroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.casaroom.clases.StartActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val handler = Handler()
        val millis = 2000
        val runnable = Runnable {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            finish()
        }
        handler.postDelayed(runnable, millis.toLong())
    }

    fun getstate(){

    }
}