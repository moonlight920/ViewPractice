package com.yf.douyincopy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class FullscreenVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen_video)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,
            DouyinCopyFragment()
        ).commit()

    }
}

val DEBUG = true
internal inline fun debugLog(value: String) {
    if (DEBUG) {
        Log.d("douyin_copy", value)
    }
}