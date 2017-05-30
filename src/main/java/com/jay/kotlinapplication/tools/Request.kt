package com.jay.kotlinapplication.tools

import android.util.Log
import java.net.URL

/**
 * Created by rocklu on 2017/5/29.
 */
public class Request(val url : String) {
    fun run() {
        val result = URL(url).readText()
        Log.e("request", result)
    }
}