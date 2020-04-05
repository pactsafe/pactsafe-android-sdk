package com.pactsafe.pactsafeandroidsdk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.pactsafe.pactsafeandroidsdk.PSApp
import com.pactsafe.pactsafeandroidsdk.R
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class PSDialogFragment(private val contracts: Map<String, Boolean>) : DialogFragment() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Dialog)
        isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.view_click_wrap, container, false)
        val alertText: TextView = view.findViewById(R.id.txt_alert_message)
        val psCheckBoxView: PSCheckBoxView = view.findViewById(R.id.ps_checkbox)
        val submitButton: Button = view.findViewById(R.id.btn_submit)

        psCheckBoxView.setContracts(contracts)
        alertText.text = PSApp.loadAlertMessage()

        compositeDisposable.add(psCheckBoxView.getCheckedSubscription().subscribe({
            submitButton.isEnabled = it
        }, {
            Timber.e("There was an error subscribing to checkbox")
        }))

        submitButton.setOnClickListener {

        }

        return view
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}