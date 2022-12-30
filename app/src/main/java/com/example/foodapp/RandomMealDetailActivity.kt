package com.example.foodapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ActivityRandomMealDetailBinding
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.fragment.HomeFragment
import com.example.foodapp.model.RandomMeal
import com.example.foodapp.viewModel.RandomViewModelProviderFactory
import com.example.foodapp.viewModel.RandomMealDetailsViewModel

class RandomMealDetailActivity : AppCompatActivity() {

    private lateinit var randomMealDetailsviewModel: RandomMealDetailsViewModel
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var youtubeUrl:String

    private lateinit var binding: ActivityRandomMealDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomMealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onLoading()

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = RandomViewModelProviderFactory(mealDatabase)
        randomMealDetailsviewModel = ViewModelProvider(this,viewModelFactory)[RandomMealDetailsViewModel::class.java]

//        randomMealDetailsviewModel = ViewModelProviders.of(this@RandomMealDetailActivity)[RandomMealDetailsViewModel::class.java]

        getDataFromIntent()

        setData()

        randomMealDetailsviewModel.getRandomMealDetails(mealId)
        getrandomMealDetailsviewModelObserval()
        onYoutubeClicked()

        onFavButtonClicked()
    }

    private fun onFavButtonClicked() {
        binding.btnSave.setOnClickListener{
            mealToSave?.let {
                randomMealDetailsviewModel.insertMeal(it)
                Toast.makeText(this,"Added to Favourite",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeClicked() {
        binding.imgYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
            startActivity(intent)
        }
    }

    private var mealToSave:RandomMeal? = null

    private fun getrandomMealDetailsviewModelObserval() {
        randomMealDetailsviewModel.observeRandomMealDetailsLiveData().observe(this){

                meal->
            onResponse()
            mealToSave = meal
            binding.tvCategoryInfo.text = "Category: {${meal.strCategory}}"
            binding.tvAreaInfo.text = "Area: {${meal.strArea}}"
            binding.tvInstructions.text = "Instruction: {${meal.strInstructions}}"

            youtubeUrl = meal.strYoutube.toString()
        }
    }

    private fun setData() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getDataFromIntent() {
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }
     fun onLoading(){
         binding.progressBar.visibility = View.VISIBLE
         binding.tvAreaInfo.visibility = View.GONE
         binding.tvInstructions.visibility = View.GONE
         binding.tvCategoryInfo.visibility = View.GONE
         binding.tvContent.visibility = View.GONE
         binding.btnSave.visibility = View.GONE
     }

    fun onResponse(){
        binding.progressBar.visibility = View.GONE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvContent.visibility = View.VISIBLE
        binding.btnSave.visibility = View.VISIBLE
    }



}