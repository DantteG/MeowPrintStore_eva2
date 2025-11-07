package com.meowprint.store.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meowprint.store.R
import com.meowprint.store.databinding.ItemProductBinding
import com.meowprint.store.model.Product
import com.meowprint.store.ui.fragments.DisplayItemFragment
import android.os.Bundle


class ProductAdapter(private var items: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val b: ItemProductBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = items[position]


        // Asignar texto
        holder.b.tvTitle.text = product.name
        holder.b.tvPrice.text = "$${product.price}"

        // Obtener la URL de la primera imagen (si existe)
        val rawUrl = product.images?.firstOrNull()?.url?.trim()
        val imageUrl = rawUrl?.let {
            if (it.startsWith("http")) it else "https://x8ki-let1-twmt.n7.xano.io$it"
        }
        Log.d("ProductAdapter", "imageUrl: $imageUrl")

        // Cargar imagen con Glide
        if (!imageUrl.isNullOrBlank()) {
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .into(holder.b.imgCover)
        } else {
            holder.b.imgCover.setImageResource(R.drawable.image_error)
        }

        // Navegación manual al fragmento DisplayItemFragment
        holder.itemView.setOnClickListener {
            val fragment = DisplayItemFragment()

            val bundle = Bundle().apply {
                putString("name", product.name)
                putFloat("price", product.price.toFloat())
                putInt("stock", product.stock)
                putString("imageUrl", product.images?.firstOrNull()?.url ?: "")
                putString("description", product.description ?: "")
            }

            fragment.arguments = bundle

            val fragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int = items.size

    fun submit(newItems: List<Product>) {
        items = newItems
        notifyDataSetChanged()
    }
}
