package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
        getJsonData(lat,long)
    }

    private fun getJsonData(lat: String?, long: String?) {
        val API_KEY = "e2239b966624b2a36fb6885cc34eb24c"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_KEY}"
        val JsonRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener { response ->
               Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()
            },Response.ErrorListener { Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show() }
        )
        queue.add(JsonRequest)
    }

}