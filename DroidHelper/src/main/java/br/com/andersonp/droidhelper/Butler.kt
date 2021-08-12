package br.com.andersonp.droidhelper

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

object Butler {

    // KEYBOARD METHODS

    fun Fragment.hideKeyboard() {
        this.activity?.hideKeyboard()
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // CLIPBOARD METHODS

    fun Fragment.writeClipboard(label: CharSequence, texto: CharSequence) {
        this.activity?.writeClipboard(label, texto)
    }

    fun Activity.writeClipboard(label: CharSequence, texto: CharSequence) {
        this.writeClipboard(label, texto)
    }

    fun Context.writeClipboard(label: CharSequence, texto: CharSequence) {
        val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText(label, texto))
    }
}