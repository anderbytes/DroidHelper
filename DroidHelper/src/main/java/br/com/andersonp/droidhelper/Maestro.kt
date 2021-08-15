package br.com.andersonp.droidhelper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Maestro - The smooth movements director
 *
 * The methods here intend to allow simpler navigations around the app
 */

object Maestro {

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
        openURL(url, type)
    }

    /**
     * Open specified url on browser or app, from this Context
     *
     * @param url the internet path to be navigated
     * @param type the type of the url as one of the listed
     */
    fun Context.openURL(url: String, type: UrlType?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.setPackage(type?.androidApp)
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

}