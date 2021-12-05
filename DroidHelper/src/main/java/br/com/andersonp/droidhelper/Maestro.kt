package br.com.andersonp.droidhelper

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.andersonp.droidhelper.Butler.getActivity
import br.com.andersonp.droidhelper.Butler.isPackageInstalled
import br.com.andersonp.droidhelper.Butler.writeTextClipboard
import br.com.andersonp.droidhelper.auxiliarclasses.UrlType
import java.lang.Exception

/**
 * Maestro - The smooth movements director
 *
 * The methods here intend to allow simpler navigations around the app
 */
@Suppress("unused")
object Maestro {

    /**
     * Loads a simple dialog with a spinner and a message
     *
     * Don't forget to dismiss() the loader after using it
     *
     * @param message the text shown beside the spinner
     * @param (optional) showOnCreate whether the Dialog needs to be shown right after the creation
     * @return the ProgressDialog of the loading screen
     */
    fun Context.showLoadingMessage(message: String, showOnCreate: Boolean = true): ProgressDialog {
        return ProgressDialog(this).apply {
            setMessage(message)
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            setCancelable(false)
            if (showOnCreate) show()
        }
    }

    /**
     * Loads a dialog that behaves as a Loading Screen with the desired view
     *
     * Don't forget to dismiss() the loader after using it
     *
     * @param loadingScreenView the view to be shown
     * @param (optional) showOnCreate whether the Dialog needs to be shown right after the creation
     * @param (optional) ownerActivity an activity to bound this dialog to, if necessary
     * @return the Dialog of the loading screen
     */
    fun Context.showLoadingView(loadingScreenView: View, showOnCreate: Boolean = true, ownerActivity: Activity? = null): Dialog {
        return Dialog(this).apply {
            window?.currentFocus
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(loadingScreenView)
            setCancelable(false)
            ownerActivity?.let { setOwnerActivity(it)}
            if (showOnCreate) show()
        }
    }

    /**
     * Loads a dialog that behaves as a Loading Screen with the desired layout
     *
     * @param loadingScreenLayout the layout to be shown
     * @return the Dialog of the loading screen to be dismissed on a future event
     */
    fun Activity.showLoadingLayout(loadingScreenLayout: Int, showOnCreate: Boolean = true): Dialog {
        return Dialog(this).apply {
            window?.currentFocus
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(loadingScreenLayout)
            setCancelable(false)
            setOwnerActivity(this@showLoadingLayout)
            if (showOnCreate) show()
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
     * Open specified url on browser or app, from this Activity
     *
     * @param url the internet path to be navigated
     * @param type the type of the url as one of the listed
     */
    fun Context.openURL(url: String, type: UrlType? = null) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        type?.let {
            getActivity()?.let {
                if (it.isPackageInstalled(type.androidApp)) {
                    intent.setPackage(type.androidApp)
                }
            }
        }

        ContextCompat.startActivity(this, intent, null)
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