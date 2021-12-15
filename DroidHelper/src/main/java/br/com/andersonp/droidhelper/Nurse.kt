package br.com.andersonp.droidhelper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresPermission
import androidx.core.view.children
import br.com.andersonp.droidhelper.Zero.ratio
import br.com.andersonp.droidhelper.Zero.round
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Nurse - The caring measures taker
 *
 * The methods here intend to be an useful pool of diagnostics and common system-wide queries
 */
@Suppress("unused")
object Nurse {

    /**
     * Shows several screen stats for debugging reasons
     *
     */
    fun screenStats() {
        val densityValue = Architect.getScreenDensityMultiplier()
        val densityDPI = Architect.getScreenDensityDPI()
        val densityName = Architect.getScreenDensityName()
        val screenWidthPX = Architect.getScreenWidthPX()
        val screenWidthDP = Architect.getScreenWidthDP()
        val screenHeightPX = Architect.getScreenHeightPX()
        val screenHeightDP = Architect.getScreenHeightDP()
        val screenWidthIN = (screenWidthPX / densityDPI)
        val screenHeightIN = (screenHeightPX / densityDPI)
        val screenWidthCM = (screenWidthIN * 2.54).round()
        val screenHeightCM = (screenHeightIN * 2.54).round()

        val screenInches =
            sqrt((screenWidthPX / densityDPI).pow(2) + (screenHeightPX / densityDPI).pow(2)).round()

        Log.d("DroidHelper_Screen", """-----------------------------------------------------------
        Density: $densityValue ($densityName) -> ~$densityDPI dpi
        Width: $screenWidthPX px ($screenWidthDP dp) -> $screenWidthIN in -> $screenWidthCM cm
        Height: $screenHeightPX px ($screenHeightDP dp) -> $screenHeightIN in -> $screenHeightCM cm
        Screen: $screenInches'' - Ratio: ${ratio(screenWidthPX, screenHeightPX, 9)}
        -----------------------------------------------------------""".trimMargin())
    }

    /**
     * Sends to logcat a tree-view of the indicated ViewGroup
     */
    fun treeView(v: ViewGroup, indLevel: String = "") {
        val tagBranch: String = if (v.tag != null) { " - Tag: " + v.tag.toString() } else ""
        Log.d("DroidHelper_TreeView",indLevel+"+ " + v.javaClass.simpleName + tagBranch)

        if (v.children.count() > 0) {
            v.children.forEach {
                if (it is ViewGroup) {
                    treeView(it, "$indLevel+")
                } else {
                    val tagNode: String = if (it.tag != null) { " - Tag: " + it.tag.toString() } else ""
                    val textNode: String = if (it is TextView) { " - Text: " + it.text } else ""
                    Log.d("DroidHelper_TreeView",indLevel+"++ " + it.javaClass.simpleName + tagNode + textNode)
                }
            }
        }
    }

    /**
     * Checks for Internet availability
     *
     * @param context any context to base the query on
     * @return whether Internet is reachable or not at the moment
     *
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isInternetAvailable(context: Context?): Boolean {
        if (context == null) {
            Log.d("Net", "Can't query internet because context here is missing")
            return false
        }
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // There are different ways of checking this, depending on the Android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            capabilities?.let {
                val hasCellular: Boolean = it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                val hasWifi: Boolean = it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                val hasEthernet: Boolean = it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)

                return (hasCellular || hasWifi || hasEthernet)
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo?.let {
                return (it.isConnected)
            }
        }
        return false
    }

    /**
     * Returns information of a package installed on the device
     *
     * @param pkg (optional) the full name of the package installed
     * @return the package info object, or null if package is not found
     */
    fun Activity.packageInfo(pkg: String = this.packageName): PackageInfo? {
        return try { packageManager.getPackageInfo(pkg, 0) }
        catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
}