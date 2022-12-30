package com.example.foodapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.CategoryDetailsActivity
import com.example.foodapp.MainActivity
import com.example.foodapp.RandomMealDetailActivity
import com.example.foodapp.adapters.CategoryItemAdapter
import com.example.foodapp.adapters.PopularMealItemAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.fragment.bottom_sheet.MealBottomSheetFragment
import com.example.foodapp.model.Category
import com.example.foodapp.model.RandomMeal
import com.example.foodapp.model.PopularMeal
import com.example.foodapp.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private lateinit var popularMealItemAdapter : PopularMealItemAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal : RandomMeal
    private lateinit var categoryItemAdapter: CategoryItemAdapter



    companion object{
        const val MEAL_ID = "com.example.foodapp.mealID"
        const val MEAL_NAME = "com.example.foodapp.mealNAME"
        const val MEAL_THUMB = "com.example.foodapp.mealTHUMB"
        const val CATEGORY_NAME = "com.example.foodapp.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        homeViewModel = ViewModelProviders.of(this@HomeFragment)[HomeViewModel::class.java]
        viewModel = (activity as MainActivity).viewModel
        popularMealItemAdapter = PopularMealItemAdapter()
        categoryItemAdapter = CategoryItemAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onRandomMealClicked()
        initializePopularMealRecyclerView()
        initializeCategoryItemRecyclerView()
        observerRandomMeal()

        viewModel.getPopularMealList()
        observePopularMealitem()

        viewModel.getCategoryList()
        observerCategoryList()
        onPopularItemClick()
        onPopularItemLongClick()
        onCategoryItemClick()
    }

    private fun onPopularItemLongClick() {
        popularMealItemAdapter.onLongItemClick = {  meal->
            val bottomSheetFragment  = MealBottomSheetFragment.newInstance(meal.idMeal)
            bottomSheetFragment.show(childFragmentManager,"MealInfo")
        }
    }

    private fun initializeCategoryItemRecyclerView() {
        binding.recyclerView.apply {
//            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            layoutManager = GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
            adapter = categoryItemAdapter
        }
    }

    private fun observerCategoryList() {
        viewModel.observeCategoryListLiveData().observe(
            viewLifecycleOwner
        ){  categories->

            categoryItemAdapter.setCategoryList(categoryList = categories as ArrayList<Category>)

        }
    }

    private fun onCategoryItemClick(){
        categoryItemAdapter.onItemClick ={ category ->
            val intent = Intent(activity, CategoryDetailsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)

        }
    }

    private fun onPopularItemClick() {
        popularMealItemAdapter.onItemClick = {
            meal->
            val intent = Intent(activity, RandomMealDetailActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun initializePopularMealRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularMealItemAdapter
        }
    }

    private fun observePopularMealitem() {
        viewModel.observerPopularMealLiveData().observe(
            viewLifecycleOwner
        ) { popularMealList ->
                popularMealItemAdapter.setPopularMeal(popularMealList = popularMealList as ArrayList<PopularMeal>)

        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(
            viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)
            this.randomMeal = meal
        }

    }


    private fun onRandomMealClicked() {
        binding.imgRandomMeal.setOnClickListener{
            val intent = Intent(activity, RandomMealDetailActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

}