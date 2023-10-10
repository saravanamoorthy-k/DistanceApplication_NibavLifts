package com.nibavlifts.distanceapplication

import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var distanceTextView: TextView
    private lateinit var wifiManager: WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        distanceTextView = findViewById(R.id.distanceTextView)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        calculateDistance()
    }

    private fun calculateDistance() {
        Thread {
            while (true) {
                val wifiInfo: WifiInfo = wifiManager.connectionInfo
                val signalLevel = WifiManager.calculateSignalLevel(wifiInfo.rssi, 100)

                val distance = calculateDistanceFromSignalLevel(signalLevel)

                runOnUiThread {
                    distanceTextView.text = "Distance (mm): $distance"
                }

                Thread.sleep(1000)
            }
        }.start()
    }

    private fun calculateDistanceFromSignalLevel(signalLevel: Int): Double {
        return signalLevel * 10.0
    }

}