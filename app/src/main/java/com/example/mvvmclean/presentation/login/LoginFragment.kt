package com.example.mvvmclean.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mvvmclean.config.network.Status
import com.example.mvvmclean.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding = FragmentLoginBinding.inflate(layoutInflater,container, false);

        return binding.root;
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.status.collect { status ->
                    if ((status != Status.IDLE)) updateUI(status)
                }
            }
        }

        binding.btSubmit.setOnClickListener {
            viewModel.onSubmitCall()
        }
    }

    private fun updateUI(status : Status){
        binding.progress.visibility = if (status == Status.LOADING) View.VISIBLE else View.GONE
        binding.btSubmit.visibility = if (status == Status.LOADING) View.GONE else View.VISIBLE
        //Toast.makeText(requireContext(), "$status", Toast.LENGTH_SHORT).show()
    }
}