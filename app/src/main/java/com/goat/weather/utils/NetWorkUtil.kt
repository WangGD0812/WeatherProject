package com.goat.weather.utils

import android.content.Context
import android.net.ConnectivityManager

object NetWorkUtil {

    //判断网络状态，有网络返回true
    fun isConnected(context: Context?): Boolean {
        return isNetworkConnected(context) || isWifiConnected(context)
    }

    //判断手机是否有网络连接
    private fun isNetworkConnected(context: Context?): Boolean {
        return context?.let {
            val mConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            mConnectivityManager.activeNetworkInfo?.isAvailable
        } ?: false
    }

    //判断wifi网络是否可用
    private fun isWifiConnected(context: Context?): Boolean {
        return context?.let {
            val mConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isAvailable
        } ?: false
    }

}