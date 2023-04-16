
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryLocalConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("enemy.android.library")
            apply("enemy.android.hilt")
        }
    }
}
