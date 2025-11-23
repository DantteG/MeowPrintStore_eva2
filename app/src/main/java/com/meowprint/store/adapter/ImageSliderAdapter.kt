package com.meowprint.store.adapter

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
        val imageUrl = images[position].url?.trim()

        Log.d("ImageSliderAdapter", "Cargando imagen: $imageUrl")

        if (!imageUrl.isNullOrBlank()) {
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .thumbnail(0.1f)
                .timeout(5000)
                .into(holder.b.imgSlide)
        } else {
            holder.b.imgSlide.setImageResource(android.R.drawable.ic_menu_report_image)
        }
    }


    override fun getItemCount(): Int = images.size
}
