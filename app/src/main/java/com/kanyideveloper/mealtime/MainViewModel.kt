
package com.kanyideveloper.mealtime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joelkanyi.settings.domain.usecase.GetAppThemeUseCase
import com.joelkanyi.settings.domain.usecase.GetIfUserIsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAppThemeUseCase: GetAppThemeUseCase,
    getIfUserIsLoggedInUseCase: GetIfUserIsLoggedInUseCase
) : ViewModel() {
    val isLoggedIn = getIfUserIsLoggedInUseCase()
        .map { it != null }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )
    val theme = getAppThemeUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0,
        )
}
