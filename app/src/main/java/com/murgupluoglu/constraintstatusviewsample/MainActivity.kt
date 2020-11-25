package com.murgupluoglu.constraintstatusviewsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.murgupluoglu.constraintstatusview.databinding.StatusErrorBinding
import com.murgupluoglu.constraintstatusviewsample.databinding.ActivityMainBinding
import com.murgupluoglu.constraintstatusviewsample.databinding.CustomStatusErrorBinding
import com.murgupluoglu.constraintstatusviewsample.databinding.CustomStatusLoadingBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.statusView.onRetryClickListener = View.OnClickListener {
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
                test1()
            }
        }

    }

    private suspend fun test1(){
        delay(2 * 1000)
        binding.statusView.showError()
        delay(2 * 1000)
        val customShowErrorView = binding.statusView.showError(CustomStatusErrorBinding.inflate(layoutInflater), "STATUSVIEW_CUSTOM_ERROR")
        val customErrorRetryButton : Button = customShowErrorView.findViewById(R.id.customErrorRetryButton)
        customErrorRetryButton.setOnClickListener {
            Log.e(TAG, "customErrorRetryButton clicked")
        }
        delay(2 * 1000)
        binding.statusView.showLoading()
        delay(2 * 1000)
        val customLoadingView = binding.statusView.showLoading(CustomStatusLoadingBinding.inflate(layoutInflater), "STATUSVIEW_CUSTOM_LOADING")
        val customLoadingTextView : TextView = customLoadingView.findViewById(R.id.customLoadingTextView)
        customLoadingTextView.text = "Updated Custom Loading Message"
        delay(2 * 1000)
        binding.statusView.showEmpty()
        delay(2 * 1000)
        binding.statusView.showNoConnection()
        delay(4 * 1000)
        binding.statusView.showError(StatusErrorBinding.inflate(layoutInflater), "STATUSVIEW_CUSTOM_ERROR")
        delay(2 * 1000)
        binding.statusView.showCustom(CustomStatusLoadingBinding.inflate(layoutInflater), "STATUSVIEW_CUSTOM_1")
        delay(2 * 1000)
        val view = binding.statusView.showCustom(CustomStatusErrorBinding.inflate(layoutInflater))
        val messageTextView = view.findViewById<TextView>(R.id.messageTextView)
        messageTextView.text = "Changed Text From Code"
        delay(2 * 1000)
        binding.statusView.showContent()
    }

    private suspend fun test2(){
        delay(2 * 1000)
        val view = binding.statusView.showLoading()
        delay(6 * 1000)
        binding.statusView.showContent()
    }
}
