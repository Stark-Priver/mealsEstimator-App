
package com.joelkanyi.analytics.domain.usecase

import com.joelkanyi.analytics.domain.repository.AnalyticsRepository
import org.json.JSONObject
import javax.inject.Inject

class SetUserProfileUseCase @Inject constructor(
    private val analyticsRepository: AnalyticsRepository
) {
    operator fun invoke(userID: String, userProperties: JSONObject?) =
        analyticsRepository.setUserProfile(userID, userProperties)
}
