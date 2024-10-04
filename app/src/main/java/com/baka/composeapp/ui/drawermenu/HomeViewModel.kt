package com.baka.composeapp.ui.drawermenu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _count = MutableLiveData(0)
    val count: LiveData<Int> get() = _count

    fun incrementCount() {
        Log.i("Count", "Count called")
        _count.postValue(_count.value?.plus(1))
    }
}