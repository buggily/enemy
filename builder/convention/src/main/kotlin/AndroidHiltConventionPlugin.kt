import com.buggily.enemy.ext.getLib
import com.buggily.enemy.ext.getLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("dagger.hilt.android.plugin")
            apply("com.google.devtools.ksp")
        }

        dependencies {
            with(getLibs()) {
                add("implementation", getLib("hilt.android"))
                add("ksp", getLib("hilt.android.compiler"))
                add("ksp", getLib("androidx.hilt.compiler"))
            }
        }
    }
}
