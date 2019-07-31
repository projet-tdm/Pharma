package com.example.pharma

import android.app.Application
import com.example.pharma.RoomDataBase.RoomService

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        RoomService.context = applicationContext }
}