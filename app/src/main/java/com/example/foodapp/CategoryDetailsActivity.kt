package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.adapters.CategorySelectedItemAdpater
import com.example.foodapp.databinding.ActivityCategoryDetailsBinding
import com.example.foodapp.fragment.HomeFragment
import com.example.foodapp.model.PopularMeal
import com.example.foodapp.viewModel.CategoryItemSelectViewModel

class CategoryDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryDetailsBinding
    private lateinit var categoryItemSelectViewModel: CategoryItemSelectViewModel
    private lateinit var categorySelectedItemAdpater : CategorySelectedItemAdpater
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categorySelectedItemAdpater = CategorySelectedItemAdpater()
        initRecyclerView()
        categoryItemSelectViewModel = ViewModelProviders.of(this@CategoryDetailsActivity)[CategoryItemSelectViewModel::class.java]

        categoryItemSelectViewModel.getSelectedCategoryItem(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        observerCategorySelectedList()


    }

    private fun initRecyclerView() {
        binding.mealRecyclerview.apply {
            layoutManager = GridLayoutManager(this@CategoryDetailsActivity,2,GridLayoutManager.VERTICAL,false)
            adapter = categorySelectedItemAdpater
        }
    }

    private fun observerCategorySelectedList() {
        categoryItemSelectViewModel.observeSelectedCategoryNameLiveData().observe(
            this,
        ){  getSelectedMeal->

            categorySelectedItemAdpater.setCategorySelectedItemData(getSelectedMeal as ArrayList<PopularMeal>)
            binding.tvCategoryCount.text = "Total Result: "+getSelectedMeal.size.toString()
        }
    }
}