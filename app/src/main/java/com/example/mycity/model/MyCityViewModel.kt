package com.example.mycity.model
import androidx.lifecycle.ViewModel
import com.example.mycity.data.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyCityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState: StateFlow<MyCityUiState> = _uiState.asStateFlow()
    private var nextId = 0

    // seed some data for development
    init {
        addCategory("Food")
        addCategory("Drinks")
        addCategory("Entertainment")
    }

    fun addCategory(name: String) {
        _uiState.update { currentState ->
            if (
                currentState.categories.any { it.name == name } ||
                name == ""
                ) {
                currentState
            } else {
                val newCategory = Category(id = nextId++, name = name)
                currentState.copy(
                    categories = currentState.categories + newCategory
                )
            }


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