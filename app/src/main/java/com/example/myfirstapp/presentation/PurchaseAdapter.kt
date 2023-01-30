package com.example.myfirstapp.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.RecyclerItemBinding
import kotlinx.android.synthetic.main.recycler_item.view.*

class PurchaseAdapter(private val listener : PurchaseAdapterListener)
    : RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder>() {


    private val purchaseList = ArrayList<Purchase>()




    class PurchaseViewHolder(item: View) : RecyclerView.ViewHolder(item)     {
        private val binding = RecyclerItemBinding.bind(item)
        fun init(purchase: Purchase, listener : PurchaseAdapterListener) = with(binding) {
            circle.setImageResource(purchase.imageId)
            textRecycler.text = "${purchase.title}"
            textRecycler2.text = "${purchase.cost}"
            ivDelete.setOnClickListener {
                listener.onPurchaseDelete(purchase)
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)

        return PurchaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        holder.init(purchaseList[position] ,  listener)


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

    fun deletePurchase(purchase : Purchase) {
        val index = purchaseList.indexOf(purchase)
        purchaseList.removeAt(index)
        notifyItemRemoved(index)
    }



}