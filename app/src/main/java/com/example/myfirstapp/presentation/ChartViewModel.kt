package com.example.myfirstapp.presentation

import PurchaseUsecase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class ChartViewModel(private val purchaseUseCase: PurchaseUsecase) : ViewModel() {

    var allListLiveData = MutableLiveData<List<Purchase>>()

    fun getAllPurchases() {
        viewModelScope.launch {
            allListLiveData.value = purchaseUseCase.getStartData()
        }
    }
}