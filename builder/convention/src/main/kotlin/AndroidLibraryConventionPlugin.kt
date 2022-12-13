import com.android.build.gradle.LibraryExtension
import com.buggily.enemy.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }

        val extension: LibraryExtension = extensions.getByType()
        configureKotlinAndroid(extension)

        extension.defaultConfig {
            targetSdk = 33
        }
    }
}
