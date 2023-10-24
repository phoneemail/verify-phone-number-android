package email.phone.phoneverification.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import email.phone.phoneverification.ui.utils.toast

object CommonUtil {

    fun dialCall(context: Context?, number: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(number)))
        context?.startActivity(intent)
    }

    fun sendEmail(context: Context?, recipient: String, subject: String, message: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.type = "message/rfc822";

        try {
            context?.startActivity(Intent.createChooser(intent, "Choose Email Client..."))
        } catch (e: Exception) {
            e.printStackTrace()
            context?.toast(e.message.toString())
        }
    }

    fun openUrl(context: Context?, link: String) =
        context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
}