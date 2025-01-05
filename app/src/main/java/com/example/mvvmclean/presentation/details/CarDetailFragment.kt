package com.example.mvvmclean.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.mvvmclean.databinding.FragmentCarDetailBinding
import com.example.mvvmclean.data.repository.FakeCarRepository
import com.example.mvvmclean.domain.usecase.GetCarDetailsUseCase
import com.example.mvvmclean.domain.usecase.UpdateCarDetailsUseCase
import com.example.mvvmclean.presentation.details.vm.CarDetailViewModel
import com.example.mvvmclean.presentation.details.vm.CartDetailsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CarDetailFragment : Fragment() {
    private lateinit var viewModel: CarDetailViewModel
    private lateinit var binding: FragmentCarDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val repo = FakeCarRepository()
        val getCarDetailsUseCase = GetCarDetailsUseCase(repo)
        val updateCarDetailsUseCase = UpdateCarDetailsUseCase(repo)
        val factory = CartDetailsViewModelFactory(getCarDetailsUseCase, updateCarDetailsUseCase)
        viewModel = ViewModelProvider(this/*, factory*/)[CarDetailViewModel::class.java]

        binding = FragmentCarDetailBinding.inflate(inflater, container, false)

        val view: View = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val carId = arguments?.getInt("carId") ?: return

        viewModel.fetchCarDetails(carId)

        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.CREATED) {

                ////
                viewModel.carDetails.collect { car ->
                    car?.let { c ->
                        binding.carName.text = c.name
                        binding.carBrand.text = c.brand
                        binding.carPrice.text = c.price
                        Glide.with(binding.carImage.context).load(c.imageUrl)
                            .into(binding.carImage)

                        binding.editButton.setOnClickListener {
                            val editDialog = EditCarDialogFragment.newInstance(c)
                            editDialog.updateData {
                                viewModel.updateCarDetails(it)
                            }
                            editDialog.show(parentFragmentManager, "EditCarDialog")
                        }
                    }
                }
                ////

            }

        }
    }
}