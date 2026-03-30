package com.example.flexora.di

import android.content.Context
import androidx.room.Room
import com.example.flexora.data.local.AppDatabase
import com.example.flexora.data.local.dao.WorkoutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "flexora_db"
        ).build()
    }

    @Provides
    fun provideWorkoutDao(database: AppDatabase): WorkoutDao {
        return database.workoutDao()
    }
}
