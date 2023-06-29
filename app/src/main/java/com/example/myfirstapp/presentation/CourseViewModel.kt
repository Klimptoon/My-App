package com.example.myfirstapp.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapp.domain.CurrencyUseCase
import com.example.myfirstapp.network.CurrencyList
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class CourseViewModel(private val currencyUseCase: CurrencyUseCase) : ViewModel() {


    val myCurrencyList: MutableLiveData<CurrencyList> = MutableLiveData()
    val isConnected: MutableLiveData<Int> = MutableLiveData()

    fun getCurrencyCourse() {
        viewModelScope.launch {
            try {
                currencyUseCase.getCurrency().body()?.let {
                    myCurrencyList.postValue(it)
                }
                isConnected.postValue(1)
            } catch (e: UnknownHostException) {
                isConnected.postValue(0)
            }
        }
        Log.d("mm", "вот значение = $isConnected  ")
    }
}
