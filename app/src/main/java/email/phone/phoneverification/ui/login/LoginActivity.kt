package email.phone.phoneverification.ui.login

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import email.phone.phoneverification.BuildConfig
import email.phone.phoneverification.R
import email.phone.phoneverification.data.OtpApiRequest
import email.phone.phoneverification.data.OtpApiResponse
import email.phone.phoneverification.databinding.ActivityLoginBinding
import email.phone.phoneverification.databinding.OtpDialogBinding
import email.phone.phoneverification.network.ApiInterface
import email.phone.phoneverification.ui.activity.MainActivity
import email.phone.phoneverification.ui.utils.*
import email.phone.phoneverification.utils.AppConstants
import email.phone.phoneverification.utils.LogUtil
import email.phone.phoneverification.utils.ScreenUtils
import email.phone.phoneverification.utils.setPreference
import `in`.aabhasjindal.otptextview.OTPListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.Charset
import java.security.SecureRandom


class LoginActivity : AppCompatActivity() {

    private  var otp: Int  = 0
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        initListeners()
        binding.btnView.tvName.text = getString(R.string.get_otp)

    }

    private fun initListeners() {
        binding.apply {

            btnView.rlButton.click {
                validate()
            }
        }
    }

    private fun validate() {

        if (binding.etPhoneNumber.text.toString().isBlank()) {
            toast(getString(R.string.msg_enter_phone_number))
        } else if(android.util.Patterns.PHONE.matcher(binding.etPhoneNumber.text.toString()).matches().not()){
            toast(getString(R.string.msg_enter_valid_phone))
        }else if (binding.chkAgree.isChecked.not()) {
            toast(getString(R.string.msg_agree_recieve_sms))
        } else {
            sendOTP()
        }
    }

    private fun sendOTP() {

        val sr: SecureRandom = SecureRandom.getInstance("SHA1PRNG")
        otp = sr.nextInt(900000) + 100000
        LogUtil.d(TAG, "sendOTP otp  $otp")
        val otpApiRequest = OtpApiRequest(
            "YOUR_API_KEY",
            "+91", "YOUR_PHONE_NUMBER_REGISTERED_WITH_API_KEY",
            "+91", binding.etPhoneNumber.text.toString(), "OTP - $otp from Phone Email",
            "Your OTP to login in the app is $otp", true
        )

        val call = ApiInterface.RetrofitHelper.apiService.sendOtp(otpApiRequest)

        call.enqueue(object : Callback<OtpApiResponse> {
            override fun onResponse(
                call: Call<OtpApiResponse>,
                response: Response<OtpApiResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.responseCode == 200) {
                        toast("success")
                        runOnUiThread(Runnable { showDialog() })
                    } else {
                        LogUtil.w(TAG, "onResponse: ${response.body().toString()}")
                    }
                }
            }

            override fun onFailure(call: Call<OtpApiResponse>, t: Throwable) {
                LogUtil.d(TAG, "onFailure: ${t.message}")
            }
        })


    }


    private fun showDialog() {
        LogUtil.d(TAG, "showDialog")
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)// Displaying custom shape with logo

        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val bindingDialog = OtpDialogBinding.inflate(inflater)
        dialog.setContentView(bindingDialog.root)
        dialog.setCancelable(false)

        dialog.window!!.setLayout(
            ScreenUtils.getScreenWidth(this) * 80 / 100,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        bindingDialog.btnSubmitView.tvName.text = getString(R.string.submit).uppercase()
        var strOTP = ""
        bindingDialog.apply {
            otpTextView?.requestFocusOTP()
            otpTextView?.otpListener = object : OTPListener {
                override fun onInteractionListener() {
                    LogUtil.e(TAG, "====onInteractionListener====" + tvOTPError.isVisible())
                    if (tvOTPError.isVisible()) {
                        tvOTPError.gone()
                    }
                }

                override fun onOTPComplete(otp: String) {
                    //otp get
                    strOTP = otp
                }
            }

            imgClose.click {
                dialog.dismiss()
            }

            btnSubmitView.rlButton.click {

                //Verify OTP with stored OTP
                if (otpTextView.otp.isNullOrBlank() || otpTextView.otp.length < 6 || otpTextView?.otp != "123456") {
                    otpTextView.showError()
                    tvOTPError.visible()
                } else if (otpTextView.otp == otp.toString()) {
                    tvOTPError.gone()
                    otpTextView.showSuccess()

                    setPreference(AppConstants.USER_ID, 1)
                    setPreference(AppConstants.PHONE_COUNTRY, binding.tvCountry.text.toString().trim())
                    setPreference(AppConstants.PHONE_NUMBER, binding.etPhoneNumber.text.toString().trim())
                    launchActivity<MainActivity>(finishAffinity = true)
                }
            }

            tvResendOTP.click {
                otpTextView?.otp = ""
                tvOTPError.gone()
            }
        }
        dialog.show()

    }

}