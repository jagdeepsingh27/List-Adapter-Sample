package com.jcoding.listadaptersample

import android.app.Application
import com.jcoding.Injection


class MainApplication() : Application() {


    override fun onCreate() {
        super.onCreate()
        //////////////////////////////////////////////////////////////////////
        //init injection
        initInjection()
        //////////////////////////////////////////////////////////////////////
    }

    private fun initInjection() {
        Injection.init(applicationContext)
    }
}