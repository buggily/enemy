package com.buggily.enemy.ui.settings

import com.buggily.enemy.CoroutineTestRule
import com.buggily.enemy.data.repository.theme.ThemeRepositable
import com.buggily.enemy.data.repository.theme.dynamic.DynamicRepositable
import com.buggily.enemy.domain.map.theme.ThemeMapper
import com.buggily.enemy.domain.map.theme.dynamic.DynamicMapper
import com.buggily.enemy.domain.use.theme.GetTheme
import com.buggily.enemy.domain.use.theme.SetTheme
import com.buggily.enemy.domain.use.theme.dynamic.GetDynamic
import com.buggily.enemy.domain.use.theme.dynamic.SetDynamic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import com.buggily.enemy.data.theme.Theme as DataTheme
import com.buggily.enemy.domain.theme.Theme as DomainTheme

class SettingsViewModelTest {

    @get:Rule
    val rule = CoroutineTestRule

    private val themeMapper = ThemeMapper()
    private val dynamicMapper = DynamicMapper()

    private lateinit var themeRepository: ThemeRepositable
    private lateinit var dynamicRepository: DynamicRepositable

    @Test
    fun themeOfThemeStateIsDefaultThemeOnInitialization() = themeOfThemeStateIsThemeOnInitialization(
        dataTheme = DataTheme.Default,
    )

    @Test
    fun themeOfThemeStateIsDarkThemeOnInitialization() = themeOfThemeStateIsThemeOnInitialization(
        dataTheme = DataTheme.Dark,
    )

    private fun themeOfThemeStateIsThemeOnInitialization(dataTheme: DataTheme) {
        themeRepository = object : ThemeRepositable {
            override fun get(): Flow<DataTheme> = flowOf(dataTheme)
            override suspend fun set(theme: DataTheme) = Unit
        }

        dynamicRepository = object : DynamicRepositable {
            override fun get(): Flow<DataTheme.Dynamic> = flowOf()
            override suspend fun set(dynamic: DataTheme.Dynamic) = Unit
        }

        val state: SettingsState = viewModel.state.value
        val themeState: SettingsState.ThemeState = state.themeState

        val expectedDomainTheme: DomainTheme = themeMapper.mapTo(dataTheme)
        val returnedDomainTheme: DomainTheme = themeState.theme

        assert(expectedDomainTheme == returnedDomainTheme)
    }

    @Test
    fun onThemeClickOfDefaultThemeSetsTheme() = onThemeClickOfThemeStateSetsTheme(
        dataTheme = DataTheme.Dark,
        domainTheme = DomainTheme.Default,
    )

    @Test
    fun onThemeClickOfDarkThemeSetsTheme() = onThemeClickOfThemeStateSetsTheme(
        dataTheme = DataTheme.Default,
        domainTheme = DomainTheme.Dark,
    )

    @Test
    fun onResetClickOfResetStateResetsTheme() {
        var dataReturnedTheme: DataTheme = DataTheme.Dark

        themeRepository = object : ThemeRepositable {
            override fun get(): Flow<DataTheme> = emptyFlow()
            override suspend fun set(theme: DataTheme) { dataReturnedTheme = theme }
        }

        dynamicRepository = object : DynamicRepositable {
            override fun get(): Flow<DataTheme.Dynamic> = emptyFlow()
            override suspend fun set(dynamic: DataTheme.Dynamic) = Unit
        }

        val state: SettingsState = viewModel.state.value
        val resetState: SettingsState.ResetState = state.resetState

        resetState.onResetClick()

        val domainExpectedTheme: DomainTheme = DomainTheme.Default
        val returnedDomainTheme: DomainTheme = themeMapper.mapTo(dataReturnedTheme)

        assert(domainExpectedTheme == returnedDomainTheme)
    }

    private fun onThemeClickOfThemeStateSetsTheme(
        dataTheme: DataTheme,
        domainTheme: DomainTheme,
    ) {
        var dataReturnedTheme: DataTheme = dataTheme

        themeRepository = object : ThemeRepositable {
            override fun get(): Flow<DataTheme> = emptyFlow()
            override suspend fun set(theme: DataTheme) { dataReturnedTheme = theme }
        }

        dynamicRepository = object : DynamicRepositable {
            override fun get(): Flow<DataTheme.Dynamic> = emptyFlow()
            override suspend fun set(dynamic: DataTheme.Dynamic) = Unit
        }

        val state: SettingsState = viewModel.state.value
        val themeState: SettingsState.ThemeState = state.themeState

        val domainExpectedTheme: DomainTheme = domainTheme
        themeState.onThemeClick(domainExpectedTheme)

        val returnedDomainTheme: DomainTheme = themeMapper.mapTo(dataReturnedTheme)
        assert(domainExpectedTheme == returnedDomainTheme)
    }

    private val viewModel: SettingsViewModel
        get() = SettingsViewModel(
            getTheme = getTheme,
            setTheme = setTheme,
            getDynamic = getDynamic,
            setDynamic = setDynamic,
        )

    private val getTheme: GetTheme
        get() = GetTheme(
            repository = themeRepository,
            mapper = themeMapper,
        )

    private val setTheme: SetTheme
        get() = SetTheme(
            repository = themeRepository,
            mapper = themeMapper,
        )

    private val getDynamic: GetDynamic
        get() = GetDynamic(
            repository = dynamicRepository,
            mapper = dynamicMapper,
        )

    private val setDynamic: SetDynamic
        get() = SetDynamic(
            repository = dynamicRepository,
            mapper = dynamicMapper,
        )
}
