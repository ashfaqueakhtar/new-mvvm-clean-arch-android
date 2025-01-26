package com.example.mvvmclean.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mvvmclean.R
import com.example.mvvmclean.databinding.FragmentCarListBinding
import com.example.mvvmclean.presentation.list.vm.CarListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CarListFragment : Fragment() {

    private lateinit var binding: FragmentCarListBinding
    private lateinit var viewModel: CarListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        /*val getCarListUseCase =
            GetCarListUseCase(FakeCarRepository()) // Provide your use case instance
        val factory = CartListViewModelFactory(getCarListUseCase)*/
        viewModel = ViewModelProvider(this, /*factory*/)[CarListViewModel::class.java]

        binding = FragmentCarListBinding.inflate(inflater, container, false)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CarAdapter { car ->

            val bundle = Bundle().apply {
                putInt("carId", car.id)
            }

            findNavController().navigate(R.id.action_carListToCarDetail, bundle)

        }
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.carList.collect { cars ->
                    cars?.let {c->
                        adapter.submitList(c)
                    }
                }
            }
        }
    }
}