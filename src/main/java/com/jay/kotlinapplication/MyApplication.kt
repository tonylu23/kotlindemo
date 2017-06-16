package com.jay.kotlinapplication

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.properties.Delegates

/**
 * Created by jiayelu on 2017/6/16.
 */
class App: Application() {
    companion object {
        private var instance: Application? = null
        fun instance() = instance
    }

    val database: SQLiteOpenHelper by lazy {

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}

class ViewModel(val db: Mydatabase) {
    var myProperty by Delegates.observable("") {
        d, old, new ->
        db.save
    }
}