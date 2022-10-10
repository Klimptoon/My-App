package com.example.myfirstapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.RecyclerItemBinding

class PurchaseAdapter : RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder>() {


    val purchaseList = ArrayList<Purchase>()

    class PurchaseViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = RecyclerItemBinding.bind(item)
        fun init(purchase: Purchase) = with(binding) {
            circle.setImageResource(purchase.imageId)
            textRecycler.text = "${purchase.title}"
            textRecycler2.text = "${purchase.cost}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return PurchaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        holder.init(purchaseList[position])
    }

    override fun getItemCount(): Int {
        return purchaseList.size
    }

    fun addPurchase(purchase: Purchase) {
        purchaseList.add(purchase)
        purchaseList
        notifyDataSetChanged()
    }

    fun setData(purchseList: List<Purchase>) {
        purchaseList.clear()
        purchaseList.addAll(purchseList)
        notifyDataSetChanged()
    }




}