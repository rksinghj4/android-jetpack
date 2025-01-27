import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlowLearningApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}