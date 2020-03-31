package com.pactsafe.pactsafeandroidsdk.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.pactsafe.pactsafeandroidsdk.PSApp
import com.pactsafe.pactsafeandroidsdk.R
import com.pactsafe.pactsafeandroidsdk.util.createClickableSubStrings
import kotlinx.android.synthetic.main.view_acceptance_check.view.*

class PSCheckBoxView(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.view_acceptance_check, this)

        attrs?.let {
            context.theme.obtainStyledAttributes(it, R.styleable.PSCheckBoxView, 0, 0).apply {
                try {
                    txt_acceptance_language.apply {
                        text = PSApp.loadAcceptanceLanguage()
                        createClickableSubStrings(
                            "##",
                            if (getBoolean(R.styleable.PSCheckBoxView_useOsBrowser, true)) PSApp.getContractLinkClickedList(context)
                            else PSApp.getContractLinks(context)
                        )
                    }
                } finally {
                    recycle()
                }
            }
        }

        val activity = context as PSClickWrapActivity
        checkbox.setOnCheckedChangeListener { compoundButton, isChecked ->   activity.onAcceptanceComplete(isChecked)}
    }

}