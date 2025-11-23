package com.meowprint.store.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.meowprint.store.databinding.FragmentAddProductBinding

class AddProductFragment : Fragment() {

    private var _b: FragmentAddProductBinding? = null
    private val b get() = _b!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentAddProductBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.btnManagePurchases.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(com.meowprint.store.R.id.fragmentContainer, ManagePurchasesFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        _b = null
        super.onDestroyView()
    }
}
