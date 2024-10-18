package com.baka.composeapp.ui.drawermenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baka.composeapp.helper.Logger

class HomeViewModel : ViewModel() {
    private val _count = MutableLiveData(0)
    val count: LiveData<Int> get() = _count

    fun incrementCount() {
        Logger.i("incrementCount is called")
        _count.postValue(_count.value?.plus(1))
    }
}