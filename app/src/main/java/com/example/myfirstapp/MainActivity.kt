package com.example.myfirstapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfirstapp.databinding.ActivityMainBinding
import com.example.myfirstapp.databinding.PurchaseInputBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private val adapter = RecyclerAdapter()
    private val imageIdList = listOf(R.drawable.circle_shape_red
        , R.drawable.circle_shape_blue
        , R.drawable.circle_shape_green
        , R.drawable.circle_shape_yellow
    )
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
        binding.apply {
            rv.layoutManager = GridLayoutManager(this@MainActivity, 1)
            rv.adapter = adapter
            buttonAdd.setOnClickListener {
                if(counter > 3) counter = 0
                val purchase = Purchase(imageIdList[counter], "Овощь", "Помидор", 12.0)
                adapter.addPurchase(purchase)
                counter++
            }
        }
    }

    private fun showAlertDialog() {
        val dialogBinding = PurchaseInputBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.info)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.confirm, null)
            .create()
        dialog.setOnShowListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val enteredText = dialogBinding.etName.text.toString()
                val cost = dialogBinding.etCost.text.toString()
                val enteredText2 = cost.toIntOrNull()
                if(enteredText2 == null || enteredText2 < 0) {
                    dialogBinding.etCost.error = getString(R.string.empty)
                    return@setOnClickListener
                }
                if (enteredText.isBlank() ) {
                    dialogBinding.etName.error = getString(R.string.empty)
                    return@setOnClickListener
                }
                this.binding.textView.text = enteredText
                dialog.dismiss()
            }

        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()
    }
}