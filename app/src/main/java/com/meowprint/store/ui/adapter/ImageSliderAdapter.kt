package com.meowprint.store.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meowprint.store.databinding.ItemImageBinding
import com.meowprint.store.model.product.ImageResource


class ImageSliderAdapter(private val images: List<ImageResource>) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(val b: ItemImageBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val rawUrl = images[position].url?.trim()
        val imageUrl = rawUrl?.let {
            if (it.startsWith("http")) it else "https://x8ki-let1-twmt.n7.xano.io$it"
        }

        Log.d("ImageSliderAdapter", "Cargando imagen: $imageUrl")

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .thumbnail(0.1f) // 10% de la imagen original
            .timeout(5000) // ⏱️ máximo 5 segundos de espera
            .into(holder.b.imgSlide)
    }


    override fun getItemCount(): Int = images.size
}
