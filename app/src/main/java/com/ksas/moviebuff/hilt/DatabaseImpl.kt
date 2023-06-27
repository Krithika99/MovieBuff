package com.ksas.moviebuff.hilt

import com.ksas.moviebuff.repository.MoviesDatabaseImpl
import com.ksas.moviebuff.repository.MoviesDatabaseRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DatabaseImpl {
    @Binds
    abstract fun bindDatabaseImpl(impl: MoviesDatabaseImpl): MoviesDatabaseRepo

}