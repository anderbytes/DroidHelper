package br.com.andersonp.droidhelper

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.andersonp.droidhelper.Enigma.md5

/**
 * Butler - The convenient tools handler
 *
 */
object Butler {

    // SYSTEM INFO

    /**
     * Gets the device ID from a fragment
     *
     * @return the device ID
     */
    fun Fragment.getDeviceID(): String {
        return requireActivity().getDeviceID()
    }

    /**
     * Gets the device ID from an Activity
     *
     * @return the device ID
     */
    fun Activity.getDeviceID(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID).md5().uppercase()
    }

    // KEYBOARD METHODS

    /**
     * Hide software keyboard from the current Fragment
     *
     */
    fun Fragment.hideKeyboard() {
        requireActivity().hideKeyboard()
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
     * Write some text to the system clipboard, from the current Fragment
     *
     * @param label the label the clip will have on system
     * @param text the text content that has been kept
     */
    fun Fragment.writeTextClipboard(label: CharSequence, text: CharSequence) {
        requireActivity().writeTextClipboard(label, text)
    }

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
     * Detects whether a package is installed on this device, from a Fragment
     *
     * @param packageName name of the package to be searched for
     * @return true if the package is installed
     */
    fun Fragment.isPackageInstalled(packageName: String?): Boolean {
        return requireActivity().isPackageInstalled(packageName)
    }

    /**
     * Detects whether a package is installed on this device, from an Activity
     *
     * @param packageName name of the package to be searched for
     * @return true if the package is installed
     */
    fun Activity.isPackageInstalled(packageName: String?): Boolean {
        if (packageName == null) return false

        return try {
            this.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    // TOAST

    /**
     * Simple way of displaying pop-up messages from a Fragment
     *
     * @param content the message to be shown, as a String
     */
    fun Fragment.msgPop(content: String) {
        requireActivity().msgPop(content)
    }

    /**
     * Simple way of displaying pop-up messages from an Activity
     *
     * @param content the message to be shown, as a String
     */
    fun Activity.msgPop(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    }

    /**
     * Simple way of displaying pop-up messages from a Fragment
     *
     * @param content the message to be shown, as a Spanned String
     */
    fun Fragment.msgPop(content: Spanned) {
        requireActivity().msgPop(content)
    }

    /**
     * Simple way of displaying pop-up messages from an Activity
     *
     * @param content the message to be shown, as a Spanned String
     */
    fun Activity.msgPop(content: Spanned) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    }

}