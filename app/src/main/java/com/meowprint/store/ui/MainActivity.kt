package com.meowprint.store.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.meowprint.store.api.AuthApi
import com.meowprint.store.api.RetrofitClient
import com.meowprint.store.api.TokenManager
import com.meowprint.store.databinding.ActivityMainBinding
import com.meowprint.store.model.LoginRequest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var tm: TokenManager
    private val authApi by lazy { RetrofitClient.authRetrofit().create(AuthApi::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        tm = TokenManager(this)

        // ✅ Verifica si ya hay sesión activa
        val token = tm.getToken()
        if (!token.isNullOrBlank()) {
            goHome()
            return
        }

        // 🔘 Botón de login
        b.btnLogin.setOnClickListener {
            val email = b.etEmail.text.toString().trim()
            val pass = b.etPassword.text.toString().trim()
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa email y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            doLogin(email, pass)
        }
    }

    private fun doLogin(email: String, password: String) {
        b.progress.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val resp = authApi.login(LoginRequest(email, password))
                val token = resp.authToken ?: resp.token
                if (token.isNullOrBlank()) throw Exception("No se recibió token")
                tm.saveToken(token)
                tm.saveUser(resp.user?.name ?: email.substringBefore("@"), resp.user?.email ?: email)
                goHome()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Login fallido: Error en el Email o Password, Verifique sus credenciales", Toast.LENGTH_LONG).show()
            } finally {
                b.progress.visibility = View.GONE
            }
        }
    }

    private fun goHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
