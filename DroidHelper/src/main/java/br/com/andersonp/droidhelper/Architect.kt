package br.com.andersonp.droidhelper

import android.content.res.Resources
import kotlin.math.roundToInt

/**
 * Architect - The grand visual expert
 *
 */
object Architect {
    /**
     * Gets the *WIDTH* of the screen in Pixels
     *
     * @return Width in Pixels
     */
    fun getScreenWidthPX(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    /**
     * Gets the *HEIGHT* of the screen in Pixels
     *
     * @return Height in Pixels
     */
    fun getScreenHeightPX(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    /**
     * Gets the *DENSITY MULTIPLIER* of this device, using 1.0 (MDPI) as base
     *
     * @return Density Multiplier as Float
     */
    fun getScreenDensityMultiplier(): Float {
        return Resources.getSystem().displayMetrics.density
    }

    /**
     * Gets the *ESTIMATED DENSITY* of the screen in DPI
     * Uses 160 as a multiple related to 1.0 (MDPI) devices
     *
     * @return Density in DPI
     */
    fun getScreenDensityDPI(): Float {
        return getScreenDensityMultiplier() * 160
    }

    /**
     * Gets the *NAME* related to the device density
     *
     * @return Density name as String
     */
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

    /**
     * Gets the *WIDTH* of the screen in DP
     *
     * @return Width in DP
     */
    fun getScreenWidthDP(): Int {
        return with (Resources.getSystem().displayMetrics) {
            (widthPixels / density).roundToInt()
        }
    }

    /**
     * Gets the *HEIGHT* of the screen in DP
     *
     * @return Height in DP
     */
    fun getScreenHeightDP(): Int {
        return with (Resources.getSystem().displayMetrics) {
            (heightPixels / density).roundToInt()
        }
    }

}