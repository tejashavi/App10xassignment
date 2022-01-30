package com.jasbir.WeatherForecast.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.util.*
import javax.inject.Inject

class Utils
@Inject
constructor() {
    fun buildAlertMessageNoGps(context: Context) {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialog, id ->
                    context.startActivity(
                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    )
                })
            .setNegativeButton(
                "No",
                DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(
                        context,
                        "Sorry You cannot proceed without permission",
                        Toast.LENGTH_LONG
                    ).show()
                })
        val alert: android.app.AlertDialog? = builder.create()
        alert?.show()
    }

    fun CheckPermission(context: Context): Boolean {
        //this function will return a boolean
        //true: if we have permission
        //false if not
        if (
            ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        return false

    }

    fun RequestPermission(activity: Activity,PERMISSION_ID: Int){
        //this function will allows us to tell the user to requesut the necessary permsiion if they are not garented
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    fun isLocationEnabled(context: Context):Boolean{
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        var locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

     fun getCityName(lat: Double,long: Double,context: Context):String{
        var stateName:String = ""

        var countryName = ""
        var geoCoder = Geocoder(context, Locale.getDefault())
        var getaddress  = geoCoder.getFromLocation(lat,long,1)

         stateName = getaddress.get(0).adminArea


        return stateName
    }

}