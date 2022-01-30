package com.jasbir.WeatherForecast.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.*
import com.jasbir.WeatherForecast.R
import com.jasbir.WeatherForecast.utils.ConnectivityLiveData
import com.jasbir.WeatherForecast.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var utils: Utils

    val viewModel: WeatherViewModel by viewModels()
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val PERMISSION_ID = 1010

    //    lateinit var rv: RecyclerView
    lateinit var errorScreen: RelativeLayout
    lateinit var retry: Button

    //    lateinit var forecastAdapter: ForecastAdapter
    lateinit var getData: Intent
    lateinit var progress: ProgressBar
    var cityName = ""
    private lateinit var connectivityLiveData: ConnectivityLiveData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        errorScreen = findViewById(R.id.errorScreen)
        progress = findViewById(R.id.progress)
        retry = findViewById(R.id.retry)
//        rv = findViewById(R.id.rv_forecast)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        connectivityLiveData= ConnectivityLiveData(application)

        connectivityLiveData.observe(this, Observer {

          when(it){
              true-> getLastLocation()
              false-> errorScreen.visibility = View.VISIBLE
          }
        })

        retry.setOnClickListener {
            errorScreen.visibility = View.GONE
            getLastLocation()
        }


    }


    fun initViewModel(area: String) {
        viewModel.city = area
        viewModel.invokeWeather()
        viewModel.getWeatherLiveData.observe(this, Observer {

            if (!it.equals("")) {
                getData = Intent(this, StartActivity::class.java)
                getData.putExtra("temp", it.main.temp.toString())
                getData.putExtra("state", it.name)
            } else {
                errorScreen.visibility = View.VISIBLE
            }


        })
        viewModel.getForeCastLiveData.observe(this, Observer {
            getData.putExtra("temp1", it.list.get(0).main.temp.toString())
            getData.putExtra("temp2", it.list.get(1).main.temp.toString())
            getData.putExtra("temp3", it.list.get(2).main.temp.toString())
            getData.putExtra("temp4", it.list.get(3).main.temp.toString())

            getData.putExtra("day1", it.list.get(0).weather.get(0).description)
            getData.putExtra("day2", it.list.get(1).weather.get(0).description)
            getData.putExtra("day3", it.list.get(2).weather.get(0).description)
            getData.putExtra("day4", it.list.get(3).weather.get(0).description)

            startActivity(getData)
            finish()


/// these code  is to show all 30 day forecast

            // forcast data
            /*temp1.text = it.list.get(0).main.temp.toString()
            temp2.text = it.list.get(1).main.temp.toString()
            temp3.text = it.list.get(2).main.temp.toString()
            temp4.text = it.list.get(3).main.temp.toString()

            day1.text = it.list.get(0).weather.get(0).description
            day2.text = it.list.get(1).weather.get(0).description
            day3.text = it.list.get(2).weather.get(0).description
            day4.text = it.list.get(3).weather.get(0).description*/


            /*  forecastAdapter = ForecastAdapter(this,it.list)

              rv.apply {
                  layoutManager = LinearLayoutManager(this@MainActivity)
                  setHasFixedSize(true)
                  adapter = forecastAdapter
              }*/


        })
    }

    fun getLastLocation() {
        if (utils.CheckPermission(this)) {
            if (utils.isLocationEnabled(this)) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        newLocationData()
                    } else {
                        cityName = utils.getCityName(location.latitude, location.longitude, this)
                        if (!cityName.isNullOrEmpty()) {
                            initViewModel(cityName)

                        } else {
                            errorScreen.visibility = View.VISIBLE
                        }

                    }
                }
            } else {
                // injecting using dagger hilt
                utils.buildAlertMessageNoGps(this)
                errorScreen.visibility = View.VISIBLE
            }
        } else {
            utils.RequestPermission(this, PERMISSION_ID)
        }


    }

    fun newLocationData() {
        var locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:", "your last last location: " + lastLocation.longitude.toString())
            cityName =
                utils.getCityName(lastLocation.latitude, lastLocation.longitude, this@MainActivity)
            if (!cityName.isNullOrEmpty()) {
                initViewModel(cityName)

            } else {
                errorScreen.visibility = View.VISIBLE
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }




}