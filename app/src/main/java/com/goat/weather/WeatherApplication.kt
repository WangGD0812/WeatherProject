package com.goat.weather

import android.app.Application
import com.goat.weather.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class WeatherApplication: Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInject: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = androidInject

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder().context(this).build().inject(this)
    }
}