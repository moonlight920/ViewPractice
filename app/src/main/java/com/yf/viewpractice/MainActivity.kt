package com.yf.viewpractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.yfengtech.widgets.widgetsDemo
import com.yf.smarttemplate.SmartTemplate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SmartTemplate.apply(this, widgetsDemo)
    }
}