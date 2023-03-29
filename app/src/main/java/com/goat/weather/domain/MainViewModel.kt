package com.goat.weather.domain

import android.util.Log
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.goat.weather.base.BaseBindingAdapter
import com.goat.weather.base.BaseViewModel
import com.goat.weather.data.model.CityInfoModel
import com.goat.weather.data.model.WheatherDataModel
import com.goat.weather.ui.adapter.DefaultBindingAdapter

class MainViewModel: BaseViewModel() {

    var cityListLiveData = MutableLiveData<List<CityInfoModel>>()
    val cityList = mutableListOf<CityInfoModel>()

    init {
        cityList.add(CityInfoModel("Beijing", 110000))
        cityList.add(CityInfoModel("Shanghai", 310000))
        cityList.add(CityInfoModel("Guangzhou", 440100))
        cityList.add(CityInfoModel("Shenzhen", 440300))
        cityList.add(CityInfoModel("Suzhou", 320500))
        cityList.add(CityInfoModel("Shengyang", 210100))
    }

    fun onItemClick(item: Any?) {
        if (item is CityInfoModel) {
            Log.d("===", "---${item.cityName}")
        }
    }


}