package com.murgupluoglu.constraintstatusview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

/**
 * Created by Mustafa Urgupluoglu on 12.02.2019.
 */

class ConstraintStatusView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
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
        hideAll()
        val view = getView(tag, layoutId)
        view.visibility = View.VISIBLE
        return view
    }

    fun showError(layoutId: Int = resIdStatusError, tag : String = TAG_STATUS_ERROR) : View {
        hideAll()
        val view = getView(tag, layoutId)
        view.visibility = View.VISIBLE
        return view
    }

    fun showEmpty(layoutId: Int = resIdStatusEmpty, tag : String = TAG_STATUS_EMPTY) : View {
        hideAll()
        val view = getView(tag, layoutId)
        view.visibility = View.VISIBLE
        return view
    }

    fun showNoConnection(layoutId: Int = resIdStatusNoConnection, tag : String = TAG_STATUS_NO_CONNECTION) : View {
        hideAll()
        val view = getView(tag, layoutId)
        view.visibility = View.VISIBLE
        return view
    }

    fun showAirplaneMode(layoutId: Int = resIdStatusAirplaneMode, tag : String = TAG_STATUS_AIRPLANE_MODE) : View {
        hideAll()
        val view = getView(tag, layoutId)
        view.visibility = View.VISIBLE
        return view
    }

    fun showCustom(@DrawableRes iconRes: Int = -1, text : String?, buttonText : String?, onClickListener: OnClickListener?) : View {
        hideAll()
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

    fun showContent(){
        hideAll()
    }

    private fun getView(tag : String, layoutId : Int) : View {
        var view : View? = findViewWithTag(tag)
        if(view == null){
            view = layoutInflater.inflate(layoutId, null)
            view.layoutParams = LinearLayoutCompat.LayoutParams(0, 0)
            view.id = View.generateViewId()
            view.tag = tag

            addView(view)

            val set = ConstraintSet()
            set.clone(this@ConstraintStatusView)
            set.connect(view.id, ConstraintSet.TOP, id, ConstraintSet.TOP, 0)
            set.connect(view.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM, 0)
            set.connect(view.id, ConstraintSet.RIGHT, id, ConstraintSet.RIGHT, 0)
            set.connect(view.id, ConstraintSet.LEFT, id, ConstraintSet.LEFT, 0)
            set.applyTo(this@ConstraintStatusView)

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

    private fun hideAll(){
        for(i in 0 until childCount){
            val view = getChildAt(i)
            if(view.tag != null
                && view.tag.toString().contains("STATUSVIEW")){
                view.visibility = View.GONE
            }
        }
    }
}