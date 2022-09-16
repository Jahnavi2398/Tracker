package com.example.geofencetracker

import android.app.Application

class ReminderApp : Application() {

    private lateinit var repository : ReminderRepo

    override fun onCreate() {
        super.onCreate()
        repository = ReminderRepo(this)
    }

    fun getRepository() = repository
}