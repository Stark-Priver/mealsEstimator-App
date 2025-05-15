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
package com.joelkanyi.presentation.mealplanner.setup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joelkanyi.common.util.UiEvents
import com.joelkanyi.presentation.mealplanner.MealPlannerNavigator
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun MealTypesScreen(
    editMealPlanPreference: Boolean,
    allergies: String,
    numberOfPeople: String,
    navigator: MealPlannerNavigator,
    viewModel: SetupViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.eventsFlow.collectLatest { event ->
            when (event) {
                is UiEvents.NavigationEvent -> {
                    if (event.route == "settings") {
                        navigator.navigateToSettings()
                    } else {
                        navigator.openMealPlanner()
                    }
                }

                else -> {}
            }
        }
    }

    MealTypesScreenContent(
        mealTypes = viewModel.dishTypes,
        onClickComplete = {
            viewModel.trackUserEvent("meal_plan_preferences_saved")
            viewModel.saveDishTypes(dishTypes = viewModel.selectedDishType)
        },
        onClickAMealType = { type ->
            viewModel.trackUserEvent("meal_type_selected - $type")
            viewModel.insertSelectedDishType(type)
        },
        isSelected = { type ->
            viewModel.selectedDishType.contains(type)
        },
        onClickNavigateBack = {
            navigator.popBackStack()
        }
    )
}

@Composable
private fun MealTypesScreenContent(
    mealTypes: List<String>,
    isSelected: (String) -> Boolean,
    onClickComplete: () -> Unit,
    onClickAMealType: (String) -> Unit,
    onClickNavigateBack: () -> Unit = {},
) {
    Column(Modifier.fillMaxSize()) {
        com.joelkanyi.designsystem.components.StandardToolbar(
            navigate = onClickNavigateBack,
            title = {},
            showBackArrow = true,
            navActions = {}
        )

        LazyVerticalGrid(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            columns = GridCells.Fixed(2)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "Select Dish Types",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "Choose the different meal types to prepare in a day",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(mealTypes) { type ->
                MealTypeItemCard(
                    mealType = type,
                    onClick = {
                        onClickAMealType(type)
                    },
                    isSelected = isSelected
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(24.dp))
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Button(onClick = {
                    onClickComplete()
                }) {
                    Text(
                        modifier = Modifier
                            .padding(4.dp),
                        text = "Complete",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Composable
fun MealTypeItemCard(
    mealType: String,
    onClick: () -> Unit,
    isSelected: (String) -> Boolean
) {
    Card(
        Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(4.dp)
            .clickable {
                onClick()
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected(mealType)) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = mealType,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (isSelected(mealType)) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}
