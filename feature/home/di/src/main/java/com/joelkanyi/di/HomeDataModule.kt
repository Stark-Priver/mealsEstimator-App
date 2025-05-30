
package com.joelkanyi.di

import com.joelkanyi.data.repository.MealsRepositoryImpl
import com.joelkanyi.domain.repository.MealsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(
    SingletonComponent::class,
)
abstract class HomeDataModule {
    @Binds
    abstract fun bindMealsRepository(
        mealsRepositoryImpl: MealsRepositoryImpl
    ): MealsRepository
}
