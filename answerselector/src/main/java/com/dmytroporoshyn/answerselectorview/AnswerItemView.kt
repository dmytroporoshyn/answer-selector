/*******************************************************************************
 * Developed by Dimitry Poroshyn on 7/31/19 1:47 PM
 * Last modified 7/31/19 1:47 PM
 * Copyright (c) 2019.  All rights reserved.
 ******************************************************************************/

package com.dmytroporoshyn.answerselectorview

import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources

class AnswerItemView(context: Context, attributes: AttributeSet?) : FrameLayout(context, attributes),
    Checkable {

    private var selectorText: TextView
    private var viewBackground: FrameLayout
    private var checkedSelector: Boolean = false
    var baseId: Int = 0
    private var listener: ((Int, Boolean) -> Unit?)? = null


    init {
        val view = LinearLayout.inflate(context, R.layout.view_answer_item, this)
        selectorText = view.findViewById(R.id.selectorText)
        viewBackground = view.findViewById(R.id.viewBackground)
    }

    fun setText(string: String) {
        selectorText.text = string
    }

    override fun isChecked(): Boolean {
        return checkedSelector
    }

    override fun toggle() {
        if (!isChecked) {
            isChecked = !checkedSelector
            refreshDrawableState()
        }
    }

    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }

    override fun setChecked(boolean: Boolean) {
        checkedSelector = boolean
        listener?.invoke(baseId, checkedSelector)
        refreshDrawableState()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            selectorText.setTextColor(
                AppCompatResources.getColorStateList(
                    this.context,
                    R.color.colorAccent
                )
            )
            viewBackground.background =
                AppCompatResources.getDrawable(
                    this.context,
                    R.drawable.ic_answer_view_background_checked
                )
        } else {
            selectorText.setTextColor(
                AppCompatResources.getColorStateList(
                    this.context,
                    R.color.blackText
                )
            )
            viewBackground.background =
                AppCompatResources.getDrawable(this.context, R.drawable.ic_answer_view_background)
        }
        return drawableState
    }

    fun listener(listener: (Int, Boolean) -> Unit) {
        this.listener = listener
    }
}