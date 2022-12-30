package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealCardBinding
import com.example.foodapp.model.PopularMeal

class CategorySelectedItemAdpater : RecyclerView.Adapter<CategorySelectedItemAdpater.CategorySelectedItemViewHolder>() {

    var categoryItemSelecetd = ArrayList<PopularMeal>()

    fun setCategorySelectedItemData(categoryItemSelecetd : ArrayList<PopularMeal>){
        this.categoryItemSelecetd = categoryItemSelecetd
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategorySelectedItemViewHolder {
        return CategorySelectedItemViewHolder(MealCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategorySelectedItemViewHolder, position: Int) {
        holder.binding.tvMealName.text = categoryItemSelecetd[position].strMeal
        Glide.with(holder.itemView)
            .load(categoryItemSelecetd[position].strMealThumb)
            .into(holder.binding.imgMeal)

    }

    override fun getItemCount(): Int {
        return categoryItemSelecetd.size
    }

    class CategorySelectedItemViewHolder(var binding: MealCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}