import com.android.build.api.dsl.ApplicationExtension
import com.buggily.enemy.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
        }

        val extension: ApplicationExtension = extensions.getByType()
        configureKotlinAndroid(extension)

        extension.defaultConfig {
            targetSdk = 33
        }
    }
}
