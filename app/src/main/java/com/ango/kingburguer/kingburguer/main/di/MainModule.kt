package com.ango.kingburguer.kingburguer.main.di

import com.ango.kingburguer.kingburguer.main.data.KingBurguerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tiagoaguiar.kingburguer.main.domain.KingBurguerRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {


    @Binds
    @Singleton
    abstract fun providesRepository(repository: KingBurguerRepositoryImpl): KingBurguerRepository

}