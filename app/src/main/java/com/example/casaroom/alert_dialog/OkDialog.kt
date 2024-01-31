package com.example.casaroom.alert_dialog


import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.casaroom.R
import com.airbnb.lottie.LottieAnimationView

class OkDialog(private val context: Context) {


    val alertDialog = AlertDialog.Builder(context)
    val dialogView = LayoutInflater.from(context).inflate(R.layout.ok_dialog, null)
    val image: LottieAnimationView = dialogView.findViewById(R.id.ceckOk)
    fun viewmage(){
        alertDialog.setView(dialogView)
        alertDialog.create()
        image.loop(true)
        image.playAnimation()
    }
}