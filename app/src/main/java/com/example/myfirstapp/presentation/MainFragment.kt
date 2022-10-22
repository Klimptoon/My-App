package com.example.myfirstapp.presentation

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myfirstapp.R
import com.example.myfirstapp.data.PurchaseDatabase
import com.example.myfirstapp.databinding.ActivityMainBinding
import com.example.myfirstapp.databinding.FragmentMainBinding
import com.example.myfirstapp.databinding.PeriodBinding
import com.example.myfirstapp.databinding.PurchaseInputBinding
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment() {


    lateinit var binding: FragmentMainBinding
    lateinit var appDb : PurchaseDatabase
    lateinit var viewModel : MainViewModel


    val dateWeek = getCurrentDateDay().substringBefore(',').toInt() - 7
    val sdfWeek = SimpleDateFormat("$dateWeek - dd, MMM yyyy")
    val sdfMonth = SimpleDateFormat("MMM yyyy")
    val sdfYear = SimpleDateFormat("yyyy ГОД")
    var imageId: Int = 0
    var type: String = ""
    var title: String = ""
    var costPurchase: String = ""


    private val adapter = PurchaseAdapter()

    private var counter = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)


        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this, MainViewModelFactory(requireContext())).get(MainViewModel::class.java)
        binding.rv.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.rv.adapter = adapter
        appDb = PurchaseDatabase.getInstance(requireContext())



        viewModel.setStartData()


        viewModel.startListLiveData.observe(viewLifecycleOwner) {                                   //для вывода списка после открытия приложения
            if (it != null) {
                adapter.setData(it)
            }
        }

        startMainSpinner()                 //запуск спинера с выбором покупки(еда , дом и т.д)

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
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, purchaseTypes)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.autoCompleteTextViewMain.setAdapter(arrayAdapter)
        binding.autoCompleteTextViewMain.onItemClickListener  = object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>?, view : View?, position: Int, item_id: Long) {
                whenForItemClick(purchaseTypes)
            }
        }
    }

    private fun showAlertDialogForTime() {                           // алерт диалог для выбора периода времени
        val dialogBinding = PeriodBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
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



    private fun getCurrentDateDay(): String {                         // Функция для получения даты
        val sdf = SimpleDateFormat("dd, MMM yyyy")
        return sdf.format(Date())
    }


    private fun showAlertDialog() {                                            //Алерт диалог с ресайклером
        val dialogBinding = PurchaseInputBinding.inflate(layoutInflater)

        val purchaseTypes = resources.getStringArray(R.array.types)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, purchaseTypes)
        dialogBinding.autoCompleteTextView.setAdapter(arrayAdapter)

        val dialog = AlertDialog.Builder(requireContext())
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
                    viewModel.addPurchase(purchase)
                    counter++
                }
            }

        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()

    }

    fun whenData() {                               //функция для вызова сортировок по типу выбранного периода даты
        when (binding.textViewDate.text) {
            getCurrentDateDay() -> {
                viewModel.setStartData()
            }
            sdfWeek.format(Date()).toString() -> {
                viewModel.setWeekData()
            }
            sdfMonth.format(Date()).toString() -> {
                viewModel.setMonthData()
            }
            sdfYear.format(Date()).toString() -> {
                viewModel.setYearData()
            }
        }
    }

    fun whenForItemClick(purchaseTypes : Array<String>) {                     //функция которая объединяет сортировки по времени и по типу покупки

        when(binding.autoCompleteTextViewMain.text.toString()) {
            purchaseTypes[0] -> {
                viewModel.setDataWithType(purchaseTypes[0])
                whenData()
            }
            purchaseTypes[1] -> {
                viewModel.setDataWithType(purchaseTypes[1])
                whenData()
            }
            purchaseTypes[2] -> {
                viewModel.setDataWithType(purchaseTypes[2])
                whenData()
            }
            purchaseTypes[3] -> {
                viewModel.setDataWithType(purchaseTypes[3])
                whenData()
            }
            purchaseTypes[4] -> {
                viewModel.setDataWithType(purchaseTypes[4])
                whenData()
            }
            purchaseTypes[5] -> {
                viewModel.setDataWithType(purchaseTypes[5])
                whenData()
            }
        }
    }


}