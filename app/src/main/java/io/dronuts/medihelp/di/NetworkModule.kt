package io.dronuts.medihelp.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.dronuts.medihelp.BuildConfig
import io.dronuts.medihelp.auth.AuthRepository
import io.dronuts.medihelp.auth.AuthRepositoryImpl
import io.dronuts.medihelp.auth.SecureTokenStore
import io.dronuts.medihelp.data.repository.IncidentRepository
import io.dronuts.medihelp.data.repository.MockIncidentRepository
import io.dronuts.medihelp.data.repository.NetworkIncidentRepository
import io.dronuts.medihelp.data.mqtt.MqttClient
import io.dronuts.medihelp.data.mqtt.MockMqttClient
import io.dronuts.medihelp.data.mqtt.PahoMqttAdapter
import io.dronuts.medihelp.mqtt.PahoMqttClient
import io.dronuts.medihelp.network.ApiService
import io.dronuts.medihelp.network.AuthInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideSecureStore(@ApplicationContext context: Context): SecureTokenStore = SecureTokenStore(context)

    @Provides
    @Singleton
    fun provideLogging(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

    @Provides
    @Singleton
    fun provideOkHttp(store: SecureTokenStore, logging: HttpLoggingInterceptor): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(store))
        if (BuildConfig.DEBUG) clientBuilder.addInterceptor(logging)
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(api: ApiService, store: SecureTokenStore): AuthRepository = AuthRepositoryImpl(api, store)

    @Provides
    @Singleton
    fun provideMqttClient(@ApplicationContext ctx: Context): MqttClient {
        return if (BuildConfig.FLAVOR == "demo") MockMqttClient() else PahoMqttAdapter(PahoMqttClient(ctx, BuildConfig.BASE_API_URL))
    }

    @Provides
    @Singleton
    fun provideNetworkIncidentRepository(api: ApiService, @ApplicationContext ctx: Context): NetworkIncidentRepository =
        NetworkIncidentRepository(api, ctx)
}
