package com.example.mycity.ui
import com.example.mycity.data.Category
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyCityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState: StateFlow<MyCityUiState> = _uiState.asStateFlow()
    private var nextId = 0

    fun addCategory(name: String) {
        val newCategory = Category(id = nextId++, name = name)
        _uiState.update { currentState ->
            currentState.copy(
                categories = currentState.categories + newCategory
            )
        }
    }

    fun deleteCategory(category: Category) {
        _uiState.update { currentState ->
            currentState.copy(
                categories = currentState.categories.filter { it.id != category.id }
            )
        }
    }

}


data class MyCityUiState(
    val categories: List<Category> = emptyList()
)