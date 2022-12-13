package com.buggily.enemy.data.theme.repository

import com.buggily.enemy.data.theme.ext.map
import com.buggily.enemy.data.theme.source.dynamic.DynamicSourceable
import com.buggily.enemy.data.theme.source.scheme.SchemeSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import com.buggily.enemy.core.model.theme.Theme as Output
import com.buggily.enemy.local.preferences.Theme as Input

class ThemeRepository(
    private val schemeSource: SchemeSourceable,
    private val dynamicSource: DynamicSourceable,
) : ThemeRepositable {

    override fun get(): Flow<Output> = combine(
        schemeSource.get(),
        dynamicSource.get(),
    ) { scheme: Input.Scheme, dynamic: Input.Dynamic ->
        Input(
            scheme = scheme,
            dynamic = dynamic,
        ).map()
    }

    override suspend fun set(theme: Output) = with(theme) {
        set(scheme)
        set(dynamic)
    }

    override suspend fun set(scheme: Output.Scheme) = schemeSource.set(scheme.map())
    override suspend fun set(dynamic: Output.Dynamic) = dynamicSource.set(dynamic.map())
}
