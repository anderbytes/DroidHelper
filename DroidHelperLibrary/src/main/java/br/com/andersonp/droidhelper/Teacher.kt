package br.com.andersonp.droidhelper

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.widget.TextView
import br.com.andersonp.droidhelper.Collector.removeBlanks
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Teacher - The proficient words lord
 *
 */
@Suppress("unused")
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

    /**
     * Highlights all words from a list in the contents of a TextView
     *
     * @param searchedWords the words to be searched in the TextView text
     * @param sourceTextView the TextView to be searched
     * @param highlightColor the color that the highlighted text will have
     * @param onlyIfAllFound only highlights if every word in list were found (default: true)
     */
    fun highlightSearch(searchedWords: List<String>, sourceTextView: TextView, highlightColor: Int = Color.BLUE, onlyIfAllFound: Boolean = true) {

        val cleanWordsList = searchedWords.removeBlanks().distinct()
        val workingText = SpannableStringBuilder(sourceTextView.text)
        val allFound: MutableList<String> = mutableListOf()
        var position = 0

        while (position < workingText.length) {
            for (wanted in cleanWordsList) {
                if ((workingText.length - position) < wanted.length) continue
                val substr = workingText.subSequence(position, position + wanted.length)
                if (substr.toString().uppercase() == wanted.uppercase()) {
                    workingText.setSpan(ForegroundColorSpan(highlightColor), position, position + wanted.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                    allFound.add(wanted)
                }
            }
            position++
        }

        // If not words are found and that is desired, stops operation
        if (onlyIfAllFound && !(allFound.containsAll(cleanWordsList) && cleanWordsList.containsAll(allFound))) {
            return
        }

        sourceTextView.text = workingText

    }

    /**
     * Highlights the word/phrase in the contents of a TextView. Colors nothing if the word/phrase
     * is not found
     *
     * @param searchedText the words to be searched in the TextView text
     * @param sourceTextView the TextView to be searched
     * @param highlightColor the color that the highlighted text will have
     */
    fun highlightSearch(searchedText: String, sourceTextView: TextView, highlightColor: Int = Color.BLUE) {
        val workingText = SpannableStringBuilder(sourceTextView.text)
        val position: Int = workingText.toString().uppercase().indexOf(searchedText.uppercase())

        if (position != -1) {
            workingText.setSpan(ForegroundColorSpan(highlightColor), position, position + searchedText.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            sourceTextView.text = workingText
        }
    }
}