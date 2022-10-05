package com.example.myfirstapp.presentation

import PurchaseUsecase
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myfirstapp.R
import com.example.myfirstapp.data.PurchaseDatabase
import com.example.myfirstapp.databinding.ActivityMainBinding
import com.example.myfirstapp.databinding.PeriodBinding
import com.example.myfirstapp.databinding.PurchaseInputBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    lateinit var appDb : PurchaseDatabase


    val currentData = getCurrentDateDay()
    val dateWeek = getCurrentDateDay().substringBefore(',').toInt() - 7
    val sdfWeek = SimpleDateFormat("$dateWeek - dd, MMM yyyy")
    val sdfMonth = SimpleDateFormat("MMM yyyy")
    val sdfYear = SimpleDateFormat("yyyy ГОД")
    val purchaseUsecase by lazy { PurchaseUsecase(applicationContext) }
    var imageId: Int = 0
    var type: String = ""
    var title: String = ""
    var costPurchase: String = ""


    private val adapter = PurchaseAdapter()

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rv.layoutManager = GridLayoutManager(this@MainActivity, 1)
        binding.rv.adapter = adapter
        appDb = PurchaseDatabase.getInstance(applicationContext)

        lifecycleScope.launch(Dispatchers.Main) {
            val listStart = withContext(Dispatchers.IO) {                                           //для вывода списка после открытия приложения
                setData()
            }
            sortToday(listStart)
            Log.d("ff", listStart.toString())
            Log.d("ff", getCurrentDateDay())
        }



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
                lifecycleScope.launch {
                    withContext(Dispatchers.Main) {
                        whenForItemClick(purchaseTypes)
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
                        }, type, title, costPurchase, currentData
                    )
                    adapter.addPurchase(purchase)
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                        purchaseUsecase.addPurchase(purchase)
                        }
                    }
                    counter++
                }
            }

        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()

    }

    suspend fun setData(): List<Purchase> {                       //функция для установления первых данных после открытия приложения
        return purchaseUsecase.getStartData()
    }


    suspend fun sortByType(type : String): List<Purchase> {         //функция с сортировкой по типу покупки
        return purchaseUsecase.getData(type)
    }




    fun sortToday(purchaseList : List<Purchase>)  {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val listOfPurchase = mutableListOf<Purchase>()
        for(purchase in purchaseList) {
            if(purchase.date == getCurrentDateDay()) {
                listOfPurchase.add(purchase)
            }
        }
        Log.d("ff", listOfPurchase.toString())
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

    fun whenData(sortedList : List<Purchase>) {                     //функция для вызова сортировок по типу выбранного периода даты
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

    suspend fun whenForItemClick(purchaseTypes : Array<String>) {                     //функция которая объединяет сортировки по времени и по типу покупки
        when(binding.autoCompleteTextViewMain.text.toString()) {
            purchaseTypes[0] -> {
                val sortedList = sortByType(purchaseTypes[0])
                whenData(sortedList)
            }
            purchaseTypes[1] -> {
                val sortedList = sortByType(purchaseTypes[1])
                whenData(sortedList)
            }
            purchaseTypes[2] -> {
                val sortedList = sortByType(purchaseTypes[2])
                whenData(sortedList)
            }
            purchaseTypes[3] -> {
                val sortedList = sortByType(purchaseTypes[3])
                whenData(sortedList)
            }
            purchaseTypes[4] -> {
                val sortedList = sortByType(purchaseTypes[4])
                whenData(sortedList)
            }
            purchaseTypes[5] -> {
                val sortedList = sortByType(purchaseTypes[5])
                whenData(sortedList)
            }
        }
    }

}