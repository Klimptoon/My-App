package com.example.myfirstapp.presentation

import PurchaseUsecase
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(context : Context) : ViewModelProvider.Factory {


    private val purchaseUsecase by lazy { PurchaseUsecase(context = context) }


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(purchaseUsecase = purchaseUsecase) as T
    }
}