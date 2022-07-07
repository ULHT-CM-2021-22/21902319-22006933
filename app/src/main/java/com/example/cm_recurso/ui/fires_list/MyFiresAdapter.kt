package com.example.cm_recurso.ui.fires_list

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cm_recurso.R
import com.example.cm_recurso.model.fire.FireParceLable

class MyFiresAdapter(private val list: List<FireParceLable>, private val listener: FiresListFragment) : RecyclerView.Adapter<MyFiresAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_fire, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        holder.index.text = "Fogo ${position +1 }"
        holder.data.text = "Data: " + currentItem.data
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val index: TextView = itemView.findViewById(R.id.fogoIndex)
        val nome: TextView = itemView.findViewById(R.id.district)
        val data: TextView = itemView.findViewById(R.id.data)

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                //listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}