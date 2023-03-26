package com.buggily.enemy.data.theme.repository

import com.buggily.enemy.core.model.theme.Theme
import com.buggily.enemy.data.theme.ext.map
import com.buggily.enemy.data.theme.source.dynamic.DynamicLocalSourceable
import com.buggily.enemy.data.theme.source.scheme.SchemeLocalSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import com.buggily.enemy.local.preferences.Theme as LocalTheme

class ThemeRepository(
    private val schemeSource: SchemeLocalSourceable,
    private val dynamicSource: DynamicLocalSourceable,
) : ThemeRepositable {

    override fun get(): Flow<Theme> = combine(
        schemeSource.get(),
        dynamicSource.get(),
    ) { scheme: LocalTheme.Scheme, dynamic: LocalTheme.Dynamic ->
        LocalTheme(
            scheme = scheme,
            dynamic = dynamic,
        ).map()
    }

    override suspend fun set(theme: Theme) = with(theme) {
        set(scheme)
        set(dynamic)
    }

    override suspend fun set(
        scheme: Theme.Scheme,
    ) = schemeSource.set(
        scheme = scheme.map(),
    )
    override suspend fun set(
        dynamic: Theme.Dynamic,
    ) = dynamicSource.set(
        dynamic = dynamic.map(),
    )
}
