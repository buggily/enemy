
import com.buggily.enemy.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.getByType

class KotlinTestConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("java-library")
            apply("org.jetbrains.kotlin.jvm")
        }

        val extension: JavaPluginExtension = extensions.getByType()
        configureKotlin(extension)
    }
}
