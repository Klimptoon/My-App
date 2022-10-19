package com.example.myfirstapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.ItemCourseBinding
import kotlinx.android.synthetic.main.item_course.view.*

//class CourseAdapter : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
//
//    var listOfCourseCurrency = mutableListOf<Currency>()
//
//
//    class CourseViewHolder(view : View) : RecyclerView.ViewHolder(view) {
//        val binding = ItemCourseBinding.bind(view)
//        fun bind(data : Currency) {
//            binding.tvCurrencyName.text = data.rates[position]
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
//        return CourseViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
//
//    }
//
//    override fun getItemCount(): Int {
//        return listOfCourseCurrency.size
//    }
//
//}