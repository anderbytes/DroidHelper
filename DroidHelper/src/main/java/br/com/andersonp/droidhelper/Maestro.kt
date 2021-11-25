package br.com.andersonp.droidhelper

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.andersonp.droidhelper.Butler.isPackageInstalled
import br.com.andersonp.droidhelper.Butler.writeTextClipboard
import java.lang.Exception

/**
 * Maestro - The smooth movements director
 *
 * The methods here intend to allow simpler navigations around the app
 */
@Suppress("unused")
object Maestro {

    /**
     * From a Fragment, loads a dialog that behaves as a Loading Screen with the desired layout/view
     *
     * @param loadingScreenLayout the layout or view to be shown
     * @return the Dialog of the loading screen to be dismissed on a future event
     */
    fun Fragment.loadingPopUp(loadingScreenLayout: Int): Dialog {
        return requireActivity().loadingPopUp(loadingScreenLayout)
    }

    /**
     * From an Activity, loads a dialog that behaves as a Loading Screen with the desired layout/view
     *
     * @param loadingScreenLayout the layout or view to be shown
     * @return the Dialog of the loading screen to be dismissed on a future event
     */
    fun Activity.loadingPopUp(loadingScreenLayout: Int): Dialog {
        return Dialog(this).apply {
            window?.currentFocus
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(loadingScreenLayout)
            setCancelable(false)
            setOwnerActivity(this@loadingPopUp)
            show()
        }
    }

    /**
     * Restarts the given fragment
     *
     */
    fun Fragment.restart() {
        with(parentFragmentManager.beginTransaction()) {
            detach(this@restart)
            commitNow()
            attach(this@restart)
            commitNow()
        }
    }

    /**
     * Open specified url on browser or app, from this Fragment
     *
     * @param url the internet path to be navigated
     * @param type the type of the url as one of the listed
     */
    fun Fragment.openURL(url: String, type: UrlType? = null) {
        requireActivity().openURL(url, type)
    }

    /**
     * Open specified url on browser or app, from this Activity
     *
     * @param url the internet path to be navigated
     * @param type the type of the url as one of the listed
     */
    fun Activity.openURL(url: String, type: UrlType? = null) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        type?.let {
            if (isPackageInstalled(type.androidApp)) {
                intent.setPackage(type.androidApp)
            }
        }

        ContextCompat.startActivity(this, intent, null)
    }

    /**
     * Url type and it's associated common package name in Android
     *
     * @property androidApp name of the package
     */
    enum class UrlType(val androidApp: String) {
        INSTAGRAM("com.instagram.android"),
        LINKEDIN("com.linkedin.android"),
        SPOTIFY("com.spotify.music"),
        TED("com.ted.android"),
        WHATSAPP("com.whatsapp"),
        FACEBOOK("com.facebook.katana"),
        YOUTUBE("com.google.android.youtube"),
        GOOGLEMAPS("com.google.android.gms.maps"),
    }

    /**
     * Send email using an existing email client on the device, from this Fragment
     *
     * @param recipient the destination email
     * @param subject Optional. The subject of the email
     * @param message Optional. The content of the email
     * @param failCode Optional. Code to run in the case of not mail client not found
     * @param copyToClipdOnError Whether the email will be copied to clipboard in the case of not having an email client installed. Defaults to FALSE.
     */
    fun Fragment.sendEmail(recipient: String, subject: String = "", message: String = "", failCode: (() -> Unit)? = null, copyToClipdOnError: Boolean = false) {
        requireActivity().sendEmail(recipient, subject, message, failCode, copyToClipdOnError)
    }

    /**
     * Send email using an existing email client on the device, from this Activity
     *
     * @param recipient the destination email
     * @param subject Optional. The subject of the email
     * @param message Optional. The content of the email
     * @param failCode Optional. Code to run in the case of not mail client not found
     * @param copyToClipdOnError Whether the email will be copied to clipboard in the case of not having an email client installed. Defaults to FALSE.
     */
    fun Activity.sendEmail(recipient: String, subject: String = "", message: String = "", failCode: (() -> Unit)? = null, copyToClipdOnError: Boolean = false) {

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        try {
            startActivity(intent)
        } catch (e: Exception) {

            if (copyToClipdOnError) {
                writeTextClipboard(label = "Email", text = recipient)
            }
            // run code if search for installed email client fails
            failCode?.invoke()
        }

    }

}