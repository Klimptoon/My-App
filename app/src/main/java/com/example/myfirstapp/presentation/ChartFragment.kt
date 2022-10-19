package com.example.myfirstapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentChartBinding
import com.example.myfirstapp.databinding.FragmentCourseBinding



class ChartFragment : Fragment() {
    private lateinit var binding : FragmentChartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChartBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
}