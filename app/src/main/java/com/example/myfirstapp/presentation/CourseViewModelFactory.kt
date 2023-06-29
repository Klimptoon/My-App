package com.example.myfirstapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.domain.CurrencyUseCase

class CourseViewModelFactory() : ViewModelProvider.Factory {

    private val currencyUseCase by lazy { CurrencyUseCase() }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CourseViewModel(currencyUseCase = currencyUseCase) as T
    }
}
