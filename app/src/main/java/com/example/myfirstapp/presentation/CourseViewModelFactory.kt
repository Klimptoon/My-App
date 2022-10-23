package com.example.myfirstapp.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.domain.CurrencyUseCase

class CourseViewModelFactory(context : Context) : ViewModelProvider.Factory {

    private val currencyUseCase by lazy { CurrencyUseCase(context) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CourseViewModel(currencyUseCase = currencyUseCase) as T
    }
}