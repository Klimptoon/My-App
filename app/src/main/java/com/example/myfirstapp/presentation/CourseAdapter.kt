package com.example.myfirstapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.ItemCourseBinding
import com.example.myfirstapp.network.Currency
import kotlinx.android.synthetic.main.item_course.view.*

class CourseAdapter : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    var currencyList = mutableListOf<Currency>()


    class CourseViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemCourseBinding.bind(item)
        fun bind(currency : Currency) = with(binding) {
            tvCurrencyName.text = "${currency.charCode} - ${currency.name}"
            tvCurrencyQuantity.text = currency.nominal.toString()
            tvCurrencyCost.text = "${currency.value} российских рублей "
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(currencyList[position])
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    fun setData(currencyListGet: List<Currency>) {
        currencyList.addAll(currencyListGet)
        notifyDataSetChanged()
    }

}