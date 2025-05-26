
package com.joelkanyi.admeal.domain.usecase

import com.joelkanyi.admeal.domain.repository.AddMealRepository
import com.joelkanyi.common.model.Ingredient
import javax.inject.Inject

class SaveMealUseCase @Inject constructor(
    private val addMealRepository: AddMealRepository
) {
    suspend operator fun invoke(
        calories: Int,
        category: String,
        cookingDifficulty: String,
        cookingInstructions: List<String>,
        cookingTime: Int,
        description: String?,
        imageUrl: String,
        ingredients: List<Ingredient>,
        name: String,
        recipePrice: Double?,
        serving: Int?,
        youtubeUrl: String?
    ) = addMealRepository.saveMeal(
        calories,
        category,
        cookingDifficulty,
        cookingInstructions,
        cookingTime,
        description,
        imageUrl,
        ingredients,
        name,
        recipePrice,
        serving,
        youtubeUrl
    )
}
