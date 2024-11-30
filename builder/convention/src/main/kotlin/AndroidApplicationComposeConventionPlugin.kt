
import com.android.build.api.dsl.ApplicationExtension
import com.buggily.enemy.configureAndroidCompose
import com.buggily.enemy.ext.getLib
import com.buggily.enemy.ext.getLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        extensions.configure<ApplicationExtension> {
            configureAndroidCompose(this)
        }

        dependencies {
            with(getLibs()) {
                add("implementation", platform(getLib("androidx-compose-bom")))
                add("implementation", getLib("androidx.activity.compose"))

                add("implementation", getLib("androidx.compose.material3"))
                add("implementation", getLib("androidx.compose.material3.windowSizeClass"))

                add("implementation", getLib("androidx.navigation.compose"))
                add("implementation", getLib("androidx.hilt.navigation.compose"))

                add("implementation", getLib("androidx.lifecycle.viewModel.ktx"))
                add("implementation", getLib("androidx.lifecycle.viewModel.compose"))
                add("implementation", getLib("androidx.lifecycle.runtime.compose"))
            }
        }
    }
}
