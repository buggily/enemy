
import com.android.build.gradle.LibraryExtension
import com.buggily.enemy.configureKotlinAndroid
import com.buggily.enemy.ext.getLib
import com.buggily.enemy.ext.getLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.kapt")
        }

        val extension: LibraryExtension = extensions.getByType()
        configureKotlinAndroid(extension)

        extension.defaultConfig {
            targetSdk = 33
        }

        dependencies {
            with(getLibs()) {
                add("implementation", getLib("kotlinx.coroutines.android"))
            }
        }
    }
}
