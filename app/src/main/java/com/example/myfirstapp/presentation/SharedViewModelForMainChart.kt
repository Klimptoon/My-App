package com.example.myfirstapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModelForMainChart : ViewModel() {

    var dateLiveData = MutableLiveData<String>()

    fun saveCurrentDate(date : String) {
        dateLiveData.value = date
    }
}