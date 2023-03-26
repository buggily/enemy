package com.buggily.enemy.data.theme.di.source.dynamic

import com.buggily.enemy.data.theme.source.dynamic.DynamicLocalSource
import com.buggily.enemy.data.theme.source.dynamic.DynamicLocalSourceable
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DynamicLocalSourceableBinding {

    @Binds
    fun binds(
        source: DynamicLocalSource,
    ): DynamicLocalSourceable
}
