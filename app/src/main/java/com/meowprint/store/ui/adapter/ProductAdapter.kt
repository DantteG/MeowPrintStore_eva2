package com.meowprint.store.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meowprint.store.databinding.ItemProductBinding
import com.meowprint.store.model.product.Product

class ProductAdapter(private var items: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val b: ItemProductBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = items[position]
        holder.b.tvTitle.text = product.name
        holder.b.tvPrice.text = "$${product.price}"
        val imageUrl = product.images?.firstOrNull()?.url
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.b.imgCover)
    }

    override fun getItemCount(): Int = items.size

    fun submit(newItems: List<Product>) {
        items = newItems
        notifyDataSetChanged()
    }
}
