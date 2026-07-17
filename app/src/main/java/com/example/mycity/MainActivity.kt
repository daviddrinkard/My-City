package com.example.mycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycity.model.Category
import com.example.mycity.ui.MyCityViewModel
import com.example.mycity.ui.theme.MyCityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyCityApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MyCityApp(
    modifier: Modifier = Modifier,
    viewModel: MyCityViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val layoutDirection = LocalLayoutDirection.current
    var newCategoryName by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection),
            ),
    ) {

        MyCityCategoryList(
            categoryList = uiState.categories,
            onDeleteCategory = { category -> viewModel.deleteCategory(category) }
        )
    Row() {
        TextField(
            value = newCategoryName,
            onValueChange = { newCategoryName = it },
            label = { Text("New category") }
        )
        Button(
            onClick = {
                viewModel.addCategory(newCategoryName)
                newCategoryName = ""
            }
        ) {
            Text(
                text = "Add"
            )

        }
    }

    }
}

@Composable
fun MyCityCategoryList(
    modifier: Modifier = Modifier,
    categoryList: List<Category>,
    onDeleteCategory: (Category) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(categoryList) { category ->
            CategoryCard(
                category = category,
                onDeleteCategory = onDeleteCategory
            )
        }

    }
}

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: Category,
    onDeleteCategory: (Category) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Text(
            text = category.name,
        )
        Button(
            onClick = { onDeleteCategory(category) }
        ) {
            Text(
                text = "X"
            )
        }
    }
}