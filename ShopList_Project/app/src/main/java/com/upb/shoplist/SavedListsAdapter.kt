package com.upb.shoplist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SavedListsAdapter(
    private val lists: List<ShoppingList>,
    private val onClick: (ShoppingList) -> Unit
) : RecyclerView.Adapter<SavedListsAdapter.SavedListViewHolder>() {

    inner class SavedListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvSavedListName)
        private val tvMeta: TextView = itemView.findViewById(R.id.tvSavedListMeta)

        fun bind(list: ShoppingList) {
            val total = list.products.sumOf { it.price }
            tvName.text = list.name
            tvMeta.text = "${list.products.size} productos · Total: $ $total"
            itemView.setOnClickListener { onClick(list) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_saved_list, parent, false)
        return SavedListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedListViewHolder, position: Int) {
        holder.bind(lists[position])
    }

    override fun getItemCount(): Int = lists.size
}