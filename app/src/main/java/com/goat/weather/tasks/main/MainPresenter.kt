package com.goat.weather.tasks.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.fragment.app.FragmentActivity
import autodispose2.AutoDispose
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import com.goat.weather.R
import com.goat.weather.model.Blocks
import com.goat.weather.model.WheatherDataModel
import com.goat.weather.net.ApiHeader
import com.goat.weather.net.BaseObserver
import com.goat.weather.net.RetrofitModule
import com.goat.weather.net.WeatherApiService
import com.goat.weather.utils.DateUtil
import com.goat.weather.utils.LocationUtil
import com.goat.weather.utils.NetWorkUtil
import com.goat.weather.utils.StringUtil
import com.tbruyelle.rxpermissions3.RxPermissions
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.NullPointerException
import java.lang.ref.WeakReference
import javax.inject.Inject


class MainPresenter @Inject constructor(): MainContract.Presenter(), LocationListener {

    companion object {
        private const val KEY_EXCLUDE = "exclude"
        private const val KEY_LANG = "lang"
        private const val KEY_UNITS = "units"

        private const val LANG_ZH = "zh"
        private const val UNIT_AUTO = "auto"

        private const val DISPLAY_MSG_PLEASE_CHECK_NETWORK = R.string.error_display_msg_network_connection_exception
        private const val DISPLAY_MSG_PLEASE_ALLOW_LOCATION_PERMISSION = R.string.pls_allow_location_permission
    }

    private var rxPermissions: RxPermissions? = null
    private var locationManager: LocationManager? = null
    private var locationProvider: String? = null
    private var longitude: Double? = null
    private var latitude: Double? = null
    private var mWeakReference: WeakReference<Activity>? = null

    override fun requestLocationPermission(activity: FragmentActivity) {
        if (!NetWorkUtil.isConnected(activity)) {
            mIView?.loadDataFailed(DISPLAY_MSG_PLEASE_CHECK_NETWORK)
            return
        }
        mWeakReference = WeakReference(activity)
        var activity = mWeakReference?.get() as FragmentActivity
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
                            mIView?.showLocationDisallow(DISPLAY_MSG_PLEASE_ALLOW_LOCATION_PERMISSION)
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
        var location: Location? = locationProvider?.let { locationManager?.getLastKnownLocation(it) }
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
        if (null != location) {
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

    private fun requestWeatherData(activity: FragmentActivity) {
        if (latitude == null || longitude == null) {
            mIView?.loadDataFailed(DISPLAY_MSG_PLEASE_ALLOW_LOCATION_PERMISSION)
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
                    var dayData = data.daily?.data?.get(0)
                    if (dayData != null) {
                        mIView?.loadCurrentDayData(dayData)
                    } else {
                        mIView?.loadDataFailed(DISPLAY_MSG_SERVER_EXCEPTION)
                    }
                }

                override fun onFailure(errorDisplayMsgId: Int) {
                    mIView?.loadDataFailed(errorDisplayMsgId)
                }
            })

    }

}