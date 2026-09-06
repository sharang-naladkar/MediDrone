package io.dronuts.medihelp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.dronuts.medihelp.BuildConfig
import io.dronuts.medihelp.data.local.AppDatabase
import io.dronuts.medihelp.data.mqtt.MockMqttClient
import io.dronuts.medihelp.data.mqtt.MqttClient
import io.dronuts.medihelp.data.repository.IncidentRepository
import io.dronuts.medihelp.data.repository.MockIncidentRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "medihelp-db").build()
    }

    @Provides
    @Singleton
    fun provideIncidentRepository(): IncidentRepository = MockIncidentRepository()

    @Provides
    @Singleton
    fun provideMqttClient(): MqttClient {
        return if (BuildConfig.USE_MOCKS) {
            MockMqttClient()
        } else {
            MockMqttClient()
        }
    }
}
