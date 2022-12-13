import com.android.build.api.dsl.ApplicationExtension
import com.buggily.enemy.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.application")
        }

        val extension: ApplicationExtension = extensions.getByType()
        configureAndroidCompose(extension)
    }
}
