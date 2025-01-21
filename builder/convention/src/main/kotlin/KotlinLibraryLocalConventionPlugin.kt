
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinLibraryLocalConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("enemy.kotlin.library")
            apply("enemy.kotlin.hilt")
        }
    }
}
