package com.example.foodapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.api.RetrofitInstance
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.model.RandomMeal
import com.example.foodapp.model.RandomMealList
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RandomMealDetailsViewModel(val mealDatabase: MealDatabase) : ViewModel() {

    private var randomMealDetailsLiveData = MutableLiveData<RandomMeal>()


    fun getRandomMealDetails(id:String){

        RetrofitInstance.api.getRandomMealDetailsFromId(id).enqueue(object : Callback<RandomMealList>{
            override fun onResponse(
                call: Call<RandomMealList>,
                response: Response<RandomMealList>
            ) {
                if (response.body() !=null){
//                    val randomMealDeatail : Meal = response.body()!!.meals[0]
                    randomMealDetailsLiveData.value = response.body()!!.meals[0]

                }else
                {
                    return
                }
            }

            override fun onFailure(call: Call<RandomMealList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun observeRandomMealDetailsLiveData() : LiveData<RandomMeal> {
        return randomMealDetailsLiveData
    }

    fun insertMeal(randomMeal: RandomMeal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(randomMeal)
        }
    }

}