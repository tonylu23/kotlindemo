package com.jay.kotlinapplication

import com.jay.kotlinapplication.bean.ForecastResult

/**
 * Created by rocklu on 2017/5/30.
 */
public interface Command<T> {
    fun execute(): T
}

data class ForecastList(val city: String, val country: String,
                        val dailyForecast:List<Forecast>)

data class Forecast(val date: String, val description: String, val high: Int,
                    val low: Int)

public class ForecastDataMapper {
    fun convertFromDataModel(forecast: ForecastResult): ForecastList {
        return ForecastList(forecast.city.name, forecast.city.country,
                convertForecastListToDomain(forecast.list))
    }
}