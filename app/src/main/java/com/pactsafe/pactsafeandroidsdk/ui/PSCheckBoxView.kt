package com.pactsafe.pactsafeandroidsdk.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.pactsafe.pactsafeandroidsdk.PSApp
import com.pactsafe.pactsafeandroidsdk.R
import com.pactsafe.pactsafeandroidsdk.util.createClickableSubStrings
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_acceptance_check.view.*

class PSCheckBoxView(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    private val clickObserver = PublishSubject.create<Boolean>()

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


        checkbox.setOnCheckedChangeListener { _, isChecked ->
            when (context) {
                is PSClickWrapActivity -> context.onAcceptanceComplete(isChecked)
                else -> clickObserver.onNext(isChecked)
            }
        }
    }

    fun getCheckedSubscription() = clickObserver
    fun setContracts(contracts: Map<String, Boolean>) {
        TODO("Not yet implemented")
    }
}