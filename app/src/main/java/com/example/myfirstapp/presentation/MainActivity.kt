package com.example.myfirstapp.presentation

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myfirstapp.R
import com.example.myfirstapp.data.PurchaseDatabase
import com.example.myfirstapp.databinding.ActivityMainBinding
import com.example.myfirstapp.databinding.PeriodBinding
import com.example.myfirstapp.databinding.PurchaseInputBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding


    private val adapter = PurchaseAdapter()

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val navController = findNavController(R.id.fragmentContainerView)

        bottomNavView.setupWithNavController(navController)

    }







}