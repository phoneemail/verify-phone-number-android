package email.phone.phoneverification

import android.app.Application
import android.content.Context

class Application  : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        var application: Context? = null
    }
}