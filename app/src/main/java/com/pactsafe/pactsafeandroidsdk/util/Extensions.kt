package com.pactsafe.pactsafeandroidsdk.util

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

fun TextView.createClickableSubStrings(placeholder: String, listeners: List<() -> Unit>) {
    val originalText = text
    val ss = SpannableString(originalText.replace(Regex(placeholder), ""))
    var startIndex = 0
    var count = 0
    var offset = 0

    fun createClickableSpan(listener: (() -> Unit)?): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(v: View) {
                listener?.let { it() }
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
    }

    while (true) {
        startIndex = originalText.indexOf(placeholder, startIndex)
        val endIndex = originalText.indexOf(placeholder, startIndex + placeholder.length)

        if (startIndex == -1 || endIndex == -1) {
            break
        }

        ss.setSpan(
            createClickableSpan(
                listeners.getOrNull(count)
            ),
            startIndex - offset,
            endIndex - offset - placeholder.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        offset += placeholder.length * 2
        startIndex = endIndex + placeholder.length + 1
        count++
    }

    movementMethod = LinkMovementMethod.getInstance()
    highlightColor = Color.TRANSPARENT
    text = ss
}