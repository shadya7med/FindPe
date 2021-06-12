package com.iti.example.findpe2.utils

import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

fun View.setAllClickable(clickable: Boolean){
    isClickable = clickable
    if(this is ViewGroup) children.forEach { child ->
        child.setAllClickable(clickable)
    }
}
fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

enum class Category(val id: Int){
    POPULARITY(1),
    CULTURAL(2),
    SEA(3),
    MODERN(4),
    NATURE(5)

}