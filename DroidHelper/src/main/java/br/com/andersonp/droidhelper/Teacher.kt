package br.com.andersonp.droidhelper

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.widget.TextView
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Teacher - The proficient words lord
 *
 */
object Teacher {

    /**
     * Converts a CharSequence with HTML elements to a Spanned String
     *
     * @return spanned string
     */
    fun CharSequence.toHTML(): Spanned {
        return (this as String).toHTML()
    }

    /**
     * Converts a String with HTML elements to a Spanned String
     *
     * @return spanned string
     */
    fun String.toHTML(): Spanned {
        return Html.fromHtml(this)
    }

    /**
     * Justify a TextView text
     *
     */
    fun TextView.justify() {
        val isJustify = AtomicBoolean(false)
        val textString = this.text.toString()
        val textPaint = this.paint
        val builder = SpannableStringBuilder()
        this.post {
            if (!isJustify.get()) {
                val lineCount = this.lineCount
                val textViewWidth = this.width - this.paddingStart - this.paddingEnd
                for (i in 0 until lineCount) {
                    val lineStart = this.layout.getLineStart(i)
                    val lineEnd = this.layout.getLineEnd(i)
                    val lineString = textString.substring(lineStart, lineEnd)
                    if (i == lineCount - 1) {
                        builder.append(SpannableString(lineString))
                        break
                    } else if (lineString.contains('\n')) {
                        builder.append(SpannableString(lineString))
                        continue
                    }
                    val trimSpaceText = lineString.trim { it <= ' ' }
                    val removeSpaceText = lineString.replace(" ".toRegex(), "")
                    val removeSpaceWidth = textPaint.measureText(removeSpaceText)
                    val spaceCount = trimSpaceText.length - removeSpaceText.length.toFloat()
                    val eachSpaceWidth = (textViewWidth - removeSpaceWidth) / spaceCount
                    val spannableString = SpannableString(lineString)
                    for (j in trimSpaceText.indices) {
                        val c = trimSpaceText[j]
                        if (c == ' ') {
                            val drawable: Drawable = ColorDrawable(0x00ffffff)
                            drawable.setBounds(0, 0, eachSpaceWidth.toInt(), 0)
                            val span = ImageSpan(drawable)
                            spannableString.setSpan(
                                span,
                                j,
                                j + 1,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }
                    }
                    builder.append(spannableString)
                }
                this.text = builder
                isJustify.set(true)
            }
        }
    }

}