package com.ksas.moviebuff.hilt

import android.content.Context
import androidx.room.Room
import com.ksas.moviebuff.database.MovieDao
import com.ksas.moviebuff.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "MovieDB").build()
    }

}