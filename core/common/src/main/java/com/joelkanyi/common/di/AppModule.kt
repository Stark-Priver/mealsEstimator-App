
package com.joelkanyi.common.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): DatabaseReference {
        val database = FirebaseDatabase.getInstance().reference
        database.keepSynced(true)
        return database
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorageReference(): StorageReference {
        return FirebaseStorage.getInstance().getReference("meal_images")
    }

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun providesContent(@ApplicationContext context: Context): Context {
        return context
    }
}
