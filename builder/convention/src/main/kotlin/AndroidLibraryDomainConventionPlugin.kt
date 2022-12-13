import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryDomainConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("enemy.android.library")
            apply("enemy.android.hilt")
        }

        dependencies {
            add("implementation", project(":core:model"))
            add("implementation", project(":core:domain"))
        }
    }
}
