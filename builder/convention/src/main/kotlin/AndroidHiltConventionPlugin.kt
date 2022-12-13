import com.buggily.enemy.ext.getLib
import com.buggily.enemy.ext.getLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.kapt")
            apply("dagger.hilt.android.plugin")
        }

        val libs: VersionCatalog = getLibs()

        dependencies {
            add("implementation", libs.getLib("hilt.android"))
            add("kapt", libs.getLib("hilt.android.compiler"))
        }
    }
}
