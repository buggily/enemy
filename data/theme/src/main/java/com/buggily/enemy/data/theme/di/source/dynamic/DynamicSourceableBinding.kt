package com.buggily.enemy.data.theme.di.source.dynamic

import com.buggily.enemy.data.theme.source.dynamic.DynamicSource
import com.buggily.enemy.data.theme.source.dynamic.DynamicSourceable
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
