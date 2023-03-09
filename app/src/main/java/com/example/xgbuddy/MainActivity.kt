package com.example.xgbuddy

import android.animation.ValueAnimator
import android.content.pm.PackageManager
import android.graphics.drawable.AnimatedVectorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_MIDI)) {
            setContentView(R.layout.activity_main)
        } else {
            displayNoMidiCompatScreen()
        }
    }

    private fun displayNoMidiCompatScreen() {
        setContentView(R.layout.activity_main_no_midi)
        findViewById<ImageView?>(R.id.leftHand).apply { (drawable as AnimatedVectorDrawable).start() }
        findViewById<ImageView?>(R.id.rightHand).apply { (drawable as AnimatedVectorDrawable).start() }
        val head = findViewById<ImageView>(R.id.head)
        ValueAnimator.ofFloat(-15f, 15f).apply {
            duration = 750
            addUpdateListener {
                head.rotationY = it.animatedValue as Float
            }
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }
    }
}