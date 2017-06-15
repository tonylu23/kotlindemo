package com.jay.kotlinapplication.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jay.kotlinapplication.Forecast
import com.jay.kotlinapplication.ForecastList
import com.jay.kotlinapplication.OnItemClickListener
import com.jay.kotlinapplication.R
import com.jay.kotlinapplication.tools.ctx
import org.jetbrains.anko.find

/**
 * Created by rocklu on 2017/5/28.
 */
class ForecastListAdapter(val weekForecast: ForecastList, val itemClick: OnItemClickListener) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {
    override fun onBindViewHolder(p0: ViewHolder?, p1: Int) {
//        p0?.textView?.text = listString.get(p1)
//        with(weekForecast.dailyForecast[p1]) {
//            p0?.textView?.text = "$date - $description - $high/$low"
//        }
        p0?.bindForecast(weekForecast.dailyForecast[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0?.ctx).inflate(R.layout.item_forecast, p0, false)
//        return ViewHolder(TextView(p0?.context))
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return weekForecast.dailyForecast.size
    }

    class ViewHolder(val view: View, val itemClick: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        private val iconView: ImageView
        private val dateView: TextView
        private val descriptionView: TextView
        private val maxTemperatureView: TextView
        private val minTemperatureView: TextView
        init {
            iconView = view.find(R.id.icon)
            dateView = view.find(R.id.date)
            descriptionView = view.find(R.id.description)
            maxTemperatureView = view.find(R.id.maxTemperature)
            minTemperatureView = view.find(R.id.minTemperature)
        }
        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                Glide.with(itemView.ctx).load(iconUrl).into(iconView)
                dateView.text = date
                descriptionView.text = description
                maxTemperatureView.text = "${high.toString()}"
                minTemperatureView.text = "${low.toString()}"
                itemView.setOnClickListener { itemClick(forecast) }
            }
        }
    }
}
public interface OnItemClickListener {
    operator fun invoke(forecast: Forecast)
}