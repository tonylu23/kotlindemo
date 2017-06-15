package com.jay.kotlinapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.jay.kotlinapplication.adapter.ForecastListAdapter
import com.jay.kotlinapplication.adapter.OnItemClickListener
import com.jay.kotlinapplication.tools.Request
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.util.*

class WeatherActivity : AppCompatActivity() {

    val url = "http://www.baidu.com"
    private val items = listOf("Mon 6/23 - Sunny - 31/17",
            "Tue 6/24 - Foggy - 21/8",
            "Wed 6/25 - Cloudy - 22/17",
            "Thurs 6/26 - Rainy - 18/11",
            "Fri 6/27 - Foggy - 21/10",
            "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
            "Sun 6/29 - Sunny - 20/7")
//    private var list: RecyclerView = null!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        initView()
        initData()
//        mapObject()
    }

    fun initView() {
//        list = findViewById(R.id.forecast_list) as RecyclerView
//        list.layoutManager = LinearLayoutManager(this)
//        list.adapter = ForecastListAdapter(items)
    }

    private var  list: RecyclerView

    init {
        list = findViewById(R.id.forecast_list) as RecyclerView
        list.layoutManager = LinearLayoutManager(this)
    }

    fun initData() {
        async() {
//            Request(url).run()
//            uiThread { longToast("Request performed") }
            val result = RequestForecastCommand("94043").execute()
            uiThread {
//                list.adapter = ForecastListAdapter(result)
                list.adapter = ForecastListAdapter(result, object: OnItemClickListener {
                    override fun invoke(forecast: Forecast) {
                        toast(forecast.date)
                    }

                })
            }
        }
    }

//    fun dealData() {
//        val data1 = Forecast(Date(), 27.5f, "123")
//        val data2 = data1.copy(temp = 30.5f)
//    }
//
//    /**
//     * 映射对象
//     */
//    fun mapObject() {
//        val data1 = Forecast(Date(), 27.6f, "12345")
//        val (dat, temp, details) = data1
//        Log.d("map_object", dat.toString() + temp + details)
//    }
}
