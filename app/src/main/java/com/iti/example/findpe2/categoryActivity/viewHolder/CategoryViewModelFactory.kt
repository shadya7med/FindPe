package com.iti.example.findpe2.categoryActivity.viewHolder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CategoryViewModelFactory (
private val categoryName: String,
private val categoryId: Int
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(categoryName, categoryId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
