package br.com.andersonp.droidhelper

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.andersonp.droidhelper.Butler.isPackageInstalled

/**
 * Maestro - The smooth movements director
 *
 * The methods here intend to allow simpler navigations around the app
 */

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
        if (isPackageInstalled(type?.androidApp)) {
            intent.setPackage(type?.androidApp)
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

}