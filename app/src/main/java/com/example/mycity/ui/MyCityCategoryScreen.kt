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
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.example.mycity.model.Category

@Composable
fun MyCityApp(
    modifier: Modifier = Modifier,
    viewModel: MyCityViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val layoutDirection = LocalLayoutDirection.current
    var newCategoryName by remember { mutableStateOf("") }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection),
            ),
    ) {
        Column {
            // Header
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = "Atlanta, GA",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 36.sp,
                    color = Color.White
                )
            }

            // Scrollable list of categories
            MyCityCategoryList(
                categoryList = uiState.categories,
                onDeleteCategory = { category -> viewModel.deleteCategory(category) },
                modifier = Modifier.weight(1f)
            )

            // Footer
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .padding(bottom = 12.dp)
            ) {
                TextField(
                    value = newCategoryName,
                    onValueChange = { newCategoryName = it },
                    label = { Text("New category") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
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
}

@Composable
fun MyCityCategoryList(
    modifier: Modifier = Modifier,
    categoryList: List<Category>,
    onDeleteCategory: (Category) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(12.dp)
    ) {
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
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(6.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = category.name,
                modifier = Modifier.weight(1f),
                fontSize = 18.sp
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
}