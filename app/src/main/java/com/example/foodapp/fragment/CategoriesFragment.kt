package com.example.foodapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.CategoryDetailsActivity
import com.example.foodapp.MainActivity
import com.example.foodapp.adapters.CategoryItemAdapter
import com.example.foodapp.databinding.FragmentCategoriesBinding
import com.example.foodapp.model.Category
import com.example.foodapp.viewModel.HomeViewModel

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoryItemAdapter: CategoryItemAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryItemAdapter = CategoryItemAdapter()
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        onCategoryItemClick()
        viewModel.getCategoryList()
        observerCategoryLivedata()

    }

    private fun observerCategoryLivedata() {
        viewModel.observeCategoryListLiveData().observe(
            viewLifecycleOwner
        ){  categoryList->
            categoryItemAdapter.setCategoryList(categoryList = categoryList as ArrayList<Category>)
        }

    }
    private fun onCategoryItemClick(){
        categoryItemAdapter.onItemClick ={ category ->
            val intent = Intent(activity, CategoryDetailsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME,category.strCategory)
            startActivity(intent)

        }
    }

    private fun initRecyclerView() {
        binding.favoriteRecyclerView.apply {
            layoutManager = GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
            adapter = categoryItemAdapter
        }
    }


}