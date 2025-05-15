/*
 * Copyright 2023 Joel Kanyi.
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
package com.joelkanyi.data.repository

import com.joelkanyi.common.model.Meal
import com.joelkanyi.common.util.Resource
import com.joelkanyi.common.util.safeApiCall
import com.joelkanyi.data.mapper.toMealPlan
import com.joelkanyi.domain.entity.MealPlan
import com.joelkanyi.domain.repository.MealPlannerRepository
import com.joelkanyi.settings.domain.MealtimeSettings
import com.joelkanyi.settings.domain.model.MealPlanPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class MealPlannerRepositoryImpl @Inject constructor(
    private val mealtimeSettings: MealtimeSettings,
    private val mealPlanDao: com.joelkanyi.database.dao.MealPlanDao,
    private val mealDbApi: com.joelkanyi.network.MealDbApi,
) : MealPlannerRepository {

    override fun mealPlanPref(): Flow<MealPlanPreference?> {
        return mealtimeSettings.mealPlanPreferences()
    }

    override suspend fun saveAllergies(allergies: List<String>) {
        mealtimeSettings.saveAllergies(allergies = allergies)
    }

    override suspend fun saveNumberOfPeople(numberOfPeople: String) {
        mealtimeSettings.saveNumberOfPeople(numberOfPeople = numberOfPeople)
    }

    override suspend fun saveDishTypes(dishTypes: List<String>) {
        mealtimeSettings.saveDishTypes(dishTypes = dishTypes)
    }

    override suspend fun getMealsInMyPlan(filterDay: String): Flow<List<MealPlan>> {
        return mealPlanDao.getPlanMeals(filterDay = filterDay).map { meals ->
            meals.map { it.toMealPlan() }
        }
    }

    override suspend fun getAllIngredients(): Resource<List<String>> {
        return safeApiCall(Dispatchers.IO) {
            val response = mealDbApi.getAllIngredients()
            Timber.d("Ingredients response: $response")
            response.map { it.name }
        }
    }

    override suspend fun deleteAMealFromPlan(mealId: String) {
        mealPlanDao.deleteAMealFromPlan(id = mealId)
    }

    override suspend fun saveMealToPlan(
        mealTypeName: String,
        mealName: String,
        mealImageUrl: String,
        mealId: String,
        mealCategory: String,
        date: String
    ) {
        mealPlanDao.insertMealPlan(
            com.joelkanyi.database.model.MealPlanEntity(
                mealTypeName = mealTypeName,
                mealDate = date,
                meals = listOf(
                    Meal(
                        name = mealName,
                        imageUrl = mealImageUrl,
                        mealId = mealId,
                        category = mealCategory
                    )
                ),
            )
        )
    }
}
