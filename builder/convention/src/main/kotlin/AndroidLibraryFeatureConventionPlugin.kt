
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("enemy.android.library")
            apply("enemy.android.hilt")
        }

        dependencies {
            add("implementation", project(":core:ext"))
            add("implementation", project(":core:ui"))

            add("implementation", project(":core:domain"))
            add("implementation", project(":core:data"))

            add("implementation", project(":core:navigation"))
        }
    }
}
