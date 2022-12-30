package com.example.foodapp.api

import com.example.foodapp.model.CategoryList
import com.example.foodapp.model.PopularMealList
import com.example.foodapp.model.RandomMealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal() : Call<RandomMealList>

    @GET("lookup.php?")
    fun getRandomMealDetailsFromId(@Query("i") id : String) : Call<RandomMealList>

    @GET("filter.php?")
    fun getPopularItem(@Query("c") popularItem:String) : Call<PopularMealList>

    @GET("categories.php")
    fun getCategoryList() : Call<CategoryList>

    @GET("filter.php")
    fun getMealOnCategorySelect(@Query("c") popularItem :String) : Call<PopularMealList>


}