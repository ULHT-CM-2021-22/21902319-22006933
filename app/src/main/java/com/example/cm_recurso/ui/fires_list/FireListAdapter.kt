package com.example.cm_recurso.ui.fires_list

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cm_recurso.databinding.ItemFireBinding
import com.example.cm_recurso.model.fire.FireParceLable

class FireListAdapter (
    private var items: List<FireParceLable> = listOf())
    : RecyclerView.Adapter<FireListAdapter.FireListViewHolder>() {
    class FireListViewHolder(val  binding: ItemFireBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FireListViewHolder {
        return FireListViewHolder(
            ItemFireBinding.inflate (
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FireListViewHolder, position: Int) {
        //Preenche cada item
        holder.binding.nome.text = items[position].name
        holder.binding.data.text = items[position].data
    }

    override fun getItemCount() = items.size

    fun updateItems(items: List<FireParceLable>) {
        Log.d(TAG, "AKHBSKASKJA --------------> " + getItemCount())
        this.items = items
        notifyDataSetChanged()
    }
}