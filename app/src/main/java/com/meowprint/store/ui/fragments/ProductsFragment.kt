package com.meowprint.store.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.meowprint.store.api.RetrofitClient
import com.meowprint.store.databinding.FragmentProductsBinding
import com.meowprint.store.model.Product
import com.meowprint.store.adapter.ProductAdapter
import kotlinx.coroutines.launch

class ProductsFragment : Fragment() {
    private var _b: FragmentProductsBinding? = null
    private val b get() = _b!!
    private lateinit var adapter: ProductAdapter
    private var master: List<Product> = emptyList()

    private val api by lazy {
        RetrofitClient.storeRetrofit(requireContext()).create(com.meowprint.store.api.StoreApi::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentProductsBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ProductAdapter(emptyList())
        b.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        b.rvProducts.adapter = adapter

        lifecycleScope.launch {
            try {
                // ✅ No pasamos q=null, solo limit y offset
                master = api.listProducts(limit = 100, offset = 0)
                adapter.submit(master)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al cargar productos: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        b.searchView.setOnQueryTextListener(object: android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query ?: "")
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText ?: "")
                return true
            }
        })
    }

    private fun filterList(q: String) {
        val s = q.trim().lowercase()
        if (s.isEmpty()) {
            adapter.submit(master)
            return
        }
        val filtered = master.filter { p ->
            listOf(p.name, p.brand, p.category, p.description).any {
                (it ?: "").lowercase().contains(s)
            }
        }
        adapter.submit(filtered)
    }

    override fun onDestroyView() {
        _b = null
        super.onDestroyView()
    }
}
