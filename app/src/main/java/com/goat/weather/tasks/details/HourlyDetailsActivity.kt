package com.goat.weather.tasks.details

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.goat.weather.R
import com.goat.weather.base.BaseActivity
import com.goat.weather.model.HourDataModel
import com.goat.weather.tasks.Constants
import kotlinx.android.synthetic.main.activity_hourly_details.*


class HourlyDetailsActivity: BaseActivity<HourlyDetailsPresenter, HourlyDetailsContract.IView>(), HourlyDetailsContract.IView {

    override val layoutId: Int
        get() = R.layout.activity_hourly_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val list = intent?.getParcelableArrayListExtra<HourDataModel>(Constants.KEY_INTENT_HOUR_DATA_LIST)
        mPresenter?.processHourlyData(list)
    }

    override fun loadHourlyData(data: List<HourDataModel>) {
        rvHourly.layoutManager = LinearLayoutManager(this)
        val adapter = HourlyCustomAdapter(data)
        rvHourly.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvHourly.adapter = adapter
    }

    override fun loadHourlyDataFailed(errorMsgId: Int) {
        Toast.makeText(applicationContext, resources.getString(errorMsgId), Toast.LENGTH_LONG).show()
    }
}