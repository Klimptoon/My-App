package com.example.myfirstapp

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.databinding.RecyclerItemBinding

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    class RecyclerHolder(item : View) : RecyclerView.ViewHolder(item) {
        val binding = RecyclerItemBinding.bind(item)
        fun fin() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}