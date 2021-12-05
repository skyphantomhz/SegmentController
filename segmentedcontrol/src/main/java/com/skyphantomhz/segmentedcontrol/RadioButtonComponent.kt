package com.skyphantomhz.segmentedcontrol

import android.R
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

abstract class RadioButtonComponent(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    var isChecked = false
        set(value) {
            if (field != value) {
                field = value
                changeState(field)
                refreshDrawableState()
            }
        }
    private var onCheckedChangeListener: OnCheckedChangeListener? = null

    private fun setButtonChecked(isChecked: Boolean) {
        this.isChecked = isChecked
        onCheckedChangeListener?.onCheckedChanged(this, isChecked)
    }

    init {
        isClickable = true
        isFocusable = true
    }

    override fun performClick(): Boolean {
        if (!isChecked) {
            setButtonChecked(!isChecked)
        }
        return super.performClick()
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(
            buttonView: RadioButtonComponent,
            isChecked: Boolean
        )
    }

    abstract fun changeState(isChecked: Boolean)

    open fun setOnCheckedChangeWidgetListener(listener: OnCheckedChangeListener?) {
        onCheckedChangeListener = listener
    }

    private val CheckedStateSet = intArrayOf(
        R.attr.state_checked
    )

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            View.mergeDrawableStates(drawableState, CheckedStateSet)
        }
        return drawableState
    }
}
