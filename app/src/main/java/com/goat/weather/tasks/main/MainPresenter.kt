package com.goat.weather.tasks.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.fragment.app.FragmentActivity
import autodispose2.AutoDispose
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import com.goat.weather.R
import com.goat.weather.model.Blocks
import com.goat.weather.model.HourDataModel
import com.goat.weather.model.WheatherDataModel
import com.goat.weather.net.ApiHeader
import com.goat.weather.net.BaseObserver
import com.goat.weather.net.RetrofitModule
import com.goat.weather.net.WeatherApiService
import com.goat.weather.tasks.Constants
import com.goat.weather.tasks.details.HourlyDetailsActivity
import com.goat.weather.utils.DateUtil
import com.goat.weather.utils.LocationUtil
import com.goat.weather.utils.NetWorkUtil
import com.goat.weather.utils.StringUtil
import com.tbruyelle.rxpermissions3.RxPermissions
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.ref.WeakReference
import javax.inject.Inject


class MainPresenter @Inject constructor(): MainContract.Presenter(), LocationListener {

    companion object {
        private const val KEY_EXCLUDE = "exclude"
        private const val KEY_UNITS = "units"
        private const val UNIT_AUTO = "auto"
    }

    private var rxPermissions: RxPermissions? = null
    private var locationManager: LocationManager? = null
    private var locationProvider: String? = null
    var longitude: Double? = null
    var latitude: Double? = null
    private var mWeakReference: WeakReference<Activity>? = null
    private var hourDataList: ArrayList<HourDataModel>? = null

    override fun requestLocationPermission(activity: FragmentActivity) {
        if (!NetWorkUtil.isConnected(activity)) {
            mIView?.loadDataFailed(Constants.DISPLAY_MSG_PLEASE_CHECK_NETWORK)
            return
        }
        mWeakReference = WeakReference(activity)
        val activity = mWeakReference?.get() as FragmentActivity
        if (LocationUtil.isLocationPermissionGranted(activity)) {
            requestLocation(activity)
        } else {
            if (null == rxPermissions) {
                rxPermissions = RxPermissions(activity)
            }
            rxPermissions!!
                .requestEach(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ).subscribe { permission ->
                    when {
                        permission.granted -> {
                            requestLocation(activity)
                        } else -> {
                            mIView?.showLocationDisallow(Constants.DISPLAY_MSG_PLEASE_ALLOW_LOCATION_PERMISSION)
                        }
                    }
                }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation(activity: FragmentActivity) {
        if (null == locationManager) {
            locationManager = LocationUtil.getLocation(activity)
        }
        if (StringUtil.isEmpty(locationProvider)) {
            locationProvider = locationManager?.let { LocationUtil.getLocationProvider(it) }
        }
        val location: Location? = locationProvider?.let { locationManager?.getLastKnownLocation(it) }
        if (location != null) {
            longitude = location.longitude
            latitude = location.latitude
            requestWeatherData(activity)
        }
        locationProvider?.let {
            locationManager?.requestLocationUpdates(it, 1000, 100 * 1000.0F, this)
        }
    }

    override fun onLocationChanged(location: Location) {
        if (location != null) {
            longitude = location.longitude
            latitude = location.latitude
            requestWeatherData(mWeakReference?.get() as FragmentActivity)
        }
    }

    override fun detachView() {
        super.detachView()
        rxPermissions = null
        locationManager?.removeUpdates(this)
        locationManager = null
        locationProvider = null
        mWeakReference = null
    }

    override fun jumpToHourlyDetailsPage() {
        val activity = mWeakReference?.get()
        if (activity != null && hourDataList != null) {
            val intent = Intent(activity, HourlyDetailsActivity::class.java).apply {
                putParcelableArrayListExtra(Constants.KEY_INTENT_HOUR_DATA_LIST, hourDataList!!)
            }
            activity.startActivity(intent)
        }
    }

    override fun requestWeatherData(activity: FragmentActivity) {
        if (null == latitude || null == longitude) {
            mIView?.loadDataFailed(Constants.DISPLAY_MSG_PLEASE_NO_LOCATION_INFO_WAS_OBTAINED)
            return
        }
        val time: Long = DateUtil.getTimestampSeconds()
        val blocks = Blocks.CURRENTLY.block + StringUtil.COMMA + Blocks.FLAGS.block
        val requestParam = HashMap<String, String>()
        requestParam[KEY_EXCLUDE] = blocks
        requestParam[KEY_UNITS] = UNIT_AUTO
        RetrofitModule.getInstance()
            .create(WeatherApiService::class.java)
            .getWeatherData(ApiHeader.getCommonHeader(), latitude!!, longitude!!, time, requestParam)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
            .subscribe(object : BaseObserver<WheatherDataModel>() {
                override fun onSuccess(data: WheatherDataModel) {
                    val dayData = data.daily?.data?.get(0)
                    hourDataList = data.hourly?.data
                    if (dayData != null) {
                        mIView?.loadCurrentDayData(dayData)
                    } else {
                        mIView?.loadDataFailed(Constants.DISPLAY_MSG_DATA_EXCEPTION)
                    }
                }

                override fun onFailure(errorDisplayMsgId: Int) {
                    mIView?.loadDataFailed(errorDisplayMsgId)
                }
            })

    }

}