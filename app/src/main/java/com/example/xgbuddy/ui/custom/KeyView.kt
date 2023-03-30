package com.example.xgbuddy.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintSet.Motion
import com.example.xgbuddy.R

class KeyView(context: Context, attributeSet: AttributeSet) :
    androidx.appcompat.widget.AppCompatImageView(context, attributeSet) {

    private val note: String // TODO: Expand this to a Note data class probably

    var listener: OnKeyPressListener? = null

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.KeyView, 0, 0)
        note = typedArray.getString(R.styleable.KeyView_note) ?: "c"
        setBackgroundForNote()
        typedArray.recycle()
    }

    private fun setBackgroundForNote() {
        if (note.length == 1) {
            setImageResource(R.drawable.kbd_white_selector)
        } else {
            setImageResource(R.drawable.kbd_black_selector)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                if (!isPressed) {
                    isPressed = true
                    listener?.onKeyDown(note)
                }
                return true
            }
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_OUTSIDE -> {
                isPressed = false
                listener?.onKeyUp(note)
                return false
            }
        }
        return false
    }

    interface OnKeyPressListener {
        fun onKeyDown(note: String)
        fun onKeyUp(note: String)
    }
}