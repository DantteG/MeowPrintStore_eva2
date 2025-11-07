package com.meowprint.store.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.meowprint.store.R
import com.meowprint.store.databinding.FragmentDisplayItemBinding

class DisplayItemFragment : Fragment() {

    private var _binding: FragmentDisplayItemBinding? = null
    private val binding get() = _binding!!

    // Variables para recibir los datos
    private var productName: String? = null
    private var productPrice: Float? = null
    private var productStock: Int? = null
    private var productImageUrl: String? = null
    private var productDescription: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Extraer datos del Bundle
        arguments?.let { bundle ->
            productName = bundle.getString("name")
            productPrice = bundle.getFloat("price", 0f)
            productStock = bundle.getInt("stock", 0)
            productImageUrl = bundle.getString("imageUrl")
            productDescription = bundle.getString("description")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDisplayItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Mostrar datos en la UI
        binding.tvProductName.text = productName ?: "Sin nombre"
        binding.tvProductPrice.text = "$${productPrice ?: 0f}"
        binding.tvProductStock.text = "Stock: ${productStock ?: 0}"
        binding.tvProductDescription.text = productDescription ?: "Sin descripción"

        Glide.with(requireContext())
            .load(productImageUrl)
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_error)
            .into(binding.imgProduct)

        binding.btnAddToCart.isEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
