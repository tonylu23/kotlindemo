package com.jay.kotlinapplication.tools

import com.google.gson.Gson
import com.jay.kotlinapplication.bean.ForecastResult
import java.net.URL

/**
 * Created by rocklu on 2017/5/30.
 */
class ForecastRequest(val zipCode: String) {
    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val APP_URL = "http://api.openweathermap.org/data/2.5/" +
                "forecast/daily?mode=json&units=metric&cnt=7"
        private val COMPLETE_URL = "$APP_URL&APPID=$APP_ID&q="
    }

    fun execute() : ForecastResult {
        val result = URL(COMPLETE_URL + zipCode).readText()
        return Gson().fromJson(result, ForecastResult::class.java)
    }
}