object Build {

    const val ID = "com.buggily.enemy"
    const val RUNNER = "androidx.test.runner.AndroidJUnitRunner"

    object Sdk {
        const val MIN = 29
        const val COMPILE = 33
        const val TARGET = 32
    }

    object OptIn {
        val values: List<String>
            get() = listOf(
                COROUTINES,
                FLOW,

                FOUNDATION,
                LIFECYCLE,
                LAYOUT,
                MATERIAL,
            )

        private const val COROUTINES = "kotlinx.coroutines.ExperimentalCoroutinesApi"
        private const val FLOW = "kotlinx.coroutines.FlowPreview"

        private const val FOUNDATION = "androidx.compose.foundation.ExperimentalFoundationApi"
        private const val LIFECYCLE = "androidx.lifecycle.compose.ExperimentalLifecycleComposeApi"
        private const val LAYOUT = "androidx.compose.foundation.layout.ExperimentalLayoutApi"
        private const val MATERIAL = "androidx.compose.material3.ExperimentalMaterial3Api"
    }
}
