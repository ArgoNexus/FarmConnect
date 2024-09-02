package com.example.farmconnect.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.farmconnect.databinding.ItemsViewProductBinding
import com.example.farmconnect.models.Category
import java.util.ArrayList

class AdapterCategory (
    val categoryList:ArrayList<Category>):
        RecyclerView.Adapter<AdapterCategory.CategoryViewHolder>(){
    class CategoryViewHolder(val binding: ItemsViewProductBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
       return CategoryViewHolder(ItemsViewProductBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.binding.apply {

             ivCatgoryImage.setImageResource(category.image)
              tvCategoryTitle.text=category.title}
        }
    }


