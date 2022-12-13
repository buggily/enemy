import com.buggily.enemy.ext.getLib
import com.buggily.enemy.ext.getLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.dependencies

class KotlinHiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.kapt")
        }

        val libs: VersionCatalog = getLibs()

        dependencies {
            add("implementation", libs.getLib("hilt.core"))
            add("kapt", libs.getLib("hilt.core.compiler"))
        }
    }
}
