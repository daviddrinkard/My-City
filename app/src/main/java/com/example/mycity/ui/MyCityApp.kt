package com.example.mycity.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState

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
    viewModel: MyCityViewModel = viewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = backStackEntry?.destination?.route != MyCityScreen.Categories.name

    Scaffold(
        topBar = {
            MyCityAppBar(
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
    NavHost(
        navController = navController,
        startDestination = MyCityScreen.Categories.name,
        modifier = Modifier.padding(innerPadding)
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
            MyCityRecommendationScreen(
                viewModel = viewModel,
                onRecommendationClick = { recommendation ->
                    // placeholder for now
                }
            )
        }
    }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCityAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "MyCity",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp
            )
                },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}