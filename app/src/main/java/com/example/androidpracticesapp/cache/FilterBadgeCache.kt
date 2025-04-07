package com.example.androidpracticesapp.cache

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class FilterBadgeCache {
    var shouldShowBadge by mutableStateOf(false)
}
