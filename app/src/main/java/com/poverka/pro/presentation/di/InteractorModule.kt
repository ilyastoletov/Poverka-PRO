package com.poverka.pro.presentation.di

import com.poverka.domain.feature.checkup.interactor.CheckupInteractor
import com.poverka.domain.feature.checkup.repository.CheckupRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class InteractorModule {

    @Provides
    fun provideCheckupInteractor(repository: CheckupRepository): CheckupInteractor =
        CheckupInteractor(repository)

}