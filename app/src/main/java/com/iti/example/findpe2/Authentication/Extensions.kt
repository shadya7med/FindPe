package com.iti.example.findpe2.Authentication

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