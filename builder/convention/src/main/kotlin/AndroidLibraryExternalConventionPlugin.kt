import com.buggily.enemy.ext.getLib
import com.buggily.enemy.ext.getLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryExternalConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("enemy.android.library")
            apply("enemy.android.hilt")
        }

        dependencies {
            add("implementation", project(":core:query"))
            add("implementation", project(":core:paging"))

            add( "implementation", getLibs().getLib("androidx.paging.core"))
        }
    }
}
