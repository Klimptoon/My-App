package com.example.myfirstapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myfirstapp.databinding.ActivityMainBinding
import com.example.myfirstapp.databinding.PurchaseInputBinding

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

        binding.buttonAdd.setOnClickListener {
            showAlertDialog()
        }

    }



    private fun start() {

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