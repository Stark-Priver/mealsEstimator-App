
package com.joelkanyi.analytics.domain.repository

import org.json.JSONObject

interface AnalyticsRepository {
    fun trackUserEvent(eventName: String, eventProperties: JSONObject? = null)
    fun setUserProfile(userID: String, userProperties: JSONObject?)
}
