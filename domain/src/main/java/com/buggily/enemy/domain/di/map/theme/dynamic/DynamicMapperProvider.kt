package com.buggily.enemy.domain.di.map.theme.dynamic

import com.buggily.enemy.domain.map.theme.dynamic.DynamicMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DynamicMapperProvider {

    @Provides
    fun provides(): DynamicMapper = DynamicMapper()
}
