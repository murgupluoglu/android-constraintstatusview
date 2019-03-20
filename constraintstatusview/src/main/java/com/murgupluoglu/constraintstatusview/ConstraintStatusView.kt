package com.murgupluoglu.constraintstatusview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*

/**
 * Created by Mustafa Urgupluoglu on 12.02.2019.
 */

class ConstraintStatusView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        const val TAG_STATUSVIEW = "STATUSVIEW"
        const val TAG_STATUS_LOADING = "STATUSVIEW_STATUS_LOADING"
        const val TAG_STATUS_ERROR = "STATUSVIEW_STATUS_ERROR"
        const val TAG_STATUS_EMPTY = "STATUSVIEW_STATUS_EMPTY"
        const val TAG_STATUS_NO_CONNECTION = "STATUSVIEW_STATUS_NO_CONNECTION"
        const val TAG_STATUS_AIRPLANE_MODE = "STATUSVIEW_STATUS_AIRPLANE_MODE"
        private const val TAG_STATUS_CUSTOM = "STATUSVIEW_STATUS_CUSTOM"
    }

    private var layoutInflater: LayoutInflater = LayoutInflater.from(getContext())

    private var resIdStatusLoading = -1
    private var resIdStatusError = -1
    private var resIdStatusEmpty = -1
    private var resIdStatusNoConnection = -1
    private var resIdStatusAirplaneMode = -1
    private var resIdStatusCustom = R.layout.status_custom

    var onRetryClickListener : OnClickListener? = null

    init {
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ConstraintStatusView)
        resIdStatusLoading = ta.getResourceId(R.styleable.ConstraintStatusView_loadingView, R.layout.status_loading)
        resIdStatusError = ta.getResourceId(R.styleable.ConstraintStatusView_errorView, R.layout.status_error)
        resIdStatusEmpty = ta.getResourceId(R.styleable.ConstraintStatusView_emptyView, R.layout.status_empty)
        resIdStatusNoConnection = ta.getResourceId(R.styleable.ConstraintStatusView_emptyView, R.layout.status_no_connection)
        resIdStatusAirplaneMode = ta.getResourceId(R.styleable.ConstraintStatusView_airplaneModeView, R.layout.status_airplane_mode)

        ta.recycle()
    }

    fun showLoading(layoutId: Int = resIdStatusLoading, tag : String = TAG_STATUS_LOADING) : View {
        showContent()
        val view = getView(tag, layoutId)
        view.visibility = View.VISIBLE
        return view
    }

    fun showError(layoutId: Int = resIdStatusError, tag : String = TAG_STATUS_ERROR) : View {
        showContent()
        val view = getView(tag, layoutId)
        view.visibility = View.VISIBLE
        return view
    }

    fun showEmpty(layoutId: Int = resIdStatusEmpty, tag : String = TAG_STATUS_EMPTY) : View {
        showContent()
        val view = getView(tag, layoutId)
        view.visibility = View.VISIBLE
        return view
    }

    fun showNoConnection(layoutId: Int = resIdStatusNoConnection, tag : String = TAG_STATUS_NO_CONNECTION) : View {
        showContent()
        val view = getView(tag, layoutId)
        view.visibility = View.VISIBLE
        return view
    }

    fun showAirplaneMode(layoutId: Int = resIdStatusAirplaneMode, tag : String = TAG_STATUS_AIRPLANE_MODE) : View {
        showContent()
        val view = getView(tag, layoutId)
        view.visibility = View.VISIBLE
        return view
    }

    /**
     * @param layoutId Layout resource file
     * @param tag Must be contains uppercase STATUSVIEW text otherwise view cannot be hide
     */
    fun showCustom(layoutId: Int, userTag : String = ""): View {
        showContent()
        var tag = userTag
        if(tag.isEmpty()){
            tag = "${TAG_STATUSVIEW}_${UUID.randomUUID()}"
            Log.e(TAG_STATUSVIEW, tag)
        }
        val view = getView(tag, layoutId)
        view.visibility = View.VISIBLE
        return view
    }

    fun showCustom(@DrawableRes iconRes: Int = -1, text : String?, buttonText : String?, onClickListener: OnClickListener?) : View {
        showContent()
        val view = getView(TAG_STATUS_CUSTOM, resIdStatusCustom)
        val imageView = view.findViewById<ImageView>(R.id.statusCustomImageView)
        val textView = view.findViewById<TextView>(R.id.statusCustomTextView)
        val retryButton = view.findViewById<TextView>(R.id.statusCustomRetryButton)
        if(iconRes != -1){
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(iconRes)
        }else{
            imageView.visibility = View.GONE
        }
        if(text != null){
            textView.visibility = View.VISIBLE
            textView.text = text
        }else{
            textView.visibility = View.GONE
        }
        if(onClickListener != null){
            retryButton.visibility = View.VISIBLE
            retryButton.text = buttonText
            retryButton?.setOnClickListener {
                it?.let {
                    onClickListener.onClick(it)
                }
            }
        }else{
            retryButton.visibility = View.GONE
        }
        view.visibility = View.VISIBLE
        return view
    }

    private fun getView(tag : String, layoutId : Int) : View {
        var view : View? = findViewWithTag(tag)
        if(view == null){
            view = layoutInflater.inflate(layoutId, this@ConstraintStatusView, false)
            view.tag = tag

            addView(view)

            val statusNoConnectionRetryButton : View? = view.findViewById(R.id.statusNoConnectionRetryButton)
            val statusAirplaneModeButton : View? = view.findViewById(R.id.statusAirplaneModeButton)
            statusNoConnectionRetryButton?.setOnClickListener {
                it?.let {
                    onRetryClickListener?.onClick(it)
                }
            }
            statusAirplaneModeButton?.setOnClickListener {
                it?.let {
                    onRetryClickListener?.onClick(it)
                }
            }

        }
        return view!!
    }

    fun showContent(){
        for(i in 0 until childCount){
            val view = getChildAt(i)
            if(view.tag != null && view.tag.toString().contains(TAG_STATUSVIEW)){
                view.visibility = View.GONE
            }
        }
    }
}