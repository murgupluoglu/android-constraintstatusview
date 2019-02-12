package com.murgupluoglu.constraintstatusviewsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusView.onRetryClickListener = View.OnClickListener {
            when(it.id){
                R.id.statusNoConnectionRetryButton -> {
                    Log.e(TAG, "statusNoConnectionRetryButton clicked")
                }
                R.id.statusAirplaneModeButton -> {
                    Log.e(TAG, "statusAirplaneModeButton clicked")
                }
                else -> Log.e(TAG, "${it.id} clicked")
            }
        }

       CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                delay(2 * 1000)
                statusView.showError()
                delay(2 * 1000)
                val customShowErrorView = statusView.showError(R.layout.custom_status_error, "STATUSVIEW_CUSTOM_ERROR")
                val customErrorRetryButton : Button = customShowErrorView.findViewById(R.id.customErrorRetryButton)
                customErrorRetryButton.setOnClickListener {
                    Log.e(TAG, "customErrorRetryButton clicked")
                }
                delay(2 * 1000)
                statusView.showLoading()
                delay(2 * 1000)
                val customLoadingView = statusView.showLoading(R.layout.custom_status_loading, "STATUSVIEW_CUSTOM_LOADING")
                val customLoadingTextView : TextView = customLoadingView.findViewById(R.id.customLoadingTextView)
                customLoadingTextView.text = "Updated Custom Loading Message"
                delay(2 * 1000)
                statusView.showEmpty()
                delay(2 * 1000)
                statusView.showNoConnection()
                delay(4 * 1000)
                statusView.showError(R.layout.custom_status_error, "STATUSVIEW_CUSTOM_ERROR")
                delay(2 * 1000)
                statusView.showCustom(R.drawable.ic_android_black_24dp, "I'm Here" , "Click Me", View.OnClickListener {
                    Log.e(TAG, "showCustom Button clicked")
                })
                delay(2 * 1000)
            }
        }

    }
}
