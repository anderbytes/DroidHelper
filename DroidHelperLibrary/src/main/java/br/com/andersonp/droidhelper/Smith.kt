package br.com.andersonp.droidhelper

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import br.com.andersonp.droidhelper.auxiliarclasses.FragmentsViewPager2Adapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Smith - The forge simplifier
 *
 */
@Suppress("unused")
object Smith {
    /**
     * Creates a TextView object with the defined parameters
     *
     * @return the created TextView
     *
     */
    fun createTextView(context: Context, label: String, bgColor: Int? = null, bgTintColor: Int? = null, bgTintMode: PorterDuff.Mode? = null, size: Float = 12f,
                       bgResource: Int? = null, minimumWidth: Int = 48, minimumHeight: Int = 48, alphaTransparency: Float = 1F, textTypeface: Typeface? = null,
                       leftPad: Int = 16, topPad: Int = 16, rightPad: Int = 16, bottomPad: Int = 16,
                       leftMargin: Int = 4, topMargin: Int = 4, rightMargin: Int = 4, bottomMargin: Int = 4): TextView {

        return (TextView(context)).apply {
            text = label
            textSize = size
            bgColor?.let { setBackgroundColor(it) }
            bgResource?.let { setBackgroundResource(it) }
            bgTintColor?.let { backgroundTintList = ColorStateList.valueOf(it) }
            bgTintMode?.let { backgroundTintMode = it }
            textTypeface?.let { typeface = it }
            minWidth = minimumWidth
            minHeight = minimumHeight
            alpha = alphaTransparency
            setPadding(leftPad,topPad,rightPad,bottomPad)
            layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
                setMargins(leftMargin,topMargin,rightMargin,bottomMargin)
            }
        }
    }

    /**
     * Implements a ViewPager2 based on a TabLayout and several customizations
     *
     * @param fragment the fragment where the TabLayout/ViewPager is running
     * @param fragsList list of fragments to be added to ViewPager
     * @param namesTabs list of names of each tab
     * @param existingAdapter (optional) use an existing adapter instead of creating one
     * @param tabLayout the TabLayout view to be associated and activated
     * @param viewPager the ViewPager view to be associated and activated
     * @param selectedTab (optional) the starting selected tab
     * @param offscreenPages (optional) the number of tabs to be preloaded in memory
     * @param enableTabSwiping (optional) whether tab swiping is allowed
     * @param scrollable (optional) whether tabs can be scrolled. False allows fullwidth even with few tabs, while scrollable doesn't
     * @param tabBackColor (optional) the background color of the tabs
     * @param indicatorColor (optional) the color of the bottom indicator
     * @param rippleColor (optional) the color of the ripple effect
     * @param tabTextColor (optional) the color of the unselected tab title
     * @param selectedTabTextColor (optional) the color of the selected tab title
     * @param runOnTabSelected (optional) code to be executed on Tab Selection listener
     * @param runOnTabUnselected (optional) code to be executed on Tab Unselection listener
     */
    fun implementViewPager(
        fragment: Fragment,
        fragsList: List<Fragment>,
        namesTabs: List<String>,
        existingAdapter: FragmentsViewPager2Adapter? = null,
        tabLayout: TabLayout,
        viewPager: ViewPager2,
        selectedTab: Int = 0,
        offscreenPages: Int = 2,
        enableTabSwiping: Boolean = true,
        scrollable: Boolean = false,
        @ColorInt tabBackColor: Int = Color.parseColor("#FFFFFF"),
        @ColorInt indicatorColor: Int = Color.parseColor("#000000"),
        @ColorRes rippleColor: Int? = null,
        @ColorInt tabTextColor: Int = Color.parseColor("#666666"),
        @ColorInt selectedTabTextColor: Int = Color.parseColor("#000000"),
        runOnTabSelected: ((tab: TabLayout.Tab?) -> Unit)? = null,
        runOnTabUnselected: ((tab: TabLayout.Tab?) -> Unit)? = null,
    ) {

        // Pre-Checks
        if (selectedTab > fragsList.size - 1) error("Selected tab index bigger than the actual number of fragments")

        // Setting an adapter to the ViewPager
        viewPager.apply {
            offscreenPageLimit = offscreenPages
            isUserInputEnabled = enableTabSwiping
            currentItem = selectedTab
            adapter = existingAdapter ?: FragmentsViewPager2Adapter(fragment.childFragmentManager,
                fragment.lifecycle,
                fragsList)
        }

        // Linking the TabLayout and the ViewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = namesTabs[position]
        }.attach()

        // Customization
        tabLayout.apply {
            setBackgroundColor(tabBackColor)
            setSelectedTabIndicatorColor(indicatorColor)
            rippleColor?.let { setTabRippleColorResource(it) }
            setTabTextColors(tabTextColor, selectedTabTextColor)
            tabMode = if (scrollable) TabLayout.MODE_SCROLLABLE else TabLayout.MODE_FIXED
        }

        // Listeners
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                runOnTabSelected?.invoke(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                runOnTabUnselected?.invoke(tab)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    /**
     * Creates a rectangle/square Bitmap filled with the given color
     *
     * @param width the width of the Bitmap shape
     * @param height the height of the Bitmap shape
     * @param hexColor (optional) the color of the Bitmap, in hex format. Defaults on solid Black
     */
    fun drawRectangularBitmap(width: Int, height: Int, hexColor: String = "#000000"): Bitmap {

        val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        // draw rectangle shape to canvas
        val shapeDrawable = ShapeDrawable(RectShape())

        shapeDrawable.setBounds( 0, 0, width, height)
        shapeDrawable.paint.color = Color.parseColor(hexColor)
        shapeDrawable.draw(Canvas(bitmap))
        return bitmap
    }

}