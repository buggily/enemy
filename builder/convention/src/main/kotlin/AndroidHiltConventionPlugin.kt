import com.buggily.enemy.ext.getLib
import com.buggily.enemy.ext.getLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.kapt")
            apply("dagger.hilt.android.plugin")
        }

        dependencies {
            with(getLibs()) {
                add("implementation", getLib("hilt.android"))
                add("kapt", getLib("hilt.android.compiler"))
                add("kapt", getLib("androidx.hilt.compiler"))
            }
        }
    }
}
