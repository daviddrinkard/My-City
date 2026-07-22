package com.example.mycity.ui
import androidx.lifecycle.ViewModel
import com.example.mycity.model.Category
import com.example.mycity.model.Recommendation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Joshua David Drinkard
// drinkarj@oregonstate.edu
// CS 492

class MyCityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState: StateFlow<MyCityUiState> = _uiState.asStateFlow()
    private var nextId = 0
    private var nextRecoId = 0

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

    fun addRecommendation(name: String, categoryId: Int) {
        _uiState.update { currentState ->
            if (
               currentState.recommendations.any { it.name == name && it.categoryId == categoryId} ||
                name == ""
            ) {
                currentState
            } else {
                val newRecommendation = Recommendation(
                    id = nextRecoId++,
                    name = name,
                    categoryId = categoryId,
                    details = ""
                )
                currentState.copy(
                    recommendations = currentState.recommendations + newRecommendation
                )
            }
        }
    }

    fun updateRecommendationDetails(recommendation: Recommendation, text: String) {
        _uiState.update { currentState ->
            if (
                text == ""
            ) {
                currentState
            } else {
                currentState.copy(
                    recommendations = currentState.recommendations.map { reco ->
                        if (reco.id == recommendation.id) reco.copy(details = text) else reco
                    }
                )
            }
        }
    }

    fun deleteRecommendation(recommendation: Recommendation) {
        _uiState.update { currentState ->
            currentState.copy(
                recommendations = currentState.recommendations.filter { it.id != recommendation.id}
            )
        }
    }

    fun selectCategory(category: Category) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

}


data class MyCityUiState(
    val categories: List<Category> = emptyList(),
    val recommendations: List<Recommendation> = emptyList(),
    val selectedCategory: Category? = null
)