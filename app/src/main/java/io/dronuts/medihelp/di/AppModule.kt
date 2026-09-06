package io.dronuts.medihelp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.dronuts.medihelp.data.local.AppDatabase
import io.dronuts.medihelp.data.repository.IncidentRepository
import io.dronuts.medihelp.data.repository.MockIncidentRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "medihelp-db").build()
    }

    @Provides
    @Singleton
    fun provideIncidentRepository(): IncidentRepository = MockIncidentRepository()
}
