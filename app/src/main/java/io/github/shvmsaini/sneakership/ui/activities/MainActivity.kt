package io.github.shvmsaini.sneakership.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import io.github.shvmsaini.sneakership.R
import io.github.shvmsaini.sneakership.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navBar.setBackgroundResource(R.drawable.bottom_tab_background)

        with(binding) {
            ivHome.setOnClickListener {
                val current = findNavController(R.id.navHost).currentDestination?.id
                if (current != R.id.homeFragment) {
                    openHomeFragment()
                }
                switchTab(true)
            }
            ivCart.setOnClickListener {
                switchTab(false)
                val current = findNavController(R.id.navHost).currentDestination?.id
                if (current == R.id.sneakerDetailsFragment) {
                    openCartFromDetails()
                } else if (current != R.id.cartFragment) {
                    openCartFromHome()
                }
            }
        }
    }

    fun openDetailFragment(bundle: Bundle) {
        findNavController(R.id.navHost).navigate(
            R.id.action_homeFragment_to_sneakerDetailsFragment,
            bundle
        )
    }

    fun openHomeFragmentFromDetails() {
        findNavController(R.id.navHost).popBackStack()
    }

    fun openHomeFragmentFromCart() {
        findNavController(R.id.navHost).popBackStack()
    }

    private fun openCartFromHome() {
        findNavController(R.id.navHost).navigate(
            R.id.action_homeFragment_to_cartFragment
        )
    }

    private fun openCartFromDetails() {
        findNavController(R.id.navHost).navigate(
            R.id.action_sneakerDetailsFragment_to_cartFragment
        )
    }

    private fun openHomeFragment() {
        findNavController(R.id.navHost).popBackStack()
    }

    fun switchTab(isHomeVisible: Boolean) {
        if (isHomeVisible) {
            with(binding) {
                home.setBackgroundResource(R.drawable.circular_background)
                cart.background = null
                ivCart.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity, R.color.colorPrimary
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
                ivHome.setColorFilter(
                    ContextCompat.getColor(this@MainActivity, R.color.white),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                )
            }
        } else {
            with(binding) {
                home.background = null
                cart.setBackgroundResource(R.drawable.circular_background)
                ivCart.setColorFilter(
                    ContextCompat.getColor(this@MainActivity, R.color.white),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                )
                ivHome.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity, R.color.colorPrimary
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
            }
        }
    }
}