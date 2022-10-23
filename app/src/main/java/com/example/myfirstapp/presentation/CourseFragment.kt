package com.example.myfirstapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentCourseBinding


class CourseFragment : Fragment() {




    var adapter = CourseAdapter()

    private lateinit var binding : FragmentCourseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this, CourseViewModelFactory(requireContext())).get(CourseViewModel::class.java)
        binding.recyclerCourse.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerCourse.adapter = adapter
        viewModel.getCurrencyCourse()
        viewModel.myCurrencyList.observe(viewLifecycleOwner) {
            adapter.setData(it.currencyList)
        }

        return binding.root
    }

}