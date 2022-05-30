package com.goat.weather.di

import com.goat.weather.tasks.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun mainActivityInjector(): MainActivity

}