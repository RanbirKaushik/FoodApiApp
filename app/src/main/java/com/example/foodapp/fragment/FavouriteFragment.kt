package com.example.foodapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.MainActivity
import com.example.foodapp.adapters.FavouriteMealAdapter
import com.example.foodapp.databinding.FragmentFavouriteBinding
import com.example.foodapp.model.RandomMeal
import com.example.foodapp.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var viewModel : HomeViewModel
    private lateinit var favouriteMealAdapter: FavouriteMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = (activity as MainActivity).viewModel
        favouriteMealAdapter = FavouriteMealAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFavourite()
        initRecyclerView()


        val itemTouchHelper = object  : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or  ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val favoriteMeal = favouriteMealAdapter.differ.currentList[position]

               // viewModel.deleteMeal(favouriteMealAdapter.differ.currentList[position])
                viewModel.deleteMeal(favoriteMeal)
                showDeleteSnackBar(favoriteMeal)
//                Snackbar.make(requireView(),"Meal Deleted...",Snackbar.LENGTH_LONG).setAction(
//                    "UNDO",
//                    View.OnClickListener {
//                        viewModel.insertMeal(favouriteMealAdapter.differ.currentList[position])
//                    }
//                ).show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecView)
    }

    private fun showDeleteSnackBar(favoriteMeal:RandomMeal) {
        Snackbar.make(requireView(),"Meal was deleted",Snackbar.LENGTH_LONG).apply {
            setAction("undo",View.OnClickListener {
                viewModel.insertMeal(favoriteMeal)
            }).show()
        }
    }

    private fun initRecyclerView() {
        binding.favRecView.apply {

            layoutManager = GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false)
            adapter = favouriteMealAdapter

        }
    }

    //requireActivity() in viewLifecycleOwner
    private fun observeFavourite() {
        viewModel.observefavouriteMealLivedata().observe(
            viewLifecycleOwner
        ){  savedMealList->
//            savedMealList.forEach{ mealList->
//                Log.d("Tag"," "+ mealList.strMeal)
//
//            }

            favouriteMealAdapter.differ.submitList(savedMealList)

        }
    }
}