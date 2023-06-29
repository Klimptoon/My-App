package com.example.myfirstapp.presentation

import PurchaseUsecase
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class ChartViewModel(private val purchaseUseCase: PurchaseUsecase) : ViewModel() {

    var startLiveData = MutableLiveData<MutableList<PieEntry>>()


    fun sortByTypesDay() {                                         //функция для заполнения списка PieEntry и передачей списка в лайв дату
        var valueOfType = 0.0f
        val listPieEntry = mutableListOf<PieEntry>()
        viewModelScope.launch(Dispatchers.Main) {
            val list = purchaseUseCase.getStartData()
            val listOfTypes = listOf("Одежда", "Еда", "Отдых", "Дом", "Прочее")
            for (type in listOfTypes) {
                Log.d("ff", sortToday(list).toString())
                sortToday(list).forEach {
                    if (it.type == type) {
                        valueOfType += it.cost.toFloat()
                    }
                }
                if (valueOfType >= 0.1f) {
                    withContext(Dispatchers.Main) {
                        listPieEntry.add(PieEntry(valueOfType, type))
                    }
                } else withContext(Dispatchers.Main) {

                }
                valueOfType = 0.0f
            }
            startLiveData.value = listPieEntry
        }
    }

    fun sortByTypesWeek() {                                         //функция для заполнения списка PieEntry и передачей списка в лайв дату
        var valueOfType = 0.0f
        val listPieEntry = mutableListOf<PieEntry>()
        viewModelScope.launch(Dispatchers.Main) {
            val list = purchaseUseCase.getStartData()
            val listOfTypes = listOf("Одежда", "Еда", "Отдых", "Дом", "Прочее")
            for (type in listOfTypes) {
                sortWeek(list).forEach {
                    if (it.type == type) {
                        valueOfType += it.cost.toFloat()
                    }
                }
                if (valueOfType >= 0.1f) {
                    withContext(Dispatchers.Main) {
                        listPieEntry.add(PieEntry(valueOfType, type))
                    }
                } else withContext(Dispatchers.Main) {

                }
                valueOfType = 0.0f
            }
            startLiveData.value = listPieEntry
        }
    }

    fun sortByTypesMonth() {                                         //функция для заполнения списка PieEntry и передачей списка в лайв дату
        var valueOfType = 0.0f
        val listPieEntry = mutableListOf<PieEntry>()
        viewModelScope.launch(Dispatchers.Main) {
            val list = purchaseUseCase.getStartData()
            val listOfTypes = listOf("Одежда", "Еда", "Отдых", "Дом", "Прочее")
            for (type in listOfTypes) {
                sortMonth(list).forEach {
                    if (it.type == type) {
                        valueOfType += it.cost.toFloat()
                    }
                }
                if (valueOfType >= 0.1f) {
                    withContext(Dispatchers.Main) {
                        listPieEntry.add(PieEntry(valueOfType, type))
                    }
                } else withContext(Dispatchers.Main) {

                }
                valueOfType = 0.0f
            }
            startLiveData.value = listPieEntry
        }
    }

    fun sortByTypesYear() {                                         //функция для заполнения списка PieEntry и передачей списка в лайв дату
        var valueOfType = 0.0f
        val listPieEntry = mutableListOf<PieEntry>()
        viewModelScope.launch(Dispatchers.Main) {
            val list = purchaseUseCase.getStartData()
            val listOfTypes = listOf("Одежда", "Еда", "Отдых", "Дом", "Прочее")
            for (type in listOfTypes) {
                sortYear(list).forEach {
                    if (it.type == type) {
                        valueOfType += it.cost.toFloat()
                    }
                }
                if (valueOfType >= 0.1f) {
                    withContext(Dispatchers.Main) {
                        listPieEntry.add(PieEntry(valueOfType, type))
                    }
                } else withContext(Dispatchers.Main) {

                }
                valueOfType = 0.0f
            }
            startLiveData.value = listPieEntry
        }
    }


    fun sortToday(purchaseList: List<Purchase>): List<Purchase> {   //функции для сортировки списка по выбранному периоду времени
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val listOfPurchase = mutableListOf<Purchase>()
        for (purchase in purchaseList) {
            if (purchase.date == sdf.format(Date())) {
                listOfPurchase.add(purchase)
            }
        }
        return listOfPurchase
    }

    fun sortWeek(purchaseList: List<Purchase>): List<Purchase> {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val listOfPurchase = mutableListOf<Purchase>()
        for (purchase in purchaseList) {
            if (purchase.date.substringAfter('.') == sdf.format(Date()).substringAfter('.')) {
                if (purchase.date.substringBefore('.').toInt() in sdf.format(Date())
                        .substringBefore('.').toInt() - 7..sdf.format(
                        Date()
                    ).substringBefore('.').toInt()
                ) {
                    listOfPurchase.add(purchase)
                }
            }
        }
        return listOfPurchase
    }

    fun sortMonth(purchaseList: List<Purchase>): List<Purchase> {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val listOfPurchase = mutableListOf<Purchase>()
        for (purchase in purchaseList) {
            if (purchase.date.substringAfterLast('.') == sdf.format(Date())
                    .substringAfterLast('.')
            ) {
                if (purchase.date.substringAfter('.').substringBeforeLast('.')
                        .toInt() == sdf.format(
                        Date()
                    ).substringAfter('.').substringBeforeLast('.').toInt()
                ) {
                    listOfPurchase.add(purchase)
                }
            }
        }
        return listOfPurchase
    }

    fun sortYear(purchaseList: List<Purchase>): List<Purchase> {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val listOfPurchase = mutableListOf<Purchase>()
        for (purchase in purchaseList) {
            if (purchase.date.substringAfterLast('.') == sdf.format(Date())
                    .substringAfterLast('.')
            ) {
                listOfPurchase.add(purchase)
            }
        }
        return listOfPurchase
    }
}