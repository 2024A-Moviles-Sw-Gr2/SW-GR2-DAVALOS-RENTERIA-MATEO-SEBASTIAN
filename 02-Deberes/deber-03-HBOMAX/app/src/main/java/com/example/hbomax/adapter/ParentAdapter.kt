package com.example.hbomax.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hbomax.R
import com.example.hbomax.model.ParentModel

class ParentAdapter (private val parentmodel: List<ParentModel>): RecyclerView.Adapter<ParentViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.parent_rv_layout,parent,false)
        return ParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.binding.apply {
            val parentmodel = parentmodel[position]
            tvTitleMovie.text =parentmodel.title
            val childAdapter = ChildAdapter(parentmodel.childModels)
            rvChild.adapter=childAdapter
        }
    }

    override fun getItemCount(): Int {
       return parentmodel.size
    }
}