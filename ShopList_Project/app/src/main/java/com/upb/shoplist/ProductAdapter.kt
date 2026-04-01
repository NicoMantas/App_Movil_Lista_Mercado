package com.upb.shoplist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val products: MutableList<Product>,
    private val onDeleteClick: (Product) -> Unit,
    private val onCheckChange: (Product, Boolean) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        private val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        private val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val cbPurchased: CheckBox = itemView.findViewById(R.id.cbPurchased)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteProduct)

        fun bind(product: Product) {
            tvProductName.text = product.name
            tvCategory.text = product.category
            tvQuantity.text = "Cantidad: ${product.quantity}"
            tvPrice.text = if (product.price > 0) "$ ${product.price}" else "-"
            cbPurchased.isChecked = product.isPurchased

            cbPurchased.setOnCheckedChangeListener { _, isChecked ->
                product.isPurchased = isChecked
                onCheckChange(product, isChecked)
            }

            btnDelete.setOnClickListener {
                onDeleteClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun removeProduct(product: Product) {
        val index = products.indexOf(product)
        if (index >= 0) {
            products.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun addProduct(product: Product) {
        products.add(product)
        notifyItemInserted(products.size - 1)
    }
}
