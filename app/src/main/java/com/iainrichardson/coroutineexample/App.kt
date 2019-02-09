package com.iainrichardson.coroutineexample

import android.app.Application
import com.iainrichardson.coroutineexample.di.AppComponent
import com.iainrichardson.coroutineexample.di.AppModule
import com.iainrichardson.coroutineexample.di.DaggerAppComponent


class App : Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        setup()
    }

    private fun setup() {
        component = DaggerAppComponent.builder()
                .appModule(AppModule()).build()
    }

    fun getAppComponent(): AppComponent = component

}
