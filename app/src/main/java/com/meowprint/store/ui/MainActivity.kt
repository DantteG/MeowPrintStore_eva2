package com.meowprint.store.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.meowprint.store.api.AuthApi
import com.meowprint.store.api.RetrofitClient
import com.meowprint.store.api.StoreApi
import com.meowprint.store.api.TokenManager
import com.meowprint.store.databinding.ActivityMainBinding
import com.meowprint.store.model.LoginRequest
import com.meowprint.store.session.SessionManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var tm: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        tm = TokenManager(this)

        val token = tm.getToken()
        val role = SessionManager.getRole(this)

        if (!token.isNullOrBlank() && !role.isNullOrBlank()) {
            goHome(role)
            return
        }

        b.btnLoginClient.setOnClickListener { loginClient() }
        b.btnLoginAdmin.setOnClickListener { promptAdminPassword() }
    }

    private fun loginClient() {
        val email = b.etEmail.text?.toString()?.trim() ?: ""
        val password = b.etPassword.text?.toString()?.trim() ?: ""

        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Completa email y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val apiAuth = RetrofitClient.userRetrofit(this@MainActivity).create(AuthApi::class.java)
                val loginResponse = apiAuth.login(LoginRequest(email, password))

                val token = loginResponse.token ?: loginResponse.authToken
                if (token.isNullOrBlank()) {
                    Toast.makeText(this@MainActivity, "No se recibió token válido", Toast.LENGTH_LONG).show()
                    return@launch
                }
                tm.saveToken(token)

                val apiUser = RetrofitClient.userRetrofit(this@MainActivity).create(StoreApi::class.java)
                val user = apiUser.getMe()

                tm.saveUser(user?.name, user?.email)

                SessionManager.saveLogin(this@MainActivity, "CLIENTE")
                startActivity(Intent(this@MainActivity, ClientActivity::class.java))
                finish()

            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error de login: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun promptAdminPassword() {
        val input = android.widget.EditText(this).apply {
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        android.app.AlertDialog.Builder(this)
            .setTitle("Contraseña de administrador")
            .setView(input)
            .setPositiveButton("Acceder") { _, _ ->
                val password = input.text.toString()
                if (password == "1111") {
                    SessionManager.saveLogin(this, "ADMIN")
                    startActivity(Intent(this, AdminActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun goHome(role: String) {
        when (role.uppercase()) {
            "ADMIN" -> startActivity(Intent(this, AdminActivity::class.java))
            else -> startActivity(Intent(this, ClientActivity::class.java))
        }
        finish()
    }
}
