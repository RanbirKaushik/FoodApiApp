package com.example.foodapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.foodapp.model.RandomMeal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(randomMeal: RandomMeal)

    @Delete
    suspend fun deleteMeal(randomMeal: RandomMeal)

    @Query("SELECT * FROM mealDatabase")
    fun getAllMealData() : LiveData<List<RandomMeal>>
}