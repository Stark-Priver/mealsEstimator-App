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
package com.joelkanyi.analytics.di

import android.content.Context
import com.joelkanyi.analytics.BuildConfig
import com.joelkanyi.analytics.data.repository.AnalyticsRepositoryImpl
import com.joelkanyi.analytics.domain.repository.AnalyticsRepository
import com.mixpanel.android.mpmetrics.MixpanelAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {

    @Singleton
    @Provides
    fun providesMixPaneApi(@ApplicationContext context: Context): MixpanelAPI {
        return MixpanelAPI.getInstance(context, BuildConfig.MIXPANEL_TOKEN, false)
    }

    @Singleton
    @Provides
    fun providesAnalyticsRepository(mixpanelAPI: MixpanelAPI): AnalyticsRepository {
        return AnalyticsRepositoryImpl(mixpanelAPI)
    }
}
