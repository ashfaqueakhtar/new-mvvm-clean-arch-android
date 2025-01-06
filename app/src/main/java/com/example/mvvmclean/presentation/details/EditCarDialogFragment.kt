package com.example.mvvmclean.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.mvvmclean.databinding.DialogEditCarBinding
import com.example.mvvmclean.domain.model.Car

class EditCarDialogFragment : DialogFragment() {

    private lateinit var binding: DialogEditCarBinding

    private var car: Car? = null

    private var onCarUpdated: ((Car) -> Unit)? = null

    companion object {
        fun newInstance(car: Car): EditCarDialogFragment {
            val fragment = EditCarDialogFragment()
            val args = Bundle()
            args.putParcelable("car", car)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        car = arguments?.getParcelable("car")
    }

    fun updateData(callBack: (Car) -> Unit){
        onCarUpdated =  callBack
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dialog?.let {
            it.window?.let { w->
                w.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            }
        }

        binding = DialogEditCarBinding.inflate(inflater, container, false)
        val v = binding.root

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        car?.let { c ->
            binding.nameEditText.setText(c.name)
            binding.brandEditText.setText(c.brand)
            binding.priceEditText.setText(c.price)

            binding.saveButton.setOnClickListener {
                val updatedCar = c.copy(
                    name = binding.nameEditText.text.toString(),
                    brand = binding.brandEditText.text.toString(),
                    price = binding.priceEditText.text.toString()
                )
                onCarUpdated?.invoke(updatedCar)


                /*val parentFragment = parentFragment
                if (parentFragment is CarDetailFragment) {
                    parentFragment.viewModel.updateCarDetails(updatedCar)
                } else {
                    Log.d("Error", "Parent fragment is not CarDetailFragment")
                }*/
                dismiss()
            }
        }
    }
}