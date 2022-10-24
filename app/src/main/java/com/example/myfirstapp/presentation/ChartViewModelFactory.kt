package com.example.myfirstapp.presentation

import PurchaseUsecase
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.domain.CurrencyUseCase

class ChartViewModelFactory(context : Context) : ViewModelProvider.Factory {
    private val purchaseUsecase by lazy { PurchaseUsecase(context) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChartViewModel(purchaseUseCase = purchaseUsecase) as T
    }
}