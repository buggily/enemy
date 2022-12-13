
import com.android.build.gradle.TestExtension
import com.buggily.enemy.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidTestConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.test")
            apply("org.jetbrains.kotlin.android")
        }

        val extension: TestExtension = extensions.getByType()
        configureKotlinAndroid(extension)
    }
}
