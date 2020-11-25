package com.murgupluoglu.constraintstatusview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import com.murgupluoglu.constraintstatusview.databinding.*
import java.util.*

/**
 * Created by Mustafa Urgupluoglu on 12.02.2019.
 */

class ConstraintStatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        const val TAG_STATUSVIEW = "STATUSVIEW"
        const val TAG_STATUS_LOADING = "STATUSVIEW_STATUS_LOADING"
        const val TAG_STATUS_ERROR = "STATUSVIEW_STATUS_ERROR"
        const val TAG_STATUS_EMPTY = "STATUSVIEW_STATUS_EMPTY"
        const val TAG_STATUS_NO_CONNECTION = "STATUSVIEW_STATUS_NO_CONNECTION"
        const val TAG_STATUS_AIRPLANE_MODE = "STATUSVIEW_STATUS_AIRPLANE_MODE"
    }

    private var layoutInflater: LayoutInflater = LayoutInflater.from(getContext())

    var onRetryClickListener : OnClickListener? = null

    fun showLoading(viewBinding: ViewBinding? = null, tag : String = TAG_STATUS_LOADING) : View {
        showContent()
        val view : View = if(viewBinding != null){
            getView(tag, viewBinding)
        }else{
            getView(tag, StatusLoadingBinding.inflate(layoutInflater))
        }
        view.visibility = View.VISIBLE
        return view
    }

    fun showError(viewBinding: ViewBinding? = null, tag : String = TAG_STATUS_ERROR) : View {
        showContent()
        val view : View = if(viewBinding != null){
            getView(tag, viewBinding)
        }else{
            getView(tag, StatusErrorBinding.inflate(layoutInflater))
        }
        view.visibility = View.VISIBLE
        return view
    }

    fun showEmpty(viewBinding: ViewBinding? = null, tag : String = TAG_STATUS_EMPTY) : View {
        showContent()
        val view : View = if(viewBinding != null){
            getView(tag, viewBinding)
        }else{
            getView(tag, StatusEmptyBinding.inflate(layoutInflater))
        }
        view.visibility = View.VISIBLE
        return view
    }

    fun showNoConnection(viewBinding: ViewBinding? = null, tag : String = TAG_STATUS_NO_CONNECTION) : View {
        showContent()
        val view : View = if(viewBinding != null){
            getView(tag, viewBinding)
        }else{
            getView(tag, StatusNoConnectionBinding.inflate(layoutInflater))
        }
        view.visibility = View.VISIBLE
        return view
    }

    fun showAirplaneMode(viewBinding: ViewBinding? = null, tag : String = TAG_STATUS_AIRPLANE_MODE) : View {
        showContent()
        val view : View = if(viewBinding != null){
            getView(tag, viewBinding)
        }else{
            getView(tag, StatusAirplaneModeBinding.inflate(layoutInflater))
        }
        view.visibility = View.VISIBLE
        return view
    }

    /**
     * @param layoutId Layout resource file
     * @param tag Must be contains uppercase STATUSVIEW text otherwise view cannot be hide
     */
    fun showCustom(viewBinding: ViewBinding, userTag : String = ""): View {
        showContent()
        var tag = userTag
        if(tag.isEmpty()){
            tag = "${TAG_STATUSVIEW}_${UUID.randomUUID()}"
            Log.e(TAG_STATUSVIEW, tag)
        }
        val view = getView(tag, viewBinding)
        view.visibility = View.VISIBLE
        return view
    }

    private fun getView(tag : String, viewBinding: ViewBinding) : View {
        var view : View? = findViewWithTag(tag)
        if(view == null){
            view = viewBinding.root
            view.tag = tag


            addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

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
        return view
    }

    fun showContent(){
        for(i in 0 until childCount){
            val view = getChildAt(i)
            Log.e("tag", (view.tag ?: "null") as String)
            if(view.tag != null && view.tag.toString().contains(TAG_STATUSVIEW)){
                view.visibility = View.GONE
            }
        }
    }
}