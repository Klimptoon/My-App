package com.example.myfirstapp.presentation

import PurchaseUsecase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class ChartViewModel(private val purchaseUseCase: PurchaseUsecase) : ViewModel() {

    var allListLiveData = MutableLiveData<List<Purchase>>()


    fun getAllPurchases() {
        viewModelScope.launch {
            allListLiveData.value = purchaseUseCase.getStartData()
        }
    }
    fun sortByTypeRatio(type : String) : Float {
        var valueOfType = 0.0
        var value = 0.0
        var ratio = 0.0f
        viewModelScope.launch {
            val list = purchaseUseCase.getStartData()
            list.forEach {
                value += it.cost.toDouble()
                if(it.type == type) {
                    valueOfType += it.cost.toDouble()

                }
            }
            ratio = (valueOfType/value).toFloat()
        }
        return ratio
    }

    fun sortToday(purchaseList : List<Purchase>) : List<Purchase> {   //функции для сортировки списка по выбранному периоду времени
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val listOfPurchase = mutableListOf<Purchase>()
        for(purchase in purchaseList) {
            if(purchase.date == sdf.format(Date())) {
                listOfPurchase.add(purchase)
            }
        }
        return listOfPurchase
    }
    fun sortWeek(purchaseList : List<Purchase>) : List<Purchase>  {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val listOfPurchase = mutableListOf<Purchase>()
        for(purchase in purchaseList) {
            if(purchase.date.substringAfter('.') == sdf.format(Date()).substringAfter('.')) {
                if(purchase.date.substringBefore('.').toInt() in sdf.format(Date()).substringBefore('.').toInt() - 7..sdf.format(
                        Date()
                    ).substringBefore('.').toInt()) {
                    listOfPurchase.add(purchase)
                }
            }
        }
        return listOfPurchase
    }
    fun sortMonth(purchaseList : List<Purchase>) : List<Purchase>  {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val listOfPurchase = mutableListOf<Purchase>()
        for(purchase in purchaseList) {
            if(purchase.date.substringAfterLast('.') == sdf.format(Date()).substringAfterLast('.')) {
                if(purchase.date.substringAfter('.').substringBeforeLast('.').toInt() == sdf.format(
                        Date()
                    ).substringAfter('.').substringBeforeLast('.').toInt()) {
                    listOfPurchase.add(purchase)
                }
            }
        }
        return listOfPurchase
    }
    fun sortYear(purchaseList : List<Purchase>) : List<Purchase>  {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val listOfPurchase = mutableListOf<Purchase>()
        for(purchase in purchaseList) {
            if(purchase.date.substringAfterLast('.') == sdf.format(Date()).substringAfterLast('.')) {
                listOfPurchase.add(purchase)
            }
        }
        return listOfPurchase
    }
}