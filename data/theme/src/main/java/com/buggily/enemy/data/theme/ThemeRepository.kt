package com.buggily.enemy.data.theme

import com.buggily.enemy.local.preferences.LocalTheme
import com.buggily.enemy.local.preferences.dynamic.LocalDynamicSourceable
import com.buggily.enemy.local.preferences.scheme.LocalSchemeSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

internal class ThemeRepository(
    private val localSchemeSource: LocalSchemeSourceable,
    private val localDynamicSource: LocalDynamicSourceable,
) : ThemeRepositable {

    override fun get(): Flow<Theme> = combine(
        localSchemeSource.get(),
        localDynamicSource.get(),
    ) { scheme: LocalTheme.Scheme, dynamic: LocalTheme.Dynamic ->
        LocalTheme(
            scheme = scheme,
            dynamic = dynamic,
        ).to()
    }

    override suspend fun set(theme: Theme) = with(theme) {
        set(scheme)
        set(dynamic)
    }

    override suspend fun set(
        scheme: Theme.Scheme,
    ) = localSchemeSource.set(
        scheme = scheme.toLocal(),
    )
    override suspend fun set(
        dynamic: Theme.Dynamic,
    ) = localDynamicSource.set(
        dynamic = dynamic.toLocal(),
    )
}
