package com.buggily.enemy.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.buggily.enemy.ActivityComposeTestRule
import com.buggily.enemy.R
import com.buggily.enemy.domain.theme.Theme
import com.buggily.enemy.ui.theme.EnemyPalette
import com.buggily.enemy.ui.theme.EnemyTheme
import org.junit.Rule
import org.junit.Test

class SettingsUiTest {

    @get:Rule
    val rule: ActivityComposeTestRule = createAndroidComposeRule()

    @Test
    fun defaultThemeButtonIsSelectedOnInitialization() {
        val state: SettingsState = SettingsState.default.copy(
            themeState = themeState.copy(
                theme = Theme.Default,
            ),
        )

        setContent {
            SettingsScreen(
                state = state,
                modifier = Modifier,
            )
        }

        defaultButton.assertIsSelected()
    }

    @Test
    fun darkThemeButtonIsSelectedOnInitialization() {
        val state: SettingsState = SettingsState.default.copy(
            themeState = themeState.copy(
                theme = Theme.Dark,
            ),
        )

        setContent {
            SettingsScreen(
                state = state,
                modifier = Modifier,
            )
        }

        darkButton.assertIsSelected()
    }

    @Test
    fun themeButtonInvokesOnThemeClickOfThemeState() {
        var clicks = 0
        val state: SettingsState = SettingsState.default.copy(
            themeState = themeState.copy(
                onThemeClick = { clicks++ },
            ),
        )

        setContent {
            SettingsScreen(
                state = state,
                modifier = Modifier,
            )
        }

        defaultButton.performClick()
        assert(clicks == 1)
    }

    @Test
    fun resetButtonInvokesOnResetClickOfResetState() {
        var clicks = 0
        val state: SettingsState = SettingsState.default.copy(
            resetState = SettingsState.default.resetState.copy(
                onResetClick = { clicks++ },
            ),
        )

        setContent {
            SettingsScreen(
                state = state,
                modifier = Modifier,
            )
        }

        resetButton.performClick()
        assert(clicks == 1)
    }

    private val defaultButton: SemanticsNodeInteraction
        get() {
            val contentDescription: String = rule.activity.getString(R.string.theme_default)
            return rule.onNodeWithContentDescription(contentDescription)
        }

    private val darkButton: SemanticsNodeInteraction
        get() {
            val contentDescription: String = rule.activity.getString(R.string.theme_dark)
            return rule.onNodeWithContentDescription(contentDescription)
        }

    private val resetButton: SemanticsNodeInteraction
        get() {
            val text: String = rule.activity.getString(R.string.reset)
            return rule.onNodeWithText(text)
        }

    private fun setContent(content: @Composable () -> Unit) {
        rule.setContent {
            EnemyTheme(palette) {
                content()
            }
        }
    }

    private val themeState: SettingsState.ThemeState
        get() = SettingsState.ThemeState.default.copy(
            themes = themes,
        )

    private val themes: List<Theme>
        get() = listOf(
            Theme.Default,
            Theme.Dark,
        )

    private val palette: EnemyPalette
        get() = EnemyPalette.Theme.Default(
            isDynamic = false,
            isSystemInDarkTheme = false,
        ).let {
            EnemyPalette(it)
        }
}
