package com.example.foodapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.api.RetrofitInstance
import com.example.foodapp.model.PopularMeal
import com.example.foodapp.model.PopularMealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryItemSelectViewModel : ViewModel() {

    private var categoryListNameLiveData =  MutableLiveData<List<PopularMeal>>()

    fun getSelectedCategoryItem(categoryName:String){
        RetrofitInstance.api.getMealOnCategorySelect(categoryName).enqueue(object : Callback<PopularMealList>{
            override fun onResponse(
                call: Call<PopularMealList>,
                response: Response<PopularMealList>
            ) {
                response.body()?.let { categoryItemSelect->
                    categoryListNameLiveData.postValue(categoryItemSelect!!.meals)
                }
            }

            override fun onFailure(call: Call<PopularMealList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun observeSelectedCategoryNameLiveData() : LiveData<List<PopularMeal>>{
        return categoryListNameLiveData
    }
}