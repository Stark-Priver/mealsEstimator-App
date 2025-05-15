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
package com.joelkanyi.auth.presentation.forgotpassword

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joelkanyi.analytics.domain.usecase.TrackUserEventUseCase
import com.joelkanyi.auth.domain.usecase.ForgotPasswordUseCase
import com.joelkanyi.auth.presentation.destinations.SignInScreenDestination
import com.joelkanyi.auth.presentation.state.LoginState
import com.joelkanyi.common.state.TextFieldState
import com.joelkanyi.common.util.Resource
import com.joelkanyi.common.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val trackUserEventUseCase: TrackUserEventUseCase,
) : ViewModel() {
    fun trackUserEvent(eventName: String) {
        viewModelScope.launch {
            trackUserEventUseCase.invoke(eventName)
        }
    }

    private val _forgotPasswordState = mutableStateOf(LoginState())
    val forgotPasswordState: State<LoginState> = _forgotPasswordState

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setEmailState(value: String) {
        _emailState.value = emailState.value.copy(text = value)
    }

    fun sendPasswordResetLink() {
        viewModelScope.launch {
            if (emailState.value.text.isEmpty()) {
                _eventFlow.emit(
                    UiEvents.SnackbarEvent(message = "Please input your email")
                )
                return@launch
            }

            _forgotPasswordState.value = forgotPasswordState.value.copy(isLoading = true)

            when (
                val result = forgotPasswordUseCase(
                    email = emailState.value.text
                )
            ) {
                is Resource.Error -> {
                    _forgotPasswordState.value = forgotPasswordState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Unknown Error Occurred"
                    )

                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = result.message ?: "Unknown Error Occurred"
                        )
                    )
                }

                is Resource.Success -> {
                    _forgotPasswordState.value = forgotPasswordState.value.copy(
                        isLoading = false,
                        data = null
                    )
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = "Password Reset link sent to your email Successfully"
                        )
                    )
                    _eventFlow.emit(
                        UiEvents.NavigationEvent(SignInScreenDestination.route)
                    )
                }

                else -> {
                    forgotPasswordState
                }
            }
        }
    }
}
