
import com.buggily.enemy.ext.getLib
import com.buggily.enemy.ext.getLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinHiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.kapt")
        }

        dependencies {
            with(getLibs()) {
                add("implementation", getLib("hilt.core"))
                add("kapt", getLib("hilt.core.compiler"))
            }
        }
    }
}
