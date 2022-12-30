package com.example.foodapp.viewModel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.api.RetrofitInstance
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.model.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private var mealDatabase: MealDatabase) : ViewModel() {

    private var randomMealLiveData = MutableLiveData<RandomMeal>()
    private var popularMealListLiveData = MutableLiveData<List<PopularMeal>>()
    private var categoryListLiveData = MutableLiveData<List<Category>>()
    private var bottomSheetMealLiveData = MutableLiveData<RandomMeal>()
    private var favouriteMealLiveData = mealDatabase.mealDao().getAllMealData()

//You can also use this for saving state
//    init {
//        getRandomMeal()
//    }
//or just make a variable and check if not null then assign the postvalue of live data...
    var saveRandomMeal: RandomMeal? = null
    fun getRandomMeal() {

        saveRandomMeal?.let { meal->

            randomMealLiveData.postValue(meal)
            return

        }
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<RandomMealList> {
            override fun onResponse(
                call: Call<RandomMealList>,
                response: Response<RandomMealList>
            ) {
                if (response.body() != null) {
                    val randomMeal: RandomMeal = response.body()!!.meals[0]
//
                    randomMealLiveData.value = randomMeal
                    saveRandomMeal = randomMeal

                } else {
                    return
                }
            }

            override fun onFailure(call: Call<RandomMealList>, t: Throwable) {

            }

        })

    }

    fun getPopularMealList() {
        RetrofitInstance.api.getPopularItem("Seafood").enqueue(object : Callback<PopularMealList> {
            override fun onResponse(
                call: Call<PopularMealList>,
                response: Response<PopularMealList>
            ) {
                if (response.body() != null) {

                    popularMealListLiveData.value = response.body()!!.meals
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<PopularMealList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getCategoryList() {
        RetrofitInstance.api.getCategoryList().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

//                   if (response.body()!=null){
//                       categoryListLiveData.value = response.body()!!.categories
//                   }else{
//
//                   }

                response.body()?.let { categoryList ->

                    categoryListLiveData.postValue(categoryList.categories)

                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getRandomMealDetails(id: String) {

        RetrofitInstance.api.getRandomMealDetailsFromId(id)
            .enqueue(object : Callback<RandomMealList> {
                override fun onResponse(
                    call: Call<RandomMealList>,
                    response: Response<RandomMealList>
                ) {


                    val meal = response.body()?.meals?.first()

                    meal?.let { meal->
                        bottomSheetMealLiveData.postValue(meal)
                    }


//                    response.body()?.let { randomPopupList ->
//                        bottomSheetMealLiveData.postValue(randomPopupList.meals)
//                    }
                }

                override fun onFailure(call: Call<RandomMealList>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }


    fun observeRandomMealLiveData(): LiveData<RandomMeal> {
        return randomMealLiveData
    }

    fun observerPopularMealLiveData(): LiveData<List<PopularMeal>> {
        return popularMealListLiveData
    }

    fun observeCategoryListLiveData(): LiveData<List<Category>> {
        return categoryListLiveData
    }

    fun observefavouriteMealLivedata(): LiveData<List<RandomMeal>> {
        return favouriteMealLiveData
    }

    fun observeBottomSheetMealLivedata(): LiveData<RandomMeal> {
        return bottomSheetMealLiveData
    }

    fun deleteMeal(randomMeal: RandomMeal) {
        viewModelScope.launch {
            mealDatabase.mealDao().deleteMeal(randomMeal)
        }
    }

    fun insertMeal(randomMeal: RandomMeal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(randomMeal)
        }
    }


}