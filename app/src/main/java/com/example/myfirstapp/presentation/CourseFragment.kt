package com.example.myfirstapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myfirstapp.databinding.FragmentCourseBinding


class CourseFragment : Fragment() {


    var adapter = CourseAdapter()
    private lateinit var binding: FragmentCourseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCourseBinding.inflate(inflater, container, false)
        init()
        binding.buttonTryAgain.setOnClickListener {
            init()
        }
        return binding.root
    }


    fun init() {
        val viewModel = ViewModelProvider(
            this,
            CourseViewModelFactory()
        ).get(CourseViewModel::class.java)
        binding.recyclerCourse.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerCourse.adapter = adapter
        viewModel.getCurrencyCourse()
        viewModel.myCurrencyList.observe(viewLifecycleOwner) {
            adapter.setData(it.currencyList)
        }
        viewModel.isConnected.observe(viewLifecycleOwner) {
            if (it == 0) {
                binding.layout1.visibility = View.GONE
                binding.layout2.visibility = View.VISIBLE
            } else {
                binding.layout1.visibility = View.VISIBLE
                binding.layout2.visibility = View.GONE
            }
        }
    }
}