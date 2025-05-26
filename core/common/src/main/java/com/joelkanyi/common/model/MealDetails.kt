
package com.joelkanyi.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MealDetails(
    val name: String,
    val imageUrl: String,
    val cookingTime: Int?,
    val servingPeople: Int?,
    val category: String,
    val cookingDifficulty: String?,
    val ingredients: List<Ingredient> = emptyList(),
    val cookingDirections: List<String> = emptyList(),
    val mealId: String,
    val mealPlanId: String?,
    val calories: Double?,
    val description: String?,
    val recipePrice: Double?,
    val reviews: List<Review>,
    val serving: Int?,
    val youtubeUrl: String?
) : Parcelable
