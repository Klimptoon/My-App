package com.example.myfirstapp.presentation

import android.content.Context
import android.os.Bundle
import android.util.AndroidRuntimeException
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentCourseBinding
import com.example.myfirstapp.network.ConnectionInternetLiveData


class CourseFragment : Fragment() {


    var adapter = CourseAdapter()
    private lateinit var cld : ConnectionInternetLiveData
    private lateinit var binding : FragmentCourseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentCourseBinding.inflate(inflater, container, false)
        //checkInternetConnection()
        try {
            init()
        }
        catch (exception : RuntimeException) {
            binding.layout1.visibility = View.GONE
            binding.layout2.visibility = View.VISIBLE
        }


        return binding.root
    }


    fun init() {
        val viewModel = ViewModelProvider(this, CourseViewModelFactory(requireContext())).get(CourseViewModel::class.java)
        binding.recyclerCourse.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerCourse.adapter = adapter
        viewModel.getCurrencyCourse()
        viewModel.myCurrencyList.observe(viewLifecycleOwner) {
            adapter.setData(it.currencyList)
        }
    }

//    private fun checkInternetConnection(){
//        cld = ConnectionInternetLiveData(requireActivity().application)
//
//        cld.observe(requireActivity()) { isConnected ->
//            if (isConnected) {
//
//                binding.layout1.visibility = View.VISIBLE
//                binding.layout2.visibility = View.GONE
//            } else {
//                binding.layout1.visibility = View.GONE
//                binding.layout2.visibility = View.VISIBLE
//            }
//        }
//
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("mm", "Вызвался: onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("mm", "Вызвался: onCreate")
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("mm", "Вызвался: onViewCreated")
    }



    override fun onStart() {
        super.onStart()
        Log.d("mm", "Вызвался: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("mm", "Вызвался: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("mm", "Вызвался: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("mm", "Вызвался: onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("mm", "Вызвался: onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("mm", "Вызвался: onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("mm", "Вызвался: onDetach")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("mm", "Вызвался: onSaveInstanceState")
    }

}