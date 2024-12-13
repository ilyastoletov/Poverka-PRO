package com.poverka.pro.presentation.feature.snackbar.di

import com.poverka.pro.presentation.feature.snackbar.host.SnackbarHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SnackbarModule {

    @Provides
    @Singleton
    fun provideSnackbarHolder(): SnackbarHolder = SnackbarHolder()

}