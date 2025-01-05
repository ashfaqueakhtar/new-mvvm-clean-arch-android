package com.example.mvvmclean.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmclean.databinding.ItemCarBinding
import com.example.mvvmclean.domain.model.Car

class CarAdapter(private val onCarClick: (Car) -> Unit) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    private val carList = mutableListOf<Car>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = carList[position]
        holder.bind(car)
    }

    override fun getItemCount(): Int = carList.size

    fun setCars(cars: List<Car>) {
        carList.clear()
        carList.addAll(cars)
        notifyDataSetChanged()
    }

    fun submitList(cars: List<Car>) {
        carList.clear()
        carList.addAll(cars)
        notifyDataSetChanged()
    }

    inner class CarViewHolder(private val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car) {
            binding.carName.text = car.name
            binding.carBrand.text = car.brand
            binding.carPrice.text = car.price
            Glide.with(binding.carImage.context).load(car.imageUrl).into(binding.carImage)
            binding.root.setOnClickListener { onCarClick(car) }
        }
    }
}