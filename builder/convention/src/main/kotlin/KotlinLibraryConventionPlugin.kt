
import com.buggily.enemy.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure

class KotlinLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("java-library")
            apply("org.jetbrains.kotlin.jvm")
            apply("org.jetbrains.kotlin.kapt")
        }

        extensions.configure<JavaPluginExtension> {
            configureKotlin(this)
        }
    }
}
