package com.example.myfirstapp.presentation

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.ActivityMainBinding
import com.example.myfirstapp.databinding.PeriodBinding
import com.example.myfirstapp.databinding.PurchaseInputBinding
import com.example.myfirstapp.domain.PurchaseUsecase
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    val dateWeek = getCurrentDateDay().substringBefore(',').toInt() - 7
    val sdfWeek = SimpleDateFormat("$dateWeek - dd, MMM yyyy")
    val sdfMonth = SimpleDateFormat("MMM yyyy")
    val sdfYear = SimpleDateFormat("yyyy ГОД")
    val purchaseUsecase = PurchaseUsecase()
    var imageId: Int = 0
    var type: String = ""
    var title: String = ""
    var costPurchase: String = ""


    private val adapter = RecyclerAdapter()

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rv.layoutManager = GridLayoutManager(this@MainActivity, 1)
        binding.rv.adapter = adapter

        val listStart = setData()                  //для вывода списка после открытия приложения
        sortToday(listStart)


        startMainSpinner()                 //запуск спинера с выбором покупки

        binding.buttonAdd.setOnClickListener {
            showAlertDialog()
        }
        binding.textViewDate.text = getCurrentDateDay()

        binding.alertToolBar.setOnClickListener {
            showAlertDialogForTime()
        }
    }


    private fun startMainSpinner() {                                   //Функция для выбора типа покупки
        val purchaseTypes = resources.getStringArray(R.array.types)
        val arrayAdapter = ArrayAdapter(this@MainActivity, R.layout.dropdown_item, purchaseTypes)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.autoCompleteTextViewMain.setAdapter(arrayAdapter)
        binding.autoCompleteTextViewMain.onItemClickListener  = object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>?, view : View?, position: Int, item_id: Long) {
                when(position) {
                    0 -> {
                        val sortedList = setData()
                        when (binding.textViewDate.text) {
                            getCurrentDateDay() -> {
                                sortToday(sortedList)
                            }
                            sdfWeek.format(Date()).toString() -> {
                                sortWeek(sortedList)
                            }
                            sdfMonth.format(Date()).toString() -> {
                                sortMonth(sortedList)
                            }
                            sdfYear.format(Date()).toString() -> {
                                sortYear(sortedList)
                            }
                        }
                    }
                    1 -> {
                        val sortedList = sortClothes()
                        when (binding.textViewDate.text) {
                            getCurrentDateDay() -> {
                                sortToday(sortedList)
                            }
                            sdfWeek.format(Date()).toString() -> {
                                sortWeek(sortedList)
                            }
                            sdfMonth.format(Date()).toString() -> {
                                sortMonth(sortedList)
                            }
                            sdfYear.format(Date()).toString() -> {
                                sortYear(sortedList)
                            }
                        }
                    }
                    2 -> {
                        val sortedList = sortFood()
                        when (binding.textViewDate.text) {
                            getCurrentDateDay() -> {
                                sortToday(sortedList)
                            }
                            sdfWeek.format(Date()).toString() -> {
                                sortWeek(sortedList)
                            }
                            sdfMonth.format(Date()).toString() -> {
                                sortMonth(sortedList)
                            }
                            sdfYear.format(Date()).toString() -> {
                                sortYear(sortedList)
                            }
                        }
                    }
                    3 -> {
                        val sortedList = sortRest()
                        when (binding.textViewDate.text) {
                            getCurrentDateDay() -> {
                                sortToday(sortedList)
                            }
                            sdfWeek.format(Date()).toString() -> {
                                sortWeek(sortedList)
                            }
                            sdfMonth.format(Date()).toString() -> {
                                sortMonth(sortedList)
                            }
                            sdfYear.format(Date()).toString() -> {
                                sortYear(sortedList)
                            }
                        }
                    }
                    4 -> {
                        val sortedList = sortHouse()
                        when (binding.textViewDate.text) {
                            getCurrentDateDay() -> {
                                sortToday(sortedList)
                            }
                            sdfWeek.format(Date()).toString() -> {
                                sortWeek(sortedList)
                            }
                            sdfMonth.format(Date()).toString() -> {
                                sortMonth(sortedList)
                            }
                            sdfYear.format(Date()).toString() -> {
                                sortYear(sortedList)
                            }
                        }
                    }
                    5 -> {
                        val sortedList = sortOther()
                        when (binding.textViewDate.text) {
                            getCurrentDateDay() -> {
                                sortToday(sortedList)
                            }
                            sdfWeek.format(Date()).toString() -> {
                                sortWeek(sortedList)
                            }
                            sdfMonth.format(Date()).toString() -> {
                                sortMonth(sortedList)
                            }
                            sdfYear.format(Date()).toString() -> {
                                sortYear(sortedList)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getCurrentDateDay(): String {                         // Функция для получения даты
        val sdf = SimpleDateFormat("dd, MMM yyyy")
        return sdf.format(Date())
    }


    private fun showAlertDialogForTime() {                           // алерт диалог для выбора периода времени
        val dialogBinding = PeriodBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()
        dialog.setOnShowListener {
            dialogBinding.cardDay.setOnClickListener {
                dialog.dismiss()
                binding.textViewDate.text = getCurrentDateDay()
            }
            dialogBinding.cardWeek.setOnClickListener {
                dialog.dismiss()
                val date = getCurrentDateDay().substringBefore(',').toInt() - 7
                val sdf = SimpleDateFormat("$date - dd, MMM yyyy")
                binding.textViewDate.text = sdf.format(Date())
            }
            dialogBinding.cardMonth.setOnClickListener {
                dialog.dismiss()
                val sdf = SimpleDateFormat("MMM yyyy")
                binding.textViewDate.text = sdf.format(Date())
            }
            dialogBinding.cardYear.setOnClickListener {
                dialog.dismiss()
                val sdf = SimpleDateFormat("yyyy ГОД")
                binding.textViewDate.text = sdf.format(Date())
            }

        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun showAlertDialog() {                                            //Алерт диалог с ресайклером
        val dialogBinding = PurchaseInputBinding.inflate(layoutInflater)

        val purchaseTypes = resources.getStringArray(R.array.types)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, purchaseTypes)
        dialogBinding.autoCompleteTextView.setAdapter(arrayAdapter)

        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.info)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.confirm, null)
            .create()
        dialog.setOnShowListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val enteredText = dialogBinding.etName.text.toString()
                val cost = dialogBinding.etCost.text.toString()
                val enteredText2 = cost.toDoubleOrNull()
                if (enteredText2 == null || enteredText2 < 0) {
                    dialogBinding.etCost.error = getString(R.string.empty)
                    return@setOnClickListener
                }
                if (enteredText.isBlank()) {
                    dialogBinding.etName.error = getString(R.string.empty)
                    return@setOnClickListener
                }
                type = dialogBinding.autoCompleteTextView.text.toString()
                title = dialogBinding.etName.text.toString()
                costPurchase = dialogBinding.etCost.text.toString()
                dialog.dismiss()
                binding.apply {
                    if (counter > 3) counter = 0
                    val purchase = Purchase(
                        when (type) {
                            "Еда" -> R.drawable.circle_shape_red
                            "Одежда" -> R.drawable.circle_shape_blue
                            "Развлечения" -> R.drawable.circle_shape_green
                            "Дом" -> R.drawable.circle_shape_yellow
                            else -> R.drawable.circle_shape_blue
                        }, type, title, costPurchase
                    )
                    adapter.addPurchase(purchase)
                    counter++
                }
            }

        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()

    }

    fun setData(): List<Purchase> {
        return purchaseUsecase.getData()
    }

    fun sortFood() : List<Purchase> {
        val purchaseList = purchaseUsecase.getData()
        val listOfFood = mutableListOf<Purchase>()
        for(purchase in purchaseList) {
            if(purchase.type == "Еда") {
                listOfFood.add(purchase)
            }
        }
        return listOfFood
    }
    fun sortClothes() : List<Purchase> {
        val purchaseList = purchaseUsecase.getData()
        val listOfClothes = mutableListOf<Purchase>()
        for(purchase in purchaseList) {
            if(purchase.type == "Одежда") {
                listOfClothes.add(purchase)
            }
        }
        return listOfClothes
    }
    fun sortRest() : List<Purchase> {
        val purchaseList = purchaseUsecase.getData()
        val listOfRest = mutableListOf<Purchase>()
        for(purchase in purchaseList) {
            if(purchase.type == "Отдых") {
                listOfRest.add(purchase)
            }
        }
        return listOfRest
    }
    fun sortHouse() : List<Purchase> {
        val purchaseList = purchaseUsecase.getData()
        val listOfHouse = mutableListOf<Purchase>()
        for(purchase in purchaseList) {
            if(purchase.type == "Дом") {
                listOfHouse.add(purchase)
            }
        }
        return listOfHouse
    }
    fun sortOther() : List<Purchase>  {
        val purchaseList = purchaseUsecase.getData()
        val listOfOther = mutableListOf<Purchase>()
        for(purchase in purchaseList) {
            if(purchase.type == "Прочее") {
                listOfOther.add(purchase)
            }
        }
        return listOfOther
    }
    fun sortToday(purchaseList : List<Purchase>)  {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val listOfPurchase = mutableListOf<Purchase>()
        for(purchase in purchaseList) {
            if(purchase.date == sdf.format(Date())) {
                listOfPurchase.add(purchase)
            }
        }
        adapter.setData(listOfPurchase)
    }
    fun sortWeek(purchaseList : List<Purchase>)  {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val listOfPurchase = mutableListOf<Purchase>()
        for(purchase in purchaseList) {
            if(purchase.date.substringAfter('.') == sdf.format(Date()).substringAfter('.')) {
                if(purchase.date.substringBefore('.').toInt() in sdf.format(Date()).substringBefore('.').toInt() - 7..sdf.format(Date()).substringBefore('.').toInt()) {
                    listOfPurchase.add(purchase)
                }
            }
        }
        adapter.setData(listOfPurchase)
    }
    fun sortMonth(purchaseList : List<Purchase>)  {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val listOfPurchase = mutableListOf<Purchase>()
        for(purchase in purchaseList) {
            if(purchase.date.substringAfterLast('.') == sdf.format(Date()).substringAfterLast('.')) {
                if(purchase.date.substringAfter('.').substringBeforeLast('.').toInt() == sdf.format(Date()).substringAfter('.').substringBeforeLast('.').toInt()) {
                    listOfPurchase.add(purchase)
                }
            }
        }
        adapter.setData(listOfPurchase)
    }
    fun sortYear(purchaseList : List<Purchase>)  {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val listOfPurchase = mutableListOf<Purchase>()
        for(purchase in purchaseList) {
            if(purchase.date.substringAfterLast('.') == sdf.format(Date()).substringAfterLast('.')) {
                    listOfPurchase.add(purchase)
            }
        }
        adapter.setData(listOfPurchase)
    }

}