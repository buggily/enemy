package com.buggily.enemy.data.di.repository.theme.dynamic

import com.buggily.enemy.data.repository.theme.dynamic.DynamicRepository
import com.buggily.enemy.data.source.theme.dynamic.DynamicSourceable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DynamicRepositoryModule {

    @Provides
    fun provides(
        source: DynamicSourceable,
    ): DynamicRepository = DynamicRepository(
        source = source,
    )
}
