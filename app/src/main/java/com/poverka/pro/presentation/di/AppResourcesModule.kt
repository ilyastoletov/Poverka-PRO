package com.poverka.pro.presentation.di

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class AppResourcesModule {

    @Provides
    @Singleton
    fun provideApplicationResources(@ApplicationContext context: Context): Resources {
        return context.resources
    }

}