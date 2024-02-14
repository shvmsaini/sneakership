package io.github.shvmsaini.sneakership.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import io.github.shvmsaini.sneakership.R
import io.github.shvmsaini.sneakership.adapters.ProductAdapter
import io.github.shvmsaini.sneakership.databinding.FragmentHomeBinding
import io.github.shvmsaini.sneakership.models.Cart
import io.github.shvmsaini.sneakership.models.Sneaker
import io.github.shvmsaini.sneakership.repository.SneakersRepository
import io.github.shvmsaini.sneakership.ui.activities.MainActivity
import io.github.shvmsaini.sneakership.viewmodels.HomeViewModel
import io.github.shvmsaini.sneakership.viewmodels.factory.HomeViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var productAdapter: ProductAdapter
    private var sneakers = ArrayList<Sneaker>()
    private var searchedSneakers = ArrayList<Sneaker>()
    private var cart = ArrayList<Cart>()
    private var cartMap = HashMap<String, Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

        setAdapter()

        viewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(SneakersRepository(requireContext()))
        )[HomeViewModel::class.java]
        getSneakers()
    }

    private fun getCartModel(sneaker: Sneaker): Cart {
        return Cart(
            id = sneaker.id,
            name = sneaker.name,
            title = sneaker.title,
            retailPrice = sneaker.retailPrice,
            size = sneaker.size,
            color = sneaker.colorway,
            image = sneaker.media?.imageUrl
        )
    }

    private fun setAdapter() {
        productAdapter =
            ProductAdapter(
                requireContext(), searchedSneakers
            ) { sneaker, isCart ->
                if (isCart) {
                    if (sneaker.addedToCart) {
                        viewModel.addToCart(getCartModel(sneaker))
                    } else {
                        viewModel.removeFromCart(getCartModel(sneaker))
                    }
                } else {
                    val bundle = Bundle()
                    bundle.putSerializable("data", sneaker)
                    (activity as MainActivity).openDetailFragment(bundle)
                }
            }
        binding.rvSneakers.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvSneakers.adapter = productAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getSneakers() {
        viewModel.sneakers.observe(requireActivity()) {
            sneakers.clear()
            searchedSneakers.clear()
            sneakers.addAll(it)
            sneakers.forEach { sneaker ->
                if (cartMap.containsKey(sneaker.id))
                    sneaker.addedToCart = true
            }
            searchedSneakers.addAll(sneakers)
            productAdapter.notifyDataSetChanged()
        }

        viewModel.cart.observe(requireActivity()) {
            cart.clear()
            cart.addAll(it)
            for (item in it) {
                cartMap[item.id!!] = true
            }
            viewModel.getSneakers()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupToolbar() {
        with(binding.toolbar) {
            mainScreen.visibility = View.VISIBLE
            etSearch.doAfterTextChanged {
                searchedSneakers.clear()
                for (item in sneakers) {
                    if (item.name?.contains(it.toString(), true) == true) {
                        Log.i("data_", item.name.toString())
                        searchedSneakers.add(item)
                    }
                }
                if (it.toString().isEmpty()) {
                    searchedSneakers.clear()
                    searchedSneakers.addAll(sneakers)
                }
                checkEmptyView()
                productAdapter.notifyDataSetChanged()
            }
            ivSearch.setOnClickListener {
                with(binding.toolbar) {
                    mainScreen.visibility = View.GONE
                    searchView.visibility = View.VISIBLE
                }
            }
            ivClearAll.setOnClickListener {
                with(binding.toolbar) {
                    etSearch.setText("")
                    mainScreen.visibility = View.VISIBLE
                    searchView.visibility = View.GONE
                }
            }
        }

        binding.tvFilter.setOnClickListener {
            val wrapper: Context = ContextThemeWrapper(context, R.style.PopupMenu)
            val popupMenu = PopupMenu(wrapper, it)
            val inflater: MenuInflater = popupMenu.menuInflater
            inflater.inflate(R.menu.sortby, popupMenu.menu)
            popupMenu.menu[0].setOnMenuItemClickListener {
                // Popular
                binding.tvFilterName.text = getString(R.string.popular)
                true
            }
            popupMenu.menu[1].setOnMenuItemClickListener {
                // Descending
                searchedSneakers.sortByDescending { sneaker ->
                    sneaker.retailPrice ?: 0
                }
                binding.tvFilterName.text = getString(R.string.high_to_low)
                productAdapter.notifyItemRangeChanged(0, searchedSneakers.size)
                true
            }
            popupMenu.menu[2].setOnMenuItemClickListener {
                // Ascending
                searchedSneakers.sortBy { sneaker ->
                    sneaker.retailPrice ?: 0
                }
                productAdapter.notifyItemRangeChanged(0, searchedSneakers.size)
                binding.tvFilterName.text = getString(R.string.low_to_high)
                true
            }
            popupMenu.show()
        }
    }

    private fun checkEmptyView() {
        if (searchedSneakers.isEmpty()) {
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.tvEmpty.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        cartMap.clear()
        viewModel.getCart()
        (activity as MainActivity).switchTab(true)
        checkEmptyView()
    }
}