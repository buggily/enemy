
import com.android.build.api.dsl.TestExtension
import com.buggily.enemy.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidTestConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.test")
        }

        extensions.configure<TestExtension> {
            configureKotlinAndroid(this)
        }
    }
}
