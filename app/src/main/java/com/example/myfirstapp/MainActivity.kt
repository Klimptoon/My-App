package com.example.myfirstapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myfirstapp.databinding.ActivityMainBinding
import com.example.myfirstapp.databinding.PeriodPickerBinding
import com.example.myfirstapp.databinding.PurchaseInputBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding


    var imageId : Int = 0
    var type : String = ""
    var title : String = ""
    var costPurchase: String = ""




    private val adapter = RecyclerAdapter()
//    private val imageIdList = listOf(R.drawable.circle_shape_red
//        , R.drawable.circle_shape_blue
//        , R.drawable.circle_shape_green
//        , R.drawable.circle_shape_yellow
//    )
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        startMainSpinner()

        binding.buttonAdd.setOnClickListener {
            showAlertDialog()
        }
        binding.textViewDate.text = getCurrentDateDay()

        binding.alertToolBar.setOnClickListener {
            showAlertDialogForTime()
        }



    }



    private fun startMainSpinner() {
        val purchaseTypes = resources.getStringArray(R.array.types)
        val arrayAdapter = ArrayAdapter(this@MainActivity, R.layout.dropdown_item, purchaseTypes)
        binding.autoCompleteTextViewMain.setAdapter(arrayAdapter)
    }

    private fun getCurrentDateDay():String{
        val sdf = SimpleDateFormat("dd, MMM yyyy")
        return sdf.format(Date())
    }





    private fun showAlertDialogForTime() {
        val dialogBinding = PeriodPickerBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()
        dialog.setOnShowListener {
            dialogBinding.buttonToday.setOnClickListener {
                dialog.dismiss()
                binding.textViewDate.text = getCurrentDateDay()
            }
            dialogBinding.buttonWeek.setOnClickListener {
                dialog.dismiss()
                val date = getCurrentDateDay().substringBefore(',').toInt() + 7
                val sdf = SimpleDateFormat("dd - $date , MMM yyyy")
                binding.textViewDate.text = sdf.format(Date())
            }
            dialogBinding.buttonMonth.setOnClickListener {
                dialog.dismiss()
                val sdf = SimpleDateFormat("MMM yyyy")
                binding.textViewDate.text = sdf.format(Date())
            }
            dialogBinding.buttonYear.setOnClickListener {
                dialog.dismiss()
                val sdf = SimpleDateFormat("yyyy ГОД")
                binding.textViewDate.text = sdf.format(Date())
            }

        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()
    }

    private fun showAlertDialog() {
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
                if(enteredText2 == null || enteredText2 < 0) {
                    dialogBinding.etCost.error = getString(R.string.empty)
                    return@setOnClickListener
                }
                if (enteredText.isBlank() ) {
                    dialogBinding.etName.error = getString(R.string.empty)
                    return@setOnClickListener
                }
                type = dialogBinding.autoCompleteTextView.text.toString()
                title = dialogBinding.etName.text.toString()
                costPurchase = dialogBinding.etCost.text.toString()
                dialog.dismiss()
                binding.apply {
                    rv.layoutManager = GridLayoutManager(this@MainActivity, 1)
                    rv.adapter = adapter
                        if(counter > 3) counter = 0
                        val purchase = Purchase(when(type) {
                            "Еда" -> R.drawable.circle_shape_red
                            "Одежда" -> R.drawable.circle_shape_blue
                            "Развлечения" -> R.drawable.circle_shape_green
                            "Дом" -> R.drawable.circle_shape_yellow
                            else -> R.drawable.circle_shape_blue
                                                           }, type, title, costPurchase)
                        adapter.addPurchase(purchase)
                        counter++
                }
            }

        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()

    }
}