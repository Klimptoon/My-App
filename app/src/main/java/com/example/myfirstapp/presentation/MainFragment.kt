package com.example.myfirstapp.presentation

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myfirstapp.R
import com.example.myfirstapp.data.PurchaseDatabase
import com.example.myfirstapp.databinding.CustomDialogDeleteBinding
import com.example.myfirstapp.databinding.FragmentMainBinding
import com.example.myfirstapp.databinding.PeriodBinding
import com.example.myfirstapp.databinding.PurchaseInputBinding
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment(), PurchaseAdapterListener {

    private lateinit var searchView: androidx.appcompat.widget.SearchView
    lateinit var binding: FragmentMainBinding
    lateinit var appDb: PurchaseDatabase
    lateinit var viewModel: MainViewModel
    private val sharedViewModelForMainChart: SharedViewModelForMainChart by activityViewModels()


    var type: String = ""
    var title: String = ""
    var costPurchase: String = ""

    private val adapter = PurchaseAdapter(this)

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    override fun onResume() {
        super.onResume()
        val purchaseTypes = resources.getStringArray(R.array.types)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, purchaseTypes)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.autoCompleteTextViewMain.setAdapter(arrayAdapter)
    }


    private fun init() {
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(requireContext())
        ).get(MainViewModel::class.java)
        binding.rv.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.rv.adapter = adapter
        appDb = PurchaseDatabase.getInstance(requireContext())
        sharedViewModelForMainChart.saveCurrentDate(getCurrentDateDay())

        binding.textViewDate.text = viewModel.date
        viewModel.setStartData()
        Log.d("mm", viewModel.setStartData().toString())




        viewModel.startListLiveData.observe(viewLifecycleOwner) {                                   //для вывода списка после открытия приложения
            if (it != null) {
                adapter.setData(it)
            }
        }
        setUpSearchView()



        startMainSpinner()                 //запуск спинера с выбором покупки(еда , дом и т.д)

        binding.buttonAdd.setOnClickListener {
            showAlertDialog()
        }


        binding.alertToolBar.setOnClickListener {
            showAlertDialogForTime()
        }
    }

    private fun startMainSpinner() {                                   //Функция для выбора типа покупки
        val purchaseTypes = resources.getStringArray(R.array.types)
        binding.autoCompleteTextViewMain.onItemClickListener =
            object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    item_id: Long
                ) {
                    viewModel.position = position
                    viewModel.type.value = binding.autoCompleteTextViewMain.text.toString()
                    viewModel.connectSortsByTypeAndTime(purchaseTypes)

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
                viewModel.saveCurrentDate(binding.textViewDate.text.toString())
                sharedViewModelForMainChart.saveCurrentDate(binding.textViewDate.text.toString())
            }
            dialogBinding.cardWeek.setOnClickListener {
                dialog.dismiss()
                viewModel.setWeekData()
                val date = getCurrentDateDay().substringBefore(',').toInt() - 7
                val sdf = SimpleDateFormat("$date - dd, MMM yyyy")
                binding.textViewDate.text = sdf.format(Date())
                viewModel.saveCurrentDate(binding.textViewDate.text.toString())
                sharedViewModelForMainChart.saveCurrentDate(binding.textViewDate.text.toString())
            }
            dialogBinding.cardMonth.setOnClickListener {
                dialog.dismiss()
                viewModel.setMonthData()
                val sdf = SimpleDateFormat("MMM yyyy")
                binding.textViewDate.text = sdf.format(Date())
                viewModel.saveCurrentDate(binding.textViewDate.text.toString())
                sharedViewModelForMainChart.saveCurrentDate(binding.textViewDate.text.toString())
            }
            dialogBinding.cardYear.setOnClickListener {
                dialog.dismiss()
                viewModel.setYearData()
                val sdf = SimpleDateFormat("yyyy ГОД")
                binding.textViewDate.text = sdf.format(Date())
                viewModel.saveCurrentDate(binding.textViewDate.text.toString())
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


    private fun getCurrentDateDayForPurchase(): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        return sdf.format(Date())
    }

    private fun getCurrentDateDay(): String {                         // Функция для получения даты
        val sdf = SimpleDateFormat("dd, MMM yyyy")
        return sdf.format(Date()).toString()
    }


    private fun showAlertDialog() {                                            //Алерт диалог с ресайклером
        val dialogBinding = PurchaseInputBinding.inflate(layoutInflater)

        val purchaseTypes = resources.getStringArray(R.array.types2)
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
                        id = 0,
                        when (type) {
                            "Еда" -> R.drawable.ic_food_24
                            "Одежда" -> R.drawable.shirt
                            "Развлечения" -> R.drawable.party
                            "Дом" -> R.drawable.good_home
                            "Отдых" -> R.drawable.rest
                            else -> R.drawable.ic_else
                        }, type, title, costPurchase, date = getCurrentDateDayForPurchase()
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

    override fun onPurchaseDelete(purchase: Purchase) {

        val dialogBinding = CustomDialogDeleteBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(
            getDrawable(
                requireContext(),
                R.drawable.background_alert_delete
            )
        )
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setOnShowListener {
            dialogBinding.buttonNo.setOnClickListener {
                dialog.dismiss()

            }
            dialogBinding.buttonYes.setOnClickListener {
                dialog.dismiss()
                viewModel.deletePurchase(purchase)
                adapter.deletePurchase(purchase)
            }

        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()
    }

    private fun filterList(query: String?, list: List<Purchase>) {
        if (query != null) {
            val filteredList = mutableListOf<Purchase>()
            for (purchase in list) {
                if (purchase.title.lowercase().contains(query.lowercase())) {
                    filteredList.add(purchase)
                }
            }
            if (filteredList.isNotEmpty()) {
                adapter.setData(filteredList)
            }
        }
    }

    private fun setUpSearchView() {
        searchView = binding.search
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.startListLiveData.value?.let { filterList(newText, it) }
                return true
            }

        })
    }
}