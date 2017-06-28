package com.jay.kotlinapplication.sql

import android.database.sqlite.SQLiteDatabase
import com.jay.kotlinapplication.App
import com.jay.kotlinapplication.Forecast
import com.jay.kotlinapplication.ForecastList
import org.jetbrains.anko.db.*

/**
 * Created by jiayelu on 2017/6/16.
 */
class SqlClass {
    val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance
    val dataMapper: DbDataMapper = DbDataMapper()
    fun sqlTask() {
        requestForecastByZipCode()
    }
    fun requestForecastByZipCode(zipCode: Long, date: Long) = {
        forecastDbHelper.use {
            val dailyRequest = "${DayForecastTable.CITY_ID} = ? " +
                    "AND ${DayForecastTable.DATE} >= ?"
            val dailyForecast = select(DayForecastTable.NAME)
                    .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                    .parseList{DayForecast(HashMap(it))}
            val city = select(CityForecastTable.NAME)
                    .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                    .parseOpt { CityForecast(HashMap(it), dailyForecast) }
            if (city != null) dataMapper.convertToDomain(city) else null

        }
    }

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {  }


    fun convertFromDomain(forecast: ForecastList) = with(forecast) {
        val daily = dailyForecast.map { convertFromDomain(id, it) }
    }

    private fun convertDayFromDomain(cityId: Long, forecast: Forecast
    ) = with(forecast) {
                DayForecast(date, description, high, low, iconUrl, cityId)
            }

}

fun SQLiteDatabase.clear(tableName: String) {
    execSQL("delete from $tableName")
}



class DbDataMapper {
    fun convertToDomain(forecast: CityForecast) = with(forecast) {
        val daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(_id, city, country, daily)
    }
    private fun convertDayToDomain(dayForecast: DayForecast) = with(
            dayForecast) {
        Forecast(date, description, high, low, iconUrl)
    }

}


fun <T: Any> SelectQueryBuilder.parseList(
        parser: (Map<String, Any>) -> T): List<T> = parseList(object : MapRowParser<T> {
    override fun parseRow(columns: Map<String, Any?>): T = parser(columns as Map<String, Any>)
})

fun <T : Any> SelectQueryBuilder.parseOpt(parser: (Map<String, Any>) -> T) : T? =
        parseOpt(object : MapRowParser<T> {
            override fun parseRow(columns: Map<String, Any?>): T =
                parser(columns as Map<String, Any>)
            })

class ForecastDbHelper() : ManagedSQLiteOpenHelper(App.instance()!!,
        ForecastDbHelper.DB_NAME, null, ForecastDbHelper.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(CityForecastTable.NAME, true, Pair(CityForecastTable.ID, INTEGER + PRIMARY_KEY),
                Pair(CityForecastTable.CITY, TEXT),
                Pair(CityForecastTable.COUNTRY, TEXT)
                )
        db?.createTable(DayForecastTable.NAME, true,
                DayForecastTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                DayForecastTable.DATE to INTEGER,
                DayForecastTable.DESCRIPTION to TEXT,
                DayForecastTable.HIGH to INTEGER,
                DayForecastTable.LOW to INTEGER,
                DayForecastTable.ICON_URL to TEXT,
                DayForecastTable.CITY_ID to INTEGER)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val DB_NAME = "forecast.db"
        val DB_VERSION = 1
        val instance: ForecastDbHelper by lazy {
            ForecastDbHelper()
        }
    }
}

object CityForecastTable {
    val NAME = "CityForecast"
    val ID = "_id"
    val CITY = "city"
    val COUNTRY = "country"
}
object DayForecastTable {
    val NAME = "DayForecast"
    val ID = "_id"
    val DATE = "date"
    val DESCRIPTION = "description"
    val HIGH = "high"
    val LOW = "low"
    val ICON_URL = "iconUrl"
    val CITY_ID = "cityId"
}

class CityForecast(val map: MutableMap<String, Any?>, val dailyForecast: List<DayForecast>) {
    var _id: Long by map
    var city: String by map
    var country: String by map
    constructor(id: Long, city: String, country: String,
                dailyForecast: List<DayForecast>): this(HashMap(), dailyForecast) {
        this._id = id
        this.city = city
        this.country = country
    }
}

class DayForecast(var map: MutableMap<String, Any?>) {
    var _id: Long by map
    var date: Long by map
    var description: String by map
    var high: Int by map
    var low: Int by map
    var iconUrl: String by map
    var cityId: Long by map
    constructor(date: Long, description: String, high: Int, low:
    Int,
                iconUrl: String, cityId: Long)
            : this(HashMap()) {
        this.date = date
        this.description = description
        this.high = high
        this.low = low
        this.iconUrl = iconUrl
        this.cityId = cityId
    }
}
