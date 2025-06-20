package com.example.productapp

import android.app.Application
import android.util.Log

// app handle to clear json file when it start app
class MyApp : Application()  {
    override fun onCreate() {
        super.onCreate()

//        val sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
//        sharedPref.edit().clear().apply()
        Log.d("RUPPITE","Start data from json file")
    }
}
