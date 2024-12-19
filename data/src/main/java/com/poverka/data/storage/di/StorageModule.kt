package com.poverka.data.storage.di

import android.content.Context
import androidx.room.Room
import com.poverka.data.storage.CheckupDatabase
import com.poverka.data.storage.dao.CheckupDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun provideCheckupDatabase(@ApplicationContext context: Context): CheckupDatabase {
        return Room.databaseBuilder(context, CheckupDatabase::class.java, "checkup.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCheckupDao(checkupDatabase: CheckupDatabase): CheckupDao {
        return checkupDatabase.getCheckupDao()
    }

}