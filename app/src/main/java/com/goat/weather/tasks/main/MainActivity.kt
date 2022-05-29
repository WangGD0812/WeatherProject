package com.goat.weather.tasks.main

import android.os.Bundle
import android.widget.Toast
import com.goat.weather.R
import com.goat.weather.base.BaseActivity
import com.goat.weather.model.DayDataModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: BaseActivity(), MainContract.View {

    override val layoutId: Int
        get() = R.layout.activity_main

    var presenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MainPresenter()
        presenter?.attachView(this)
        presenter?.requestWeatherData()
    }

    override fun loadCurrentDayData(data: DayDataModel?) {
        tvSummary.text = data?.summary
    }

    override fun loadDataFailed(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showLocationDisallow(resId: Int) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
    }

}