package com.example.hbomax.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hbomax.R
import com.example.hbomax.model.ChildModel
import coil.load

class ChildAdapter(private val childmodel: List<ChildModel>):RecyclerView.Adapter<ChildViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.child_rv_layout, parent,false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.binding.apply {
            ivChildItem.load(childmodel[position].imageUrl)
        }
    }

    override fun getItemCount(): Int {
        return childmodel.size
    }
}