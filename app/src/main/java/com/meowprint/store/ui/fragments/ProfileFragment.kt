package com.meowprint.store.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.meowprint.store.api.TokenManager
import com.meowprint.store.databinding.FragmentProfileBinding
import com.meowprint.store.ui.MainActivity

class ProfileFragment : Fragment() {
    private var _b: FragmentProfileBinding? = null
    private val b get() = _b!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _b = FragmentProfileBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tm = TokenManager(requireContext())

        // Mostrar datos del usuario
        b.tvWelcome.text = "¡Bienvenido, ${tm.getUserName()}!"
        b.tvEmail.text = tm.getUserEmail()

        // Lógica de cierre de sesión
        b.btnLogout.setOnClickListener {
            tm.clear()
            Toast.makeText(requireContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show()

            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        _b = null
        super.onDestroyView()
    }
}
