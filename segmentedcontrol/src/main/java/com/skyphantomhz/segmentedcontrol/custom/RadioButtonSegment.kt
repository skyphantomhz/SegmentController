package com.skyphantomhz.segmentedcontrol.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.skyphantomhz.segmentedcontrol.RadioButtonComponent
import com.skyphantomhz.segmentedcontrol.R

class RadioButtonSegment(context: Context, attrs: AttributeSet? = null) :
    RadioButtonComponent(context, attrs) {

    val child: View

    var content: String? = null
        set(value) {
            child.findViewById<TextView>(R.id.tv_content)?.text = value
            field = value
        }

    var textColor: ColorStateList? = null
        set(value) {
            if (value != null) {
                child.findViewById<TextView>(R.id.tv_content).setTextColor(value)
            }
            field = value
        }

    val defaultTypeFace: Typeface


    init {
        child =
            LayoutInflater.from(context)
                .inflate(R.layout.component_radio_button_segment, this, true)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RadioButtonSimple,
            0, 0
        ).apply {
            try {
                content = getString(R.styleable.RadioButtonSimple_rbsContent)
                textColor = getColorStateList(R.styleable.RadioButtonSimple_rbsTextColor)
            } finally {
                recycle()
            }
        }
        defaultTypeFace = child.findViewById<TextView>(R.id.tv_content).typeface
    }

    override fun changeState(isChecked: Boolean) {
        isSelected = isChecked
    }
}
