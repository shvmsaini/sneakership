package io.github.shvmsaini.sneakership.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.github.shvmsaini.sneakership.R
import io.github.shvmsaini.sneakership.adapters.CartAdapter
import io.github.shvmsaini.sneakership.databinding.FragmentCartBinding
import io.github.shvmsaini.sneakership.models.Cart
import io.github.shvmsaini.sneakership.repository.SneakersRepository
import io.github.shvmsaini.sneakership.strategy.DefaultTaxStrategy
import io.github.shvmsaini.sneakership.ui.activities.MainActivity
import io.github.shvmsaini.sneakership.viewmodels.CartViewModel
import io.github.shvmsaini.sneakership.viewmodels.factory.CartViewModelFactory

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private var sneakers = ArrayList<Cart>()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var viewModel: CartViewModel
    private var subTotal: Int = 0
    private var taxStrategy = DefaultTaxStrategy()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar
        with(binding.toolbar) {
            titleToolbar.visibility = View.VISIBLE
            tvName.text = getString(R.string.cart)
            toolbarBackIcon.setOnClickListener {
                (activity as MainActivity).openHomeFragmentFromCart()
            }
        }

        // Adapter
        cartAdapter = CartAdapter(
            requireContext(), sneakers
        ) { item ->
            viewModel.removeFromCart(item)
            updateTotal(item.retailPrice ?: 0)
            checkEmptyView()
        }
        binding.rvCart.adapter = cartAdapter

        viewModel = ViewModelProvider(
            this, CartViewModelFactory(SneakersRepository(requireContext()))
        )[CartViewModel::class.java]
        viewModel.getCart()
        getCartData()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getCartData() {
        viewModel.cart.observe(requireActivity()) {
            sneakers.clear()
            sneakers.addAll(it)
            sneakers.forEach { sneaker -> subTotal += (sneaker.retailPrice ?: 0) }
            cartAdapter.notifyDataSetChanged()
            updateTotal()
            checkEmptyView()
        }
    }

    private fun updateTotal(price: Int = 0) {
        subTotal -= price
        val taxes = (subTotal * taxStrategy.getTaxPercent()) / 100
        with(binding) {
            tvSubTotal.text = getString(R.string.subtotal_price, subTotal.toString())
            tvTaxes.text = getString(R.string.taxes_price, taxes.toString())
            tvTotalPrice.text = getString(R.string.price_x, (subTotal + taxes).toString())
        }
    }

    private fun checkEmptyView() {
        if (sneakers.size == 0) {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.details.visibility = View.GONE
        } else {
            binding.tvEmpty.visibility = View.GONE
            binding.details.visibility = View.VISIBLE
        }
    }
}