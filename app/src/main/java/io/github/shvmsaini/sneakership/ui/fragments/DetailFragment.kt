package io.github.shvmsaini.sneakership.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import io.github.shvmsaini.sneakership.R
import io.github.shvmsaini.sneakership.adapters.ColorAdapter
import io.github.shvmsaini.sneakership.adapters.SizeAdapter
import io.github.shvmsaini.sneakership.databinding.FragmentDetailBinding
import io.github.shvmsaini.sneakership.models.Cart
import io.github.shvmsaini.sneakership.models.Sneaker
import io.github.shvmsaini.sneakership.repository.SneakersRepository
import io.github.shvmsaini.sneakership.ui.activities.MainActivity
import io.github.shvmsaini.sneakership.viewmodels.DetailsViewModel
import io.github.shvmsaini.sneakership.viewmodels.factory.DetailsViewModelFactory

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private var addedToCart: Boolean = false
    private lateinit var sneaker: Sneaker
    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this, DetailsViewModelFactory(SneakersRepository(requireContext()))
        )[DetailsViewModel::class.java]

        with(binding.toolbar) {
            titleToolbar.visibility = View.VISIBLE
            title.visibility = View.GONE
            tvName.visibility = View.GONE
            toolbarBackIcon.setOnClickListener {
                (activity as MainActivity).openHomeFragmentFromDetails()
            }
        }

        binding.sneakerDetailsCard.setBackgroundResource(R.drawable.bottom_tab_background)

        sneaker = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("data", Sneaker::class.java)!!
        } else {
            arguments?.getSerializable("data") as Sneaker
        }

        addedToCart = sneaker.addedToCart
        setSizeAdapter(sneaker.size ?: 0)
        setColorAdapter(sneaker.colorway ?: "")

        binding.tvAddToCart.setOnClickListener {
            addedToCart = !addedToCart
            if (!addedToCart) {
                viewModel.removeFromCart(getCart(sneaker))
            } else {
                viewModel.addToCart(getCart(sneaker))
            }
            setupCartButtonUI()
        }

        setupCartButtonUI()
        setData()
    }

    private fun setData() {
        binding.tvSneakerName.text = sneaker.name
        binding.tvTitle.text = sneaker.title
        binding.tvPrice.text =
            getString(R.string.price_x, (sneaker.retailPrice ?: 0).toString())

        Glide.with(requireContext())
            .load(sneaker.media?.imageUrl)
            .placeholder(R.drawable.shoes)
            .into(binding.ivSneaker)
    }

    private fun setSizeAdapter(size: Int) {
        val arr = arrayListOf(7, 8, 9)
        binding.rvSize.adapter = SizeAdapter(requireContext(), arr, size)
    }

    private fun setColorAdapter(color: String) {
        val arr = arrayListOf("Blue", "Red", "Black")
        binding.rvColor.adapter = ColorAdapter(requireContext(), arr, color)
    }

    private fun getCart(item: Sneaker): Cart {
        return Cart(
            id = item.id,
            name = item.name,
            title = item.title,
            retailPrice = item.retailPrice,
            size = item.size,
            color = item.colorway,
            image = item.media?.imageUrl
        )
    }

    private fun setupCartButtonUI() {
        binding.tvAddToCart.isSelected = !addedToCart
        binding.tvAddToCart.text =
            if (!addedToCart) getString(R.string.add_to_cart) else getString(R.string.remove_cart)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).switchTab(true)
    }
}