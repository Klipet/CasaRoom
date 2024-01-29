package com.example.casaroom.alert_dialog

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.casaroom.R

class OkDialog(context: Context): Dialog(context) {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ok_dialog)

        val checkMarkView = findViewById<CheckMarkView>(R.id.checkMark)
        checkMarkView.animateCheckMark()
    }
}

class CheckMarkView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.GREEN
        strokeWidth = 8f
        strokeCap = Paint.Cap.ROUND
    }

    private var animatedFraction = 0f

    fun animateCheckMark() {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000
            interpolator = LinearInterpolator()
            addUpdateListener { valueAnimator ->
                this@CheckMarkView.animatedFraction = valueAnimator.animatedFraction
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Рисуем зеленую галочку
        val startX = width / 4f
        val startY = height / 2f
        val midX = width / 2f
        val midY = height / 1.5f
        val endX = width / 1.2f
        val endY = height / 3f

        canvas.drawLine(startX, startY, midX, midY, paint)
        canvas.drawLine(midX, midY, endX, endY, paint)
    }
}