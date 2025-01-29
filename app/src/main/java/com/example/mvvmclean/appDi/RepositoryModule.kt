package com.example.mvvmclean.appDi

import com.example.mvvmclean.data.repository.AuthRepositoryImpl
import com.example.mvvmclean.data.repository.FakeCarRepository
import com.example.mvvmclean.domain.repository.AuthRepository
import com.example.mvvmclean.domain.repository.CarRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun CarRepository(fakeCarRepository: FakeCarRepository) : CarRepository

    @Binds
    @Singleton
    abstract fun AuthRepository(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository
}