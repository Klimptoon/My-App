package com.example.myfirstapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapp.network.CourseRepository
import com.example.myfirstapp.network.Currency
import com.example.myfirstapp.network.CurrencyList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class CourseViewModel : ViewModel() {

    var repository = CourseRepository()
    val myCurrencyList : MutableLiveData<CurrencyList> = MutableLiveData()

    fun getCurrencyCourse() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.getCurrency().body()?.let {
                myCurrencyList.postValue(it)
            }
        }
    }

}