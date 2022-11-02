package com.example.myfirstapp.presentation


import PurchaseUsecase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val purchaseUsecase : PurchaseUsecase) : ViewModel() {

    var startListLiveData = MutableLiveData<List<Purchase>>()



    init {

    }

    override fun onCleared() {
        super.onCleared()
    }


    fun addPurchase(purchase: Purchase) {                               //функция для добавления элементов в список
        viewModelScope.launch(Dispatchers.IO) {
            purchaseUsecase.addPurchase(purchase)
        }
    }

    fun deletePurchase(purchase: Purchase) {
        viewModelScope.launch(Dispatchers.IO) {
            purchaseUsecase.deletePurchase(purchase)
        }
    }

    fun setStartData() {                                                    //функция для установления первых данных после открытия приложения
        viewModelScope.launch {
            val listOfPurchase = purchaseUsecase.getStartData()
            startListLiveData.value = sortToday(listOfPurchase)
        }
    }
    fun setWeekData() {                                                    //функция для установления данных если выбрали период неделя
        viewModelScope.launch {
            val listOfPurchase = purchaseUsecase.getStartData()
            startListLiveData.value = sortWeek(listOfPurchase)
        }
    }
    fun setMonthData() {                                                 //функция для установления данных если выбрали период месяц
        viewModelScope.launch {
            val listOfPurchase = purchaseUsecase.getStartData()
            startListLiveData.value = sortMonth(listOfPurchase)
        }
    }
    fun setYearData() {                                                    //функция для установления данных если выбрали период год
        viewModelScope.launch {
            val listOfPurchase = purchaseUsecase.getStartData()
            startListLiveData.value = sortYear(listOfPurchase)
        }
    }

    fun setDataWithType(type : String) {                                  //функция для установки данных по типу покупки
        viewModelScope.launch {
            val listOfPurchase = purchaseUsecase.getData(type)
            startListLiveData.value = listOfPurchase
        }
    }
    fun setDataWithTypeToday(type : String) {                                  //функция для установки данных по типу покупки
        viewModelScope.launch {
            val listOfPurchase = purchaseUsecase.getData(type)
            val sortedList = sortToday(listOfPurchase)
            startListLiveData.value = sortedList
        }
    }

    fun setDataWithTypeWeek(type : String) {                                  //функция для установки данных по типу покупки
        viewModelScope.launch {
            val listOfPurchase = purchaseUsecase.getData(type)
            val sortedList = sortWeek(listOfPurchase)
            startListLiveData.value = sortedList
        }
    }
    fun setDataWithTypeMonth(type : String) {                                  //функция для установки данных по типу покупки
        viewModelScope.launch {
            val listOfPurchase = purchaseUsecase.getData(type)
            val sortedList = sortMonth(listOfPurchase)
            startListLiveData.value = sortedList
        }
    }
    fun setDataWithTypeYear(type : String) {                                  //функция для установки данных по типу покупки
        viewModelScope.launch {
            val listOfPurchase = purchaseUsecase.getData(type)
            val sortedList = sortWeek(listOfPurchase)
            startListLiveData.value = sortedList
        }
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