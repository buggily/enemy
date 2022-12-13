import com.android.build.gradle.LibraryExtension
import com.buggily.enemy.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
        }

        val extension: LibraryExtension = extensions.getByType()
        configureAndroidCompose(extension)
    }
}
