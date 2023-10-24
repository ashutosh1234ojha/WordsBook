package com.example.mywordsbook.data

import android.graphics.drawable.VectorDrawable
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route:String
)
