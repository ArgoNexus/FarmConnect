package com.example.farmconnect.Fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.farmconnect.Constants
import com.example.farmconnect.R
import com.example.farmconnect.adapters.AdapterCategory
import com.example.farmconnect.databinding.FragmentHomeBinding
import com.example.farmconnect.models.Category


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        setStatusBarColor()
        navigatingToSearchFragment()
        setAllCategories()
        return binding.root
    }

    private fun navigatingToSearchFragment() {
        binding.searchCv.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun setStatusBarColor() {
        activity?.window?.apply {
            val statusBarColor = ContextCompat.getColor(requireContext(), R.color.yellow)
            this.statusBarColor = statusBarColor

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
    private fun setAllCategories(){
        val categoryList=ArrayList<Category>()
        for(i in 0 until Constants.AllProductsCategory.size){
            categoryList.add(Category(
                Constants.AllProductsCategory[i],
                Constants.AllProductsCategoryIcon[i]))
        }
        binding.rvCategories.adapter= AdapterCategory(categoryList)

    }
}