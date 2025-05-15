/*
 * Copyright 2022 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joelkanyi.network

import com.joelkanyi.network.model.AuthResponseDto
import com.joelkanyi.network.model.CategoriesResponseDto
import com.joelkanyi.network.model.CreateFavoriteRequestDto
import com.joelkanyi.network.model.CreateMealRequestDto
import com.joelkanyi.network.model.FavoritesResponseDto
import com.joelkanyi.network.model.IngredientsResponseDto
import com.joelkanyi.network.model.LoginRequestDto
import com.joelkanyi.network.model.MealDetailsResponseDto
import com.joelkanyi.network.model.MealsResponseDto
import com.joelkanyi.network.model.RefreshTokenRequestDto
import com.joelkanyi.network.model.RegisterRequestDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MealtimeApiService {
    @GET("categories")
    suspend fun getCategories(): List<CategoriesResponseDto>

    @GET("meals")
    suspend fun getMeals(
        @Query("category") category: String? = null
    ): List<MealsResponseDto>

    @GET("meals/{mealId}")
    suspend fun getMealDetails(
        @Path("mealId") mealId: String
    ): MealDetailsResponseDto

    @GET("meals/search")
    suspend fun searchMeals(
        @Query("category") category: String? = null,
        @Query("name") name: String? = null,
        @Query("ingredient") ingredient: String? = null
    ): List<MealsResponseDto>

    @GET("meals/random")
    suspend fun getRandomMeal(): MealDetailsResponseDto

    @POST("meals")
    suspend fun saveMeal(@Body createMealRequestDto: CreateMealRequestDto)

    @GET("meals/ingredients")
    suspend fun getAllIngredients(): List<IngredientsResponseDto>

    @GET("favorite/{userId}")
    suspend fun getFavorites(
        @Path("userId") userId: String
    ): List<FavoritesResponseDto>

    @POST("favorite")
    suspend fun saveFavorite(
        @Body favorite: CreateFavoriteRequestDto
    )

    @DELETE("favorite/{mealId}/{userId}")
    suspend fun deleteFavorite(
        @Path("mealId") mealId: String,
        @Path("userId") userId: String
    )

    @POST("auth/google")
    fun signInWithGoogle(idToken: String): AuthResponseDto

    @Headers("Authorization: No-Auth")
    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body refreshTokenRequestDto: RefreshTokenRequestDto
    ): AuthResponseDto?

    @Headers("No-Authentication: true")
    @POST("auth/login")
    suspend fun loginUser(
        @Body loginRequestDto: LoginRequestDto
    ): AuthResponseDto

    @Headers("Authorization: No-Auth")
    @POST("auth/register")
    suspend fun registerUser(
        @Body registerRequestDto: RegisterRequestDto
    ): AuthResponseDto

    @Headers("Authorization: No-Auth")
    @POST("auth/forgot-password")
    suspend fun forgotPassword(email: String)
}
