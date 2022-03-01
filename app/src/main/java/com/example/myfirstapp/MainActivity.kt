package com.example.myfirstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfirstapp.databinding.ActivityMainBinding

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
        start()
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
}