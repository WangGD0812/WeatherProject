package com.goat.weather.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * The base class for all activity, activity public property or function.
 * For example: presenter initialize, freeing common resource, page event track, common style,etc.
 */
abstract class BaseActivity<T: BasePresenter<K>, K: IBaseView>: AppCompatActivity() {

    @Inject lateinit var mPresenter: T

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        mPresenter?.attachView(this as K)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }

}