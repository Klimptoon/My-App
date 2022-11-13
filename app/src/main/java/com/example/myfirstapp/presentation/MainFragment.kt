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
import androidx.fragment.app.activityViewModels
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
    private val sharedViewModelForMainChart : SharedViewModelForMainChart by activityViewModels()


    private val dateWeek = getCurrentDateDay().substringBefore(',').toInt() - 7
    private val sdfWeek = SimpleDateFormat("$dateWeek - dd, MMM yyyy")
    private val sdfMonth = SimpleDateFormat("MMM yyyy")
    private val sdfYear = SimpleDateFormat("yyyy ГОД")
    var imageId: Int = 0
    var type: String = ""
    var title: String = ""
    var costPurchase: String = ""


    private val adapter = PurchaseAdapter()

    private var counter = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        Log.d("ff", "onCreateView - Main fragment")

        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ff", "onViewCreated - Main fragment")
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this, MainViewModelFactory(requireContext())).get(MainViewModel::class.java)
        binding.rv.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.rv.adapter = adapter
        appDb = PurchaseDatabase.getInstance(requireContext())
        sharedViewModelForMainChart.saveCurrentDate(getCurrentDateDay())




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
                whenPress(position, purchaseTypes)
            }
        }
    }

    fun whenPress(position : Int, purchaseTypes: Array<String>) {
        when(position){
            0 -> when(binding.textViewDate.text) {
                getCurrentDateDay() -> viewModel.setDataWithTypeToday(purchaseTypes[0])
                sdfWeek.format(Date()).toString() -> viewModel.setDataWithTypeWeek(purchaseTypes[0])
                sdfMonth.format(Date()).toString() -> viewModel.setDataWithTypeMonth(purchaseTypes[0])
                sdfYear.format(Date()).toString() -> viewModel.setDataWithTypeYear(purchaseTypes[0])
            }
            1 -> when(binding.textViewDate.text) {
                getCurrentDateDay() -> viewModel.setDataWithTypeToday(purchaseTypes[1])
                sdfWeek.format(Date()).toString() -> viewModel.setDataWithTypeWeek(purchaseTypes[1])
                sdfMonth.format(Date()).toString() -> viewModel.setDataWithTypeMonth(purchaseTypes[1])
                sdfYear.format(Date()).toString() -> viewModel.setDataWithTypeYear(purchaseTypes[1])
            }
            2 -> when(binding.textViewDate.text) {
                getCurrentDateDay() -> viewModel.setDataWithTypeToday(purchaseTypes[2])
                sdfWeek.format(Date()).toString() -> viewModel.setDataWithTypeWeek(purchaseTypes[2])
                sdfMonth.format(Date()).toString() -> viewModel.setDataWithTypeMonth(purchaseTypes[2])
                sdfYear.format(Date()).toString() -> viewModel.setDataWithTypeYear(purchaseTypes[2])
            }
            3 -> when(binding.textViewDate.text) {
                getCurrentDateDay() -> viewModel.setDataWithTypeToday(purchaseTypes[3])
                sdfWeek.format(Date()).toString() -> viewModel.setDataWithTypeWeek(purchaseTypes[3])
                sdfMonth.format(Date()).toString() -> viewModel.setDataWithTypeMonth(purchaseTypes[3])
                sdfYear.format(Date()).toString() -> viewModel.setDataWithTypeYear(purchaseTypes[3])
            }
            4 -> when(binding.textViewDate.text) {
                getCurrentDateDay() -> viewModel.setDataWithTypeToday(purchaseTypes[4])
                sdfWeek.format(Date()).toString() -> viewModel.setDataWithTypeWeek(purchaseTypes[4])
                sdfMonth.format(Date()).toString() -> viewModel.setDataWithTypeMonth(purchaseTypes[4])
                sdfYear.format(Date()).toString() -> viewModel.setDataWithTypeYear(purchaseTypes[4])
            }
            5 -> when(binding.textViewDate.text) {
                getCurrentDateDay() -> viewModel.setDataWithTypeToday(purchaseTypes[5])
                sdfWeek.format(Date()).toString() -> viewModel.setDataWithTypeWeek(purchaseTypes[5])
                sdfMonth.format(Date()).toString() -> viewModel.setDataWithTypeMonth(purchaseTypes[5])
                sdfYear.format(Date()).toString() -> viewModel.setDataWithTypeYear(purchaseTypes[5])
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
                viewModel.setStartData()
                binding.textViewDate.text = getCurrentDateDay()
                sharedViewModelForMainChart.saveCurrentDate(binding.textViewDate.text.toString())
            }
            dialogBinding.cardWeek.setOnClickListener {
                dialog.dismiss()
                viewModel.setWeekData()
                val date = getCurrentDateDay().substringBefore(',').toInt() - 7
                val sdf = SimpleDateFormat("$date - dd, MMM yyyy")
                binding.textViewDate.text = sdf.format(Date())
                sharedViewModelForMainChart.saveCurrentDate(binding.textViewDate.text.toString())
            }
            dialogBinding.cardMonth.setOnClickListener {
                dialog.dismiss()
                viewModel.setMonthData()
                val sdf = SimpleDateFormat("MMM yyyy")
                binding.textViewDate.text = sdf.format(Date())
                sharedViewModelForMainChart.saveCurrentDate(binding.textViewDate.text.toString())
            }
            dialogBinding.cardYear.setOnClickListener {
                dialog.dismiss()
                viewModel.setYearData()
                val sdf = SimpleDateFormat("yyyy ГОД")
                binding.textViewDate.text = sdf.format(Date())
                sharedViewModelForMainChart.saveCurrentDate(binding.textViewDate.text.toString())
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
        return sdf.format(Date()).toString()
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






}