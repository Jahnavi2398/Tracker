package com.example.geofencetracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class GeoBroadcastRecv : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        GeoTransitionJobIntentService.enqueueWork(context, intent)
    }
}