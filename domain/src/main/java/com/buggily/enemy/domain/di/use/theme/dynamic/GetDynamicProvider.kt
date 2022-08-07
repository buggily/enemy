package com.buggily.enemy.domain.di.use.theme.dynamic

import com.buggily.enemy.data.repository.track.dynamic.DynamicRepositable
import com.buggily.enemy.domain.map.theme.dynamic.DynamicMapper
import com.buggily.enemy.domain.use.theme.dynamic.GetDynamic
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetDynamicProvider {

    @Provides
    fun provides(
        repository: DynamicRepositable,
        mapper: DynamicMapper,
    ): GetDynamic = GetDynamic(
        repository = repository,
        mapper = mapper,
    )
}
