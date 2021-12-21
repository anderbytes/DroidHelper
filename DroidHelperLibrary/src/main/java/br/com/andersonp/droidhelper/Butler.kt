package br.com.andersonp.droidhelper

import android.app.Activity
import android.app.Notification
import android.app.PendingIntent
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.Settings
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import br.com.andersonp.droidhelper.Enigma.md5
import java.util.*

/**
 * Butler - The convenient tools handler
 *
 */
@Suppress("unused")
object Butler {

    // SYSTEM INFO

    /**
     * Gets the Hardware ID of the device
     */
    fun Context.getDeviceHardwareID(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }

    /**
     * Gets the Advertising ID of the device
     */
    fun getAdvertisingID(): String {
        return UUID.randomUUID().toString()
    }

    // KEYBOARD METHODS

    /**
     * Hide software keyboard from the current Fragment
     *
     */
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    /**
     * Hide software keyboard from the current Activity
     *
     */
    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    /**
     * Hide software keyboard from the current Context
     *
     * @param view specific view from where to hide keyboard from
     */
    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // CLIPBOARD METHODS

    /**
     * Write some text to the system clipboard, from the current Activity
     *
     * @param label the label the clip will have on system
     * @param text the text content that has been kept
     */
    fun Activity.writeTextClipboard(label: CharSequence, text: CharSequence) {
        val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText(label, text))
    }

    // PACKAGES

    /**
     * Detects whether a package is installed on this device, from an Activity
     *
     * @param packageName name of the package to be searched for
     * @return true if the package is installed
     */
    fun Activity.isPackageInstalled(packageName: String): Boolean {
        return try {
            this.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    // TOAST

    /**
     * Simple way of displaying pop-up messages
     *
     * @param content the message to be shown, as a String
     */
    fun Activity.msgPop(content: String) {
        runOnUiThread { Toast.makeText(this, content, Toast.LENGTH_LONG).show() }
    }

    /**
     * Simple way of displaying pop-up messages
     *
     * @param content the message to be shown, as a Spanned String
     */
    fun Activity.msgPop(content: Spanned) {
        runOnUiThread { Toast.makeText(this, content, Toast.LENGTH_LONG).show() }
    }

    /**
     * Returns the associated Activity of a given context
     *
     * @return the associated activity, or null if not possible to find one
     */
    fun Context.getActivity(): Activity? {
        var workContext = this
        while (workContext is ContextWrapper) {
            if (workContext is Activity) {
                return workContext
            }
            workContext = workContext.baseContext
        }
        return null
    }

    /**
     * Create a Android notification using the specified parameters
     *
     * @param context create the notification under the given context
     * @param channelName name of the Channel where the notification will be created in
     * @param channelID ID of the Channel where the notification will be created in
     * @param title Title of the notification
     * @param text The text to be showed in the notification
     * @param smallDwbIcon the icon that will appear at the top bar on the device
     * @param largeIcon (optional) the image that will appear at the side of the notification text
     * @param style (optional) an additional style (ex: BigPicture or BigText) to be used
     * @param priority the priority of the notification. Defaults to DEFAULT priority
     * @param autoCancel tells the notification to be dismissed after clicked. Defaults to TRUE
     * @param intent (optional) an Intent to be executed when the notification is clicked
     */
    fun createNotification(
        context: Context,
        channelName: String,
        channelID: Int,
        title: String,
        text: String,
        smallDwbIcon: Int,
        largeIcon: Bitmap? = null,
        style: NotificationCompat.Style? = null,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        autoCancel: Boolean = true,
        intent: PendingIntent? = null) {

        // Mandatory parameters
        val builder = NotificationCompat.Builder(context, channelName)
            .setSmallIcon(smallDwbIcon)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(autoCancel)
            .setPriority(priority)

        // Large side Icon
        largeIcon?.let { builder.setLargeIcon (it) }

        // Style set (optional)
        style?.let { builder.setStyle(it)}

        // Intent setter
        intent?.let { builder.setContentIntent(it) }

        // Generate the notification
        NotificationManagerCompat.from(context).notify(channelID, builder.build())

    }
}