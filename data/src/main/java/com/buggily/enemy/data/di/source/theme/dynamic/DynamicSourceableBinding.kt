package com.buggily.enemy.data.di.source.theme.dynamic

import com.buggily.enemy.data.source.theme.dynamic.DynamicSource
import com.buggily.enemy.data.source.theme.dynamic.DynamicSourceable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DynamicSourceableBinding {

    @Binds
    fun binds(
        source: DynamicSource,
    ): DynamicSourceable
}
