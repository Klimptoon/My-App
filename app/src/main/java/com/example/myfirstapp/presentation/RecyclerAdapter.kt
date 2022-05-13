package com.example.myfirstapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R
import com.example.myfirstapp.data.PurchaseRepository
import com.example.myfirstapp.databinding.RecyclerItemBinding

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>() {


    val purchaseList = ArrayList<Purchase>()

    class RecyclerHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = RecyclerItemBinding.bind(item)
        fun init(purchase: Purchase) = with(binding) {
            circle.setImageResource(purchase.imageId)
            textRecycler.text = "${purchase.title}"
            textRecycler2.text = "${purchase.cost}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return RecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.init(purchaseList[position])
    }

    override fun getItemCount(): Int {
        return purchaseList.size
    }

    fun addPurchase(purchase: Purchase) {
        purchaseList.add(purchase)
        notifyDataSetChanged()
    }

    fun setData(purchseList: List<Purchase>) {
        purchaseList.clear()
        purchaseList.addAll(purchseList)
        notifyDataSetChanged()
    }




}