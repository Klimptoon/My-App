package com.example.myfirstapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapp.domain.CurrencyUseCase
import com.example.myfirstapp.network.CourseRepository
import com.example.myfirstapp.network.Currency
import com.example.myfirstapp.network.CurrencyList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class CourseViewModel(private val currencyUseCase: CurrencyUseCase) : ViewModel() {


    val myCurrencyList : MutableLiveData<CurrencyList> = MutableLiveData()

    fun getCurrencyCourse() {
        viewModelScope.launch {
            currencyUseCase.getCurrency().body()?.let {
                myCurrencyList.postValue(it)
            }
        }
    }

}