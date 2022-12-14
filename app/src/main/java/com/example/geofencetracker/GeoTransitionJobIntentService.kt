package com.example.geofencetracker

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeoTransitionJobIntentService :JobIntentService(){

    companion object {
        private const val LOG_TAG = "GeoTrIntentService"

        private const val JOB_ID = 573

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(
                context,
                GeoTransitionJobIntentService::class.java, JOB_ID,
                intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent != null) {
            if (geofencingEvent.hasError()) {
                val errorMessage = GeofenceErrorMessages.getErrorString(this,
                    geofencingEvent.errorCode)
                Log.e(LOG_TAG, errorMessage)
                return
            }
        }
        if (geofencingEvent != null) {
            handleEvent(geofencingEvent)
        }
    }
    private fun handleEvent(event: GeofencingEvent) {
        if (event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            val reminder = event.triggeringGeofences?.let { getFirstReminder(it) }
            val message = reminder?.message
            val latLng = reminder?.latLng
            if (message != null && latLng != null) {
                // 3
                sendNotification(this, message, latLng)
            }
        }
    }

    private fun getFirstReminder(triggeringGeofences: List<Geofence>): Reminder? {
        val firstGeofence = triggeringGeofences[0]
        return (application as ReminderApp).getRepository().get(firstGeofence.requestId)
    }


}