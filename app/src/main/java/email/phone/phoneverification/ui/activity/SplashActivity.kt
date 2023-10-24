package email.phone.phoneverification.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import email.phone.phoneverification.databinding.ActivitySplashBinding
import email.phone.phoneverification.ui.login.LoginActivity
import email.phone.phoneverification.utils.AppConstants
import email.phone.phoneverification.utils.getPreference

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 2000 //2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDelayHandler = Handler(Looper.getMainLooper())
        mDelayHandler!!.postDelayed(runnable, SPLASH_DELAY)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(runnable)
        }
    }

    private val runnable: Runnable = Runnable {
        if (!isFinishing) {

            if (getPreference(AppConstants.USER_ID, 0) == 0) {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}