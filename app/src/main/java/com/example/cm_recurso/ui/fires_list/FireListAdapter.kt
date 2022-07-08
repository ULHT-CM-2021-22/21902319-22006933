package com.example.cm_recurso.ui.fires_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cm_recurso.databinding.ItemFireBinding
import com.example.cm_recurso.model.fire.FireParceLable

class FireListAdapter (
    private var items: List<FireParceLable> = listOf(),
    private val onClick: (FireParceLable) -> Unit,)
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
        holder.binding.firestatus.text = items[position].status
        holder.binding.firesconcelho.text = items[position].conselho
        holder.binding.firesfreguesia.text = items[position].frequesia
        holder.binding.data.text = items[position].data
        holder.binding.hora.text = items[position].hora

        holder.binding.singleFire.setOnClickListener{ onClick(items[position]) }
    }

    override fun getItemCount() = items.size

    fun updateItems(items: List<FireParceLable>) {
        this.items = items
        notifyDataSetChanged()
    }
}