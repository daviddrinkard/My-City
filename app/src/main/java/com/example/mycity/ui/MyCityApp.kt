package com.example.mycity.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycity.model.Category

// Joshua David Drinkard
// drinkarj@oregonstate.edu
// CS 492

enum class MyCityScreen {
    Categories,
    Recommendations,
    RecommendationDetail
}

@Composable
fun MyCityApp(
    modifier: Modifier = Modifier,
    viewModel: MyCityViewModel = viewModel()   // created ONCE here
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MyCityScreen.Categories.name,
        modifier = modifier
    ) {
        composable(MyCityScreen.Categories.name) {
            MyCityCategoryScreen(
                viewModel = viewModel,
                onCategoryClick = { category ->
                    viewModel.selectCategory(category)
                    navController.navigate(MyCityScreen.Recommendations.name)
                }
            )
        }
        composable(MyCityScreen.Recommendations.name) {
            // recommendations here
        }
    }
}

