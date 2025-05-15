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
package com.joelkanyi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.joelkanyi.database.converters.Converters
import com.joelkanyi.database.dao.FavoritesDao
import com.joelkanyi.database.dao.MealDao
import com.joelkanyi.database.dao.MealPlanDao
import com.joelkanyi.database.dao.OnlineMealsDao
import com.joelkanyi.database.model.FavoriteEntity
import com.joelkanyi.database.model.MealEntity
import com.joelkanyi.database.model.MealPlanEntity
import com.joelkanyi.database.model.OnlineMealCategoryEntity
import com.joelkanyi.database.model.OnlineMealEntity

@Database(
    entities = [
        MealEntity::class,
        FavoriteEntity::class,
        MealPlanEntity::class,
        OnlineMealCategoryEntity::class,
        OnlineMealEntity::class
    ],
    version = 10,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class MealTimeDatabase : RoomDatabase() {
    abstract val mealDao: MealDao
    abstract val favoritesDao: FavoritesDao
    abstract val mealPlanDao: MealPlanDao
    abstract val onlineMealsDao: OnlineMealsDao
}
