package com.skyphantomhz.segmentedcontrol
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout

class RadioGroupComponent(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    var checkedId = -1
    private var mPassThroughListener: PassThroughHierarchyChangeListener? =
        null
    private var mChildOnCheckedChangeListener: RadioButtonComponent.OnCheckedChangeListener? = null
    var onCheckedChangeListener: (group: RadioGroupComponent, checkedId: Int) -> Unit = { _, _ -> }

    init {
        mChildOnCheckedChangeListener = CheckedStateTracker()
        mPassThroughListener = PassThroughHierarchyChangeListener()
        super.setOnHierarchyChangeListener(mPassThroughListener)
    }

    fun setCheck(viewId: Int) {
        val checkedView = findViewById<View>(viewId)
        if (checkedView != null && checkedView is RadioButtonComponent) {
            if (checkedId != -1) {
                setCheckedStateForView(checkedId, false)
            }
            checkedId = viewId
            setCheckedStateForView(viewId, true)
        }
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        if (child is RadioButtonComponent) {
            if (child.isChecked) {
                if (checkedId != -1) {
                    setCheckedStateForView(checkedId, false)
                }
                setCheckedId(child.id, child.isChecked)
            }
        }
        super.addView(child, params)
    }

    private fun setCheckedStateForView(viewId: Int, checked: Boolean) {
        val checkedView = findViewById<View>(viewId)
        if (checkedView != null && checkedView is RadioButtonComponent) {
            checkedView.isChecked = checked
        }
    }

    inner class PassThroughHierarchyChangeListener : OnHierarchyChangeListener {
        private val mOnHierarchyChangeListener: OnHierarchyChangeListener? = null

        override fun onChildViewAdded(
            parent: View,
            child: View
        ) {
            if (parent === this@RadioGroupComponent && child is RadioButtonComponent) {
                var id = child.getId()
                if (id == View.NO_ID) {
                    id = View.generateViewId()
                    child.setId(id)
                }
                child.setOnCheckedChangeWidgetListener(
                    mChildOnCheckedChangeListener
                )
            }
            mOnHierarchyChangeListener?.onChildViewAdded(parent, child)
        }

        override fun onChildViewRemoved(
            parent: View,
            child: View
        ) {
            if (parent === this@RadioGroupComponent && child is RadioButtonComponent) {
                child.setOnCheckedChangeWidgetListener(null)
            }
            mOnHierarchyChangeListener?.onChildViewRemoved(parent, child)
        }
    }

    private fun setCheckedId(@IdRes id: Int, isChecked: Boolean) {
        checkedId = if (isChecked) id else -1
        onCheckedChangeListener(this, checkedId)
    }

    inner class CheckedStateTracker : RadioButtonComponent.OnCheckedChangeListener {
        override fun onCheckedChanged(
            buttonView: RadioButtonComponent,
            isChecked: Boolean
        ) {
            if (checkedId != -1) {
                setCheckedStateForView(checkedId, false)
            }
            if (isChecked) {
                val id = buttonView.id
                setCheckedId(id, isChecked)
            }
        }
    }
}
