
package com.joelkanyi.admeal.di

import com.joelkanyi.admeal.data.repository.AddMealRepositoryImpl
import com.joelkanyi.admeal.domain.repository.AddMealRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AddMealModule {
    @Binds
    abstract fun bindAddMealRepository(
        addMealRepositoryImpl: AddMealRepositoryImpl
    ): AddMealRepository
}
