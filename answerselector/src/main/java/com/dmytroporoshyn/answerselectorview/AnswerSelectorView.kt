/*******************************************************************************
 * Developed by Dimitry Poroshyn on 7/31/19 1:19 PM
 * Last modified 7/31/19 1:19 PM
 * Copyright (c) 2019.  All rights reserved.
 ******************************************************************************/

package com.dmytroporoshyn.answerselectorview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

class AnswerSelectorView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var title: TextView
    private var left: AnswerItemView
    private var middle: AnswerItemView
    private var right: AnswerItemView
    private var answer: Int = 0

    fun setItemChecked(int: Int?) {
        when (int) {
            left.baseId -> left.isChecked = true
            middle.baseId -> middle.isChecked = true
            right.baseId -> right.isChecked = true
            else -> middle.isChecked = true
        }
    }

    private fun setCheckedListener(list: List<AnswerItemView>) {
        list.forEach { petSizeItem ->
            petSizeItem.listener { s, b ->
                if (b) {
                    answer = s
                    list.filter { it != petSizeItem }.forEach { it.isChecked = false }
                }
            }
        }
    }

    init {
        val view = inflate(context, R.layout.view_answer_selector, this)
        title = view.findViewById(R.id.title)
        left = view.findViewById(R.id.left)
        middle = view.findViewById(R.id.middle)
        right = view.findViewById(R.id.right)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.AnswerSelectorView,
            0, 0
        ).apply {
            try {
                getString(R.styleable.AnswerSelectorView_titleSelector)?.let { title.text = it }
                getString(R.styleable.AnswerSelectorView_left)?.let { left.setText(it) }
                getString(R.styleable.AnswerSelectorView_middle)?.let { middle.setText(it) }
                getString(R.styleable.AnswerSelectorView_right)?.let { right.setText(it) }
                left.baseId = getInteger(R.styleable.AnswerSelectorView_baseIdLeft, 0)
                middle.baseId = getInteger(R.styleable.AnswerSelectorView_baseIdMiddle, 1)
                right.baseId = getInteger(R.styleable.AnswerSelectorView_baseIdRight, 2)
            } finally {
                recycle()
            }
        }
        setCheckedListener(listOf(left, middle, right))
    }
}