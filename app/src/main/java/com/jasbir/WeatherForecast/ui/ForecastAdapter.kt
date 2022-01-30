package com.jasbir.WeatherForecast.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jasbir.WeatherForecast.R
import com.jasbir.WeatherForecast.data.dataclass.forcast.ListData

class ForecastAdapter(
    var activity: Activity, var forecastList: List<ListData>
) :
    RecyclerView.Adapter<ForecastAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(activity)
            .inflate(R.layout.forcastitem, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.temp.text = forecastList.get(position).main.temp.toString()
        holder.day.text = forecastList.get(position).weather.get(0).description


    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var day: TextView
        var temp: TextView

        init {
            day = itemView.findViewById(R.id.day)
            temp = itemView.findViewById(R.id.temp)

        }


    }

    override fun getItemCount(): Int {

        return forecastList.size
    }


}