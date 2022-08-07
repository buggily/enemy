package com.buggily.enemy.data.di.repository.theme.dynamic

import com.buggily.enemy.data.repository.track.dynamic.DynamicRepositable
import com.buggily.enemy.data.repository.track.dynamic.DynamicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DynamicRepositableBinding {

    @Binds
    fun binds(
        repository: DynamicRepository,
    ): DynamicRepositable
}
