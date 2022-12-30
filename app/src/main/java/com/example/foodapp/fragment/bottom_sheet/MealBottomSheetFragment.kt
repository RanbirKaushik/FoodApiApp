package com.example.foodapp.fragment.bottom_sheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodapp.MainActivity
import com.example.foodapp.RandomMealDetailActivity
import com.example.foodapp.databinding.FragmentMealBottomSheetBinding
import com.example.foodapp.fragment.HomeFragment
import com.example.foodapp.viewModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID = "param1"

class MealBottomSheetFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var mealId: String? = null
    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMealBottomSheetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        mealId?.let { viewModel.getRandomMealDetails(it) }

        observerBottomSheetMealLivedata()

        onBottomSheetDialogClicked()

    }

    private fun onBottomSheetDialogClicked() {
        binding.bottomSheet.setOnClickListener{
            if (mealName != null && mealThumb != null){
                val intent = Intent(activity,RandomMealDetailActivity::class.java)
                intent.apply{
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                    putExtra(HomeFragment.MEAL_ID,mealId)
                }
                startActivity(intent)
            }
        }
    }

    private var mealName:String? = null
    private var mealThumb:String? = null
    private fun observerBottomSheetMealLivedata() {
        viewModel.observeBottomSheetMealLivedata().observe(
            viewLifecycleOwner, Observer {
                    meal->
                Glide.with(this)
                    .load(meal.strMealThumb)
                    .into(binding.imgCategory)

                binding.tvMealCategory.text = meal.strCategory
                binding.tvMealCountry.text = meal.strArea
                binding.tvMealNameInBtmsheet.text = meal.strMeal


                mealName = meal.strMeal
                mealThumb = meal.strMealThumb
            }
        )
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String) =
                MealBottomSheetFragment().apply {
                    arguments = Bundle().apply {
                        putString(MEAL_ID, param1)
                    }
                }
    }
}