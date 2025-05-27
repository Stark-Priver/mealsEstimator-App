
package com.joelkanyi.auth.presentation.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joelkanyi.analytics.domain.usecase.SetUserProfileUseCase
import com.joelkanyi.analytics.domain.usecase.TrackUserEventUseCase
import com.joelkanyi.auth.domain.usecase.SignInWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LandingPageViewModel @Inject constructor(
    private val trackUserEventUseCase: com.joelkanyi.analytics.domain.usecase.TrackUserEventUseCase,
    private val setUserProfileUseCase: com.joelkanyi.analytics.domain.usecase.SetUserProfileUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
) : ViewModel() {
    fun trackUserEvent(eventName: String) {
        viewModelScope.launch {
            trackUserEventUseCase.invoke(eventName)
        }
    }

    fun setUserProfile(
        userId: String,
        userProperties: JSONObject?
    ) {
        viewModelScope.launch {
            setUserProfileUseCase(
                userID = userId,
                userProperties = userProperties
            )
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            signInWithGoogleUseCase(
                idToken = idToken
            )
        }
    }
}
