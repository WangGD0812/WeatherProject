package com.goat.weather.di

import android.content.Context
import com.goat.weather.WeatherApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule


@Component(modules = [AndroidInjectionModule::class, ActivityModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): ApplicationComponent
    }

    fun inject(application: WeatherApplication)
}