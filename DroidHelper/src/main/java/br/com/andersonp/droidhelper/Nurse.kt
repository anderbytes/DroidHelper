package br.com.andersonp.droidhelper

import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import br.com.andersonp.droidhelper.Zero.ratio
import br.com.andersonp.droidhelper.Zero.round
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Nurse - The caring measures taker
 *
 * The methods here intend to be an useful pool of diagnostics and common queries
 */
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
}