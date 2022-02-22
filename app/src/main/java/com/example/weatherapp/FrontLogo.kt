package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class FrontLogo : AppCompatActivity() {
    lateinit var mfusedlocation:FusedLocationProviderClient
    private var mycode=1421
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_front_logo)
        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)
        getlastlocation()
    }

    @SuppressLint("MissingPermission")
    private fun getlastlocation() {
        if(grantedPermission()){
            if(gpsEnabled()){
                mfusedlocation.lastLocation.addOnCompleteListener{
                    task->
                    var location: Location ?= task.result
                    if(location == null){
                        Toast.makeText(this,"location is null",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Log.d("location","location found")
                        Toast.makeText(this,"location is found",Toast.LENGTH_LONG).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            var intent = Intent(this,MainActivity::class.java)
                            intent.putExtra("lat",location.latitude.toString())
                            intent.putExtra("long",location.longitude.toString())
                            startActivity(intent)
                            finish()
                        },2000)
                    }
                }
            }
            else{
                Toast.makeText(this,"Please enable your GPS",Toast.LENGTH_LONG).show()
            }
        }
        else {
            requestPermission()
        }
    }

    private fun gpsEnabled(): Boolean {
        var locationmanager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),mycode)

    }

    private fun grantedPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == mycode){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getlastlocation()
            }
        }
    }
}