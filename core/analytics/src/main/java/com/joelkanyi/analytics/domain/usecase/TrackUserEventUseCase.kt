
package com.joelkanyi.analytics.domain.usecase

import com.joelkanyi.analytics.domain.repository.AnalyticsRepository
import javax.inject.Inject

class TrackUserEventUseCase @Inject constructor(
    private val analyticsRepository: AnalyticsRepository
) {
    operator fun invoke(name: String) = analyticsRepository.trackUserEvent(name)
}
