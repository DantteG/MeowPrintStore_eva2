package com.meowprint.store.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.meowprint.store.R // <-- THIS IS THE FIX: Import your project's R class
import com.meowprint.store.databinding.ActivityHomeBinding
import com.meowprint.store.ui.fragments.AddProductFragment
import com.meowprint.store.ui.fragments.ProductsFragment
import com.meowprint.store.ui.fragments.ProfileFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var b: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(b.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                // The 'R' reference will now be correctly resolved
                replace(R.id.fragmentContainer, ProfileFragment())
            }
        }

        b.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                // All 'R' references are now fixed
                R.id.nav_profile -> {
                    supportFragmentManager.commit { replace(R.id.fragmentContainer, ProfileFragment()) }
                    true
                }
                R.id.nav_products -> {
                    supportFragmentManager.commit { replace(R.id.fragmentContainer, ProductsFragment()) }
                    true
                }
                R.id.nav_add -> {
                    supportFragmentManager.commit { replace(R.id.fragmentContainer, AddProductFragment()) }
                    true
                }
                else -> false
            }
        }
    }
}
