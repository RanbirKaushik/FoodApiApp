package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealCardBinding
import com.example.foodapp.model.RandomMeal

class FavouriteMealAdapter : RecyclerView.Adapter<FavouriteMealAdapter.FavouriteMealViewHolder>() {




    private val diffUtil = object  : DiffUtil.ItemCallback<RandomMeal>(){
        override fun areItemsTheSame(oldItem: RandomMeal, newItem: RandomMeal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: RandomMeal, newItem: RandomMeal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteMealViewHolder {
        return FavouriteMealViewHolder(MealCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FavouriteMealViewHolder, position: Int) {
        var meal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = meal.strMeal
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class FavouriteMealViewHolder(var binding: MealCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}