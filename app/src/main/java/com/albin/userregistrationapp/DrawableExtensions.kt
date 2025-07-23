package com.albin.userregistrationapp


import android.graphics.drawable.GradientDrawable
import android.graphics.Color // Add this import for convenience if you use Color.parseColor later

var GradientDrawable.strokeColor: Int
    get() = 0
    set(value) {
        setStroke(14, value)
    }