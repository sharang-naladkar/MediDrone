package io.dronuts.medihelp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.dronuts.medihelp.data.local.AppDatabase
import io.dronuts.medihelp.data.local.IncidentDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDbModule {
    @Provides
    @Singleton
    fun provideIncidentDao(database: AppDatabase): IncidentDao = database.incidentDao()
}
