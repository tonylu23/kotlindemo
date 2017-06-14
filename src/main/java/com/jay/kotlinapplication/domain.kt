package com.jay.kotlinapplication

import com.jay.kotlinapplication.bean.Forecast
import com.jay.kotlinapplication.bean.ForecastResult
import com.jay.kotlinapplication.tools.ForecastRequest
import java.text.DateFormat
import java.util.*
import com.jay.kotlinapplication.Forecast as ModelForecast

/**
 * Created by rocklu on 2017/5/30.
 */
public interface Command<T> {
    fun execute(): T
}

data class ForecastList(val city: String, val country: String,
                        val dailyForecast:List<ModelForecast>)

data class Forecast(val date: String, val description: String, val high: Int,
                    val low: Int)

public class ForecastDataMapper {
    fun convertFromDataModel(forecast: ForecastResult): ForecastList {
        return ForecastList(forecast.city.name, forecast.city.country,
                convertForecastListToDomain(forecast.list))
    }
    private fun convertForecastListToDomain(list : List<Forecast>): List<ModelForecast> {
        return list.map { convertForecastItemToDomain(it) }
    }
    private fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
        return ModelForecast(convertDate(forecast.dt),
                forecast.weather[0].description,  forecast.temp.max.toInt(),
                forecast.temp.min.toInt())
    }
    private fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date * 1000)
    }
}

class RequestForecastCommand(val zipCode: String): Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }

}