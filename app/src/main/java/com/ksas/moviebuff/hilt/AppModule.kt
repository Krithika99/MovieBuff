package com.ksas.moviebuff.hilt

import android.content.Context
import android.content.SharedPreferences
import com.ksas.moviebuff.api.MoviesApi
import com.ksas.moviebuff.repository.MoviesMainRepo
import com.ksas.moviebuff.repository.MoviesRepository
import com.ksas.moviebuff.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideMoviesApi(): MoviesApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MoviesApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(api: MoviesApi): MoviesMainRepo = MoviesRepository(api)


}

@InstallIn(SingletonComponent::class)
@Module
object SharedPrefModule {

    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    }
}