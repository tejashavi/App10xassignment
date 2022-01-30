package com.jasbir.WeatherForecast.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.jasbir.WeatherForecast.R

class StartActivity : AppCompatActivity() {

    lateinit var state: TextView
    lateinit var temp: TextView
    lateinit var day1: TextView
    lateinit var temp1: TextView
    lateinit var day2: TextView
    lateinit var temp2: TextView
    lateinit var day3: TextView
    lateinit var temp3: TextView
    lateinit var day4: TextView
    lateinit var temp4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        initvariables()
        // current data
        temp.text = intent.getStringExtra("temp").toString()
        state.text = intent.getStringExtra("state").toString()

        // forecast data

        day1.text = intent.getStringExtra("day1").toString()
        day2.text = intent.getStringExtra("day1").toString()
        day3.text = intent.getStringExtra("day1").toString()
        day4.text = intent.getStringExtra("day1").toString()

        temp1.text = intent.getStringExtra("temp1").toString()
        temp2.text = intent.getStringExtra("temp2").toString()
        temp3.text = intent.getStringExtra("temp3").toString()
        temp4.text = intent.getStringExtra("temp4").toString()



    }

    fun initvariables(){
        temp = findViewById(R.id.tempTv)
        state = findViewById(R.id.cityTv)
        day1 = findViewById(R.id.day1)
        day2 = findViewById(R.id.day2)
        day3 = findViewById(R.id.day3)
        day4 = findViewById(R.id.day4)
        temp1 = findViewById(R.id.temp1)
        temp2 = findViewById(R.id.temp2)
        temp3 = findViewById(R.id.temp3)
        temp4 = findViewById(R.id.temp4)
    }


}