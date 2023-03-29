package com.goat.weather.base

import androidx.databinding.ViewDataBinding
import com.goat.weather.BR
import com.goat.weather.utils.injectViewModel

open class BaseBindingViewModelActivity<BINDING : ViewDataBinding, VM : BaseViewModel>:
    BaseBindingActivity<BINDING>() {



    //创建 ViewModel 变量并延迟初始化
    val viewModel:VM by lazy {
        createViewModel()
    }

    override fun initDataBinding(binding: BINDING) {
        //绑定 viewModel
        //绑定变量为 vm。
        // 具体业务实现中在实际的布局 xml 文件中声明当前视图的 ViewModel 变量为 vm 即可自动进行绑定。
        binding.setVariable(BR.vm, viewModel)

    }

    /**
     * @description 初始化 ViewModel 并自动进行绑定
     * @return VM ViewModel 实例对象
     */
    private fun createViewModel():VM{
        try {
            //注入 ViewModel，并转换为 VM 类型
            val viewModel = injectViewModel() as VM
            viewModel.bind(this)
            return viewModel
        }catch (e:Exception){
            // 抛出异常
            throw Exception("ViewModel is not inject", e)
        }
    }
}