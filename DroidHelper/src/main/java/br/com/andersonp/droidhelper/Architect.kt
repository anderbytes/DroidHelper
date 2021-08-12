package br.com.andersonp.droidhelper

import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import kotlin.math.roundToInt

object Architect {
    /**
     * Gets the *WIDTH* of the screen in Pixels
     *
     * @return Width in Pixels
     */
    fun getScreenWidthPX(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getScreenHeightPX(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    fun getScreenDensityMultiplier(): Float {
        return Resources.getSystem().displayMetrics.density
    }

    fun getScreenDensityDPI(): Float {
        return getScreenDensityMultiplier() * 160
    }

    fun getScreenDensityName(): String {
        return when(getScreenDensityMultiplier()) {
            0.75F -> "LDPI"
            1.0F -> "MDPI"
            1.5F -> "HDPI"
            2.0F -> "XHDPI"
            3.0F -> "XXHDPI"
            4.0F -> "XXXHDPI"
            else -> "Unspecified"
        }
    }

    fun getScreenWidthDP(): Int {
        return with (Resources.getSystem().displayMetrics) {
            (widthPixels / density).roundToInt()
        }
    }

    fun getScreenHeightDP(): Int {
        return with (Resources.getSystem().displayMetrics) {
            (heightPixels / density).roundToInt()
        }
    }

    fun treeView(v: View, indLevel: String = "") {
        val tagBranch: String = if (v.tag != null) { " - Tag: " + v.tag.toString() } else ""
        Log.d("treeView",indLevel+"+ " + v.javaClass.simpleName + tagBranch)

        if (v is ViewGroup && v.children.count() > 0) {
            v.children.forEach {
                if (it is ViewGroup) {
                    treeView(it, "$indLevel+")
                } else {
                    val tagNo: String = if (it.tag != null) { " - Tag: " + it.tag.toString() } else ""
                    val textNo: String = if (it is TextView) { " - Text: " + it.text } else ""
                    Log.d("treeView",indLevel+"++ " + it.javaClass.simpleName + tagNo + textNo)
                }
            }
        }
    }

}