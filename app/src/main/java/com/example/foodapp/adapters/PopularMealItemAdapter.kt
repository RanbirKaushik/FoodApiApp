package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemPopularLayoutBinding
import com.example.foodapp.model.PopularMeal
import com.example.foodapp.model.PopularMealList

class PopularMealItemAdapter : RecyclerView.Adapter<PopularMealItemAdapter.PopularMealItemViewHolder>() {

    lateinit var onItemClick : ((PopularMeal) -> Unit)
    var onLongItemClick: ((PopularMeal) -> Unit)? = null

    private var popularMealList = ArrayList<PopularMeal>()



    fun setPopularMeal(popularMealList:ArrayList<PopularMeal>) {
        this.popularMealList = popularMealList
        notifyDataSetChanged()
    }


    class PopularMealItemViewHolder(var binding: ItemPopularLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealItemViewHolder {
        return PopularMealItemViewHolder(ItemPopularLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: PopularMealItemViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(popularMealList[position].strMealThumb)
            .into(holder.binding.imgPopularItem)

        holder.binding.imgPopularItem.setOnClickListener{
            onItemClick.invoke(popularMealList[position])
        }

        holder.binding.imgPopularItem.setOnLongClickListener{
            onLongItemClick!!.invoke(popularMealList[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return popularMealList.size
    }
}